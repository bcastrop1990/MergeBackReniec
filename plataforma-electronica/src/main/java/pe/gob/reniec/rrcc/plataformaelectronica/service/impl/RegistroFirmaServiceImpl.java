package pe.gob.reniec.rrcc.plataformaelectronica.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.reniec.rrcc.plataformaelectronica.config.NotificationProperties;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.*;
import pe.gob.reniec.rrcc.plataformaelectronica.exception.ApiValidateException;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.*;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.*;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ApiPageResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ApiResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.BusqRegCivilResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.BusqRegCivilRuipinResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty.NotificationDto;
import pe.gob.reniec.rrcc.plataformaelectronica.security.JWTUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.security.UserInfo;
import pe.gob.reniec.rrcc.plataformaelectronica.security.SecurityUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.service.*;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ArchivoConstant;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.SolicitudConstant;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.Utilitario;

@Service
@AllArgsConstructor
@Slf4j
public class RegistroFirmaServiceImpl implements RegistroFirmaService {

  private RegistradorCivilDao registradorCivilDao;
  private PersonaDao personaDao;
  private TipoSolicitudRegFrimaDao tipoSolicitudRegFrimaDao;
  private JWTUtil jwtUtil;
  private SolicitudNumeracionService solicitudNumeracionService;
  private SolicitudDao solicitudDao;
  private OficinaDao oficinaDao;
  private ArchivoDao archivoDao;
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

    Long Id_Solicitud = solicitudBean.getIdSolicitud();
    solicitudBean.setIdTipoRegistro(SolicitudConstant.TIPO_FIRMA);
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
    solicitudBean.setIdArchivoSustento(archivoService.getIdByCodigo(solicitudBean.getListArchivoSustento().get(0).getCodigoNombre()));
    //solicitudBean.setIdArchivoSustento(Long.valueOf(1));
    solicitudBean.setIdTipoDocumentoSolicitante(SolicitudConstant.TIPO_DOC_DNI);
    Long id_Solicitud = solicitudDao.registrar(solicitudBean);
    solicitudDao.registrarHistorial(solicitudBean);
    for (int i = solicitudBean.getListArchivoSustento().size(); i > 0; i --) {
      Long idArchivoSustento= (archivoService.getIdByCodigo(solicitudBean.getListArchivoSustento().get(i-1).getCodigoNombre()));
      archivoDao.actualizarIdSolicitud(idArchivoSustento, id_Solicitud, ArchivoConstant.ESTADO_ASIGNADO, solicitudBean.getListArchivoSustento().get(i-1).getTipoCodigoNombre());
    }
      solicitudBean.getDetalleSolicitudFirma().forEach(detalle -> {
      detalle.setIdSolicitud(solicitudBean.getIdSolicitud());
      detalle.setIdTipoDocumento(SolicitudConstant.TIPO_DOC_DNI);
      detalle.setIdCrea(userInfo.getDni());
      detalle.setIdSolicitud(id_Solicitud);
      solicitudDao.registrarDetalleFirma(detalle);
      detalle.getDetalleArchivo().forEach(archivo -> {
        archivo.setIdArchivo(archivoService.getIdByCodigo(archivo.getArchivo().getCodigoNombre()));
        archivo.setIdDetalleSolicitud(detalle.getIdDetalleSolicitud());
        archivo.setIdCrea(userInfo.getDni());
        archivo.setCodigoUsoArchivo(SolicitudConstant.USO_ARCH_SUSTENTO);
        archivo.setId_solicitud(Id_Solicitud);
        solicitudDao.registrarDetalleArchivoFirma(archivo);
        archivoDao.actualizarIdSolicitudDetalle(archivo.getIdArchivo(), ArchivoConstant.ESTADO_ASIGNADO);
      });
    });

    NotificationDto notificationDto = NotificationDto.builder()
            .from(notificationProperties.getFrom())
            .subject(notificationProperties.getSubject())
            .to(solicitudBean.getEmail())
            .message(String.format(notificationProperties.getBodyTemplate(),
                    buildFullName(solicitudBean),
                    SolicitudConstant.SOLICITUD_FIRMA,
                    LocalDate.now().format(DateTimeFormatter.ofPattern(ConstantUtil.DATE_FORMAT)),
                    solicitudBean.getNumeroSolicitud()))
            .build();
    notificationService.send(notificationDto);

