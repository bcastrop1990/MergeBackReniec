package pe.gob.reniec.rrcc.plataformaelectronica.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.SolicitudDao;
import pe.gob.reniec.rrcc.plataformaelectronica.exception.ApiValidateException;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.DetalleSolicitudArchivoFirmaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.DetalleSolicitudFirmaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.PersonaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ConsultaSolSegRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ValidarDatosSeguimientoRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ApiPageResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ArchivoResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ConsultaSolSegResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.SegSolDetFirmaResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.security.JWTUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.security.UserInfo;
import pe.gob.reniec.rrcc.plataformaelectronica.security.SecurityUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.service.PersonaService;
import pe.gob.reniec.rrcc.plataformaelectronica.service.SeguimientoService;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.SolicitudConstant;

@Service
@AllArgsConstructor
@Slf4j
public class SeguimientoServiceImpl implements SeguimientoService {

  private SolicitudDao solicitudDao;
  private PersonaService personaService;
  private JWTUtil jwtUtil;

  @Override
  public String validarDatos(ValidarDatosSeguimientoRequest request) {

    PersonaBean personaValida = PersonaBean.builder()
        .dni(request.getDni())
        .digitoVerifica(request.getDigitoVerifica())
        .fechaEmision(request.getFechaEmision())
        .build();

    PersonaBean persona = personaService.validarDatos(personaValida);
    SolicitudBean solicitudBean = solicitudDao.obtenerPorNumero(request.getNumeroSolicitud())
        .orElseThrow(() -> new ApiValidateException(ConstantUtil.MSG_DATOS_INVALIDO));

    if (solicitudBean.getIdTipoRegistro().equals(SolicitudConstant.TIPO_LIBRO)) {
        if(!solicitudBean.getNumeroDocumentoSolicitante().equals(request.getDni())) {
          throw new ApiValidateException(ConstantUtil.MSG_DATOS_INVALIDO);
        }
    } else {
      if (!solicitudBean.getNumeroDocumentoSolicitante().equals(request.getDni())) {
        solicitudDao.obtenerSolFirmaByDniReg(request.getDni())
            .orElseThrow(() -> new ApiValidateException(ConstantUtil.MSG_DATOS_INVALIDO));
      }
    }
    UserInfo userInfo = this.mapToPersonInfo(persona, solicitudBean.getCodigoOrec());
    return jwtUtil.createExternalToken(userInfo);

  }

  @Override
  public ApiPageResponse<ConsultaSolSegResponse> consultarSeguimiento(ConsultaSolSegRequest request, int page, int size) {
    UserInfo userInfo = (UserInfo) SecurityUtil.getAuthentication().getPrincipal();
    Page<SolicitudBean> solicitudes = solicitudDao.consultarSeguimiento(
        request.getNumeroSolicitud(),
        PageRequest.of(page - 1, size),
        userInfo.getDni(),
        request.getFechaIni(),
        request.getFechaFin());
    ApiPageResponse<ConsultaSolSegResponse> response = new ApiPageResponse<>();
    response.setCode(ConstantUtil.OK_CODE);
    response.setMessage(ConstantUtil.OK_MESSAGE);
    response.setData(solicitudes.getContent().stream().map(this::solBeanToSolSegResponse).collect(Collectors.toList()));
    response.setPage(solicitudes.getNumber());
    response.setSize(solicitudes.getSize());
    response.setTotalPage(solicitudes.getTotalPages());
    response.setTotalElements(solicitudes.getTotalElements());
    response.setNumberOfElements(solicitudes.getNumberOfElements());
    return response;
  }
  private ConsultaSolSegResponse solBeanToSolSegResponse(SolicitudBean bean) {
    ConsultaSolSegResponse response = new ConsultaSolSegResponse();
    response.setTipoRegistro(bean.getTipoRegistro().getDescripcion());
    response.setEstadoSolicitud(bean.getSolicitudEstado().getDescripcion());
    response.setFechaSolicitud(bean.getFechaSolicitud().format(DateTimeFormatter.ofPattern(ConstantUtil.DATE_FORMAT)));
    response.setCodigoArchivoSustento(bean.getArchivoSustento().getCodigoNombre());
    response.setCodigoArchivoRespuesta(bean.getArchivoRespuesta().getCodigoNombre());
    response.setNumeroSolicitud(bean.getNumeroSolicitud());
    return response;
  }

  @Override
  public List<SegSolDetFirmaResponse> consultarSeguimientoSolFirma(String nroSolicitud) {
    SolicitudBean solicitudBean = solicitudDao.obtenerPorNumero(nroSolicitud)
        .orElseThrow(() -> new ApiValidateException(SolicitudConstant.SOLICITUD_NO_EXISTE));
    List<SegSolDetFirmaResponse> response = new ArrayList<>();
    List<DetalleSolicitudFirmaBean> detalle = solicitudDao.listarByIdSolicitud(solicitudBean.getIdSolicitud());

    detalle.forEach(det -> {
      Optional<DetalleSolicitudArchivoFirmaBean> detalleArchivo =
      solicitudDao.listarArchivoFirmaByDetalleId(det.getIdDetalleSolicitud())
          .stream().filter(archivo -> archivo.getCodigoUsoArchivo().equals(SolicitudConstant.USO_ARCH_RESPUESTA))
          .findFirst();

      response.add(SegSolDetFirmaResponse.builder()
              .tipoSolicitud(det.getTipoSolicitud().getDescripcion())
              .numeroDocumento(det.getNumeroDocumento())
              .primerApellido(det.getPrimerApellido())
              .segundoApellido(det.getSegundoApellido())
              .preNombres(det.getPreNombres())
              .archivoRespuesta(this.mapArchivoRespuesta(detalleArchivo))
          .build());
    });

    return response;
  }

  private UserInfo mapToPersonInfo(PersonaBean personaBean, String orec) {
    return UserInfo.builder()
        .dni(personaBean.getDni())
        .primerApellido(personaBean.getPrimerApellido())
        .segundoApellido(personaBean.getSegundoApellido())
        .preNombre(personaBean.getPreNombre())
        .codigoOrec(orec)
        .build();
  }

  private ArchivoResponse mapArchivoRespuesta(Optional<DetalleSolicitudArchivoFirmaBean> archivo) {
    if (!archivo.isPresent())
      return null;


    return ArchivoResponse.builder()
        .tipoArchivo(archivo.get().getTipoArchivo().getNombreArchivo())
        .nombreOriginal(String.format("%s.%s",
            archivo.get().getArchivo().getNombreOriginal(),
            archivo.get().getArchivo().getExtension()))
        .codigo(archivo.get().getArchivo().getCodigoNombre())
        .build();
  }

}
