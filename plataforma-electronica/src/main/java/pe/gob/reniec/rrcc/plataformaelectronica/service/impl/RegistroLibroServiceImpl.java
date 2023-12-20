package pe.gob.reniec.rrcc.plataformaelectronica.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.reniec.rrcc.plataformaelectronica.config.NotificationProperties;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.ArchivoDao;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.OficinaDao;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.PersonaDao;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.SolicitudDao;
import pe.gob.reniec.rrcc.plataformaelectronica.exception.ApiValidateException;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.OficinaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.PersonaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudNumeracionBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.DatosOficinaRegLibroRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.DatosPersonaRegLibroRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ValidarDatosRegLibroRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty.NotificationDto;
import pe.gob.reniec.rrcc.plataformaelectronica.security.JWTUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.security.UserInfo;
import pe.gob.reniec.rrcc.plataformaelectronica.security.SecurityUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.service.ArchivoService;
import pe.gob.reniec.rrcc.plataformaelectronica.service.NotificationService;
import pe.gob.reniec.rrcc.plataformaelectronica.service.RegistroLibroService;
import pe.gob.reniec.rrcc.plataformaelectronica.service.SolicitudNumeracionService;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ArchivoConstant;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.SolicitudConstant;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.Utilitario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class RegistroLibroServiceImpl implements RegistroLibroService {

  private PersonaDao personaDao;
  private SolicitudNumeracionService solicitudNumeracionService;
  private SolicitudDao solicitudDao;
  private OficinaDao oficinaDao;
  private ArchivoDao archivoDao;
  private JWTUtil jwtUtil;
  private NotificationService notificationService;
  private NotificationProperties notificationProperties;
  private ArchivoService archivoService;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public String registrar(SolicitudBean solicitudBean) {
    SolicitudNumeracionBean numeracionBean = solicitudNumeracionService.obtener();
    String numeroSolicitud = Utilitario.generateNumeracion(numeracionBean.getPeriodo(),
        numeracionBean.getLongitud(), numeracionBean.getCorrelativo());

    UserInfo userInfo = (UserInfo) SecurityUtil.getAuthentication().getPrincipal();
    OficinaBean oficinaBean = oficinaDao.obtener(userInfo.getCodigoOrec())
        .orElseThrow(() -> new ApiValidateException(ConstantUtil.MSG_OREC_NO_EXISTE));

    solicitudBean.setIdArchivoSustento(archivoService.getIdByCodigo(solicitudBean.getListArchivoSustento().get(0).getCodigoNombre()));
    solicitudBean.setIdTipoRegistro(SolicitudConstant.TIPO_LIBRO);
    solicitudBean.setCodigoOrec(oficinaBean.getCodigoOrec());
    solicitudBean.setDescripcionOrecCorta(oficinaBean.getDescripcionLocalCorta());
    solicitudBean.setDescripcionOrecLarga(oficinaBean.getDescripcionLocalLarga());
    solicitudBean.setCodigoDepartamentoOrec(oficinaBean.getCodigoDepartamento());
    solicitudBean.setCodigoProvinciaOrec(oficinaBean.getCodigoProvincia());
    solicitudBean.setCodigoDistritoOrec(oficinaBean.getCodigoDistrito());
    solicitudBean.setCodigoCentroPobladoOrec(oficinaBean.getCodigoCentroPoblado());
    solicitudBean.setNumeroDocumentoSolicitante(userInfo.getDni());
    solicitudBean.setPrimerApellido(userInfo.getPrimerApellido());
    solicitudBean.setSegundoApellido(userInfo.getSegundoApellido());
    solicitudBean.setPreNombres(userInfo.getPreNombre());
    solicitudBean.setNumeroSolicitud(numeroSolicitud);
    solicitudBean.setIdCrea(userInfo.getDni());
    solicitudBean.setCodigoEstado(SolicitudConstant.ESTADO_REGISTRADO);
    solicitudBean.setIdTipoDocumentoSolicitante(SolicitudConstant.TIPO_DOC_DNI);
    Long id_Solicitud= solicitudDao.registrar(solicitudBean);
    solicitudDao.registrarHistorial(solicitudBean);
    //archivoDao.actualizarEstado(solicitudBean.getIdArchivoSustento(), ArchivoConstant.ESTADO_ASIGNADO);
    for (int i = solicitudBean.getListArchivoSustento().size(); i > 0; i --) {
      Long idArchivoSustento= (archivoService.getIdByCodigo(solicitudBean.getListArchivoSustento().get(i-1).getCodigoNombre()));
      archivoDao.actualizarIdSolicitud(idArchivoSustento, id_Solicitud, ArchivoConstant.ESTADO_ASIGNADO, solicitudBean.getListArchivoSustento().get(i-1).getTipoCodigoNombre());
    }
    solicitudBean.getDetalleSolicitudLibro().forEach(detalle -> {
      detalle.setIdSolicitud(solicitudBean.getIdSolicitud());
      detalle.setIdCrea(userInfo.getDni());
      detalle.setIdSolicitud(id_Solicitud);
      solicitudDao.registrarDetalleLibro(detalle);
    });
    NotificationDto notificationDto = NotificationDto.builder()
            .from(notificationProperties.getFrom2())
            .subject(notificationProperties.getSubject())
            .to(solicitudBean.getEmail())
            .message(String.format(notificationProperties.getBodyTemplate(),
                    buildFullName(solicitudBean),
                    SolicitudConstant.SOLICITUD_LIBRO,
                    LocalDate.now().format(DateTimeFormatter.ofPattern(ConstantUtil.DATE_FORMAT)),
                    solicitudBean.getNumeroSolicitud()))
            .build();
    notificationService.send(notificationDto);
    return numeroSolicitud;
  }

  @Override
  public String validarDatos(ValidarDatosRegLibroRequest datosRequest) {
    DatosPersonaRegLibroRequest datosPersona = datosRequest.getDatosPersona();
    DatosOficinaRegLibroRequest datosOficina = datosRequest.getDatosOficina();
     PersonaBean persona = personaDao.validarPersona(datosPersona.getDni(),
            datosPersona.getDigitoVerifica(), datosPersona.getFechaEmision())
        .orElseThrow(() -> new ApiValidateException(ConstantUtil.MSG_PERSONA_NO_ENCONTRADA));

    UserInfo userInfo = this.mapToPersonInfo(persona, datosOficina.getCodigoOrec());

    return jwtUtil.createExternalToken(userInfo);
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


  private String buildFullName(SolicitudBean solicitudBean) {
    return Stream.of(solicitudBean.getPreNombres(),
                    solicitudBean.getPrimerApellido(),
                    solicitudBean.getSegundoApellido())
            .filter(Objects::nonNull)
            .filter(name -> !StringUtils.isEmpty(name))
            .collect(Collectors.joining(" "));
  }
}