    return numeroSolicitud;
  }

  @Override
  public List<TipoSolicitudRegFirmaBean> listarTipoSolicitud() {
    return tipoSolicitudRegFrimaDao.listar();
  }

  @Override
  public String validarDatos(ValidarDatosRegFirmaRequest datosRequest) {

    DatosPersonaRegFirmaRequest datosPersona = datosRequest.getDatosPersona();
    DatosOficinaRegFirmaRequest datosOficina = datosRequest.getDatosOficina();
    PersonaBean persona = personaDao.validarPersona(datosPersona.getDni(),
            datosPersona.getDigitoVerifica(), datosPersona.getFechaEmision())
        .orElseThrow(() -> new ApiValidateException(ConstantUtil.MSG_PERSONA_NO_ENCONTRADA));
    UserInfo userInfo = this.mapToPersonInfo(persona, datosOficina.getCodigoOrec());
    return jwtUtil.createExternalToken(userInfo);
  }

  @Override
  public PersonaBean consultarPersonaPorDni(String dni) {

    if (dni.length() != ConstantUtil.INT_MAX_DIG_DNI || !StringUtils.isNumeric(dni)) {
      throw new ApiValidateException(ConstantUtil.MSG_FORMATO_DNI_INCORRECTO);
    }
    return personaDao.buscarByDni(dni)
            .orElseThrow(() -> new ApiValidateException(ConstantUtil.MSG_PERSONA_NO_ENCONTRADA));
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
  @Override
  public ApiResponse<BusqRegCivilRuipinResponse> consultarRegCivilPorDatosRuipin(BusqPorDatosRegCivilRuipinRequest request) {
    RegistradorCivilRuipinBean registradorCivilRuipinBean = RegistradorCivilRuipinBean.builder()
            .numeroDocIdentidad(request.getDni())
            .primerApellido(request.getPrimerApellido())
            .build();

    Optional<RegistradorCivilRuipinBean> solicitudes = registradorCivilDao.consultarRegCivilRuipinPorDatos(
            registradorCivilRuipinBean);

    BusqRegCivilRuipinResponse solResponse = mapSolicitudToRegCivilRuipinResponse(solicitudes.get());


    ApiResponse<BusqRegCivilRuipinResponse> response = new ApiResponse<>();
    response.setCode(ConstantUtil.OK_CODE);
    response.setMessage(ConstantUtil.OK_MESSAGE);
    response.setData(solResponse);

    return response;
  }

  private BusqRegCivilRuipinResponse mapSolicitudToRegCivilRuipinResponse(RegistradorCivilRuipinBean regCivil) {
    BusqRegCivilRuipinResponse regCivilResponse = new BusqRegCivilRuipinResponse();

    regCivilResponse.setPrimerApellido(regCivil.getPrimerApellido());
    regCivilResponse.setSegundoApellido(regCivil.getSegundoApellido());
    regCivilResponse.setPreNombres(regCivil.getPreNombre());
    regCivilResponse.setDni(regCivil.getNumeroDocIdentidad());
    return regCivilResponse;
  }
  @Override
  public ApiPageResponse<BusqRegCivilResponse> consultarRegCivilPorDatos(BusqPorDatosRegCivilRequest request, int page, int size) {
    RegistradorCivilBean registradorCivilBean = RegistradorCivilBean.builder()
            .numeroDocIdentidad(request.getDni())
            .primerApellido(request.getPrimerApellido())
            .segundoApellido(request.getSegundoApellido())
            .preNombre(request.getPreNombres())
            .build();

    Page<RegistradorCivilBean> solicitudes = registradorCivilDao.consultarRegCivilPorDatos(
            registradorCivilBean,
            PageRequest.of(page - 1, size));

    List<BusqRegCivilResponse> solResponse = solicitudes.getContent()
            .stream().map(this::mapSolicitudToRegCivilResponse)
            .collect(Collectors.toList());

    ApiPageResponse<BusqRegCivilResponse> response = new ApiPageResponse<>();
    response.setCode(ConstantUtil.OK_CODE);
    response.setMessage(ConstantUtil.OK_MESSAGE);
    response.setData(solResponse);
    response.setPage(solicitudes.getNumber());
    response.setSize(solicitudes.getSize());
    response.setTotalPage(solicitudes.getTotalPages());
    response.setTotalElements(solicitudes.getTotalElements());
    response.setNumberOfElements(solicitudes.getNumberOfElements());


    return response;
  }

  private BusqRegCivilResponse mapSolicitudToRegCivilResponse(RegistradorCivilBean regCivil) {
    BusqRegCivilResponse regCivilResponse = new BusqRegCivilResponse();

    regCivilResponse.setPrimerApellido(regCivil.getPrimerApellido());
    regCivilResponse.setSegundoApellido(regCivil.getSegundoApellido());
    regCivilResponse.setPreNombres(regCivil.getPreNombre());
    regCivilResponse.setCelular(regCivil.getCelular());
    regCivilResponse.setEmail(regCivil.getEmail());
    regCivilResponse.setNombreDepartamento(regCivil.getOficina().getNombreDepartamento());
    regCivilResponse.setNombreProvincia(regCivil.getOficina().getNombreProvincia());
    regCivilResponse.setNombreDistrito(regCivil.getOficina().getNombreDistrito());
    regCivilResponse.setDescripcionOrec(regCivil.getDescripcionOrecCorta());
    regCivilResponse.setDni(regCivil.getNumeroDocIdentidad());
    return regCivilResponse;
  }

}
