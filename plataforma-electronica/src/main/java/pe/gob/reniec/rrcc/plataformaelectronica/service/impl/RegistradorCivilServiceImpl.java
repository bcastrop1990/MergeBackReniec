package pe.gob.reniec.rrcc.plataformaelectronica.service.impl;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.ArchivoDao;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.OficinaDao;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.RegistradorCivilDao;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.SolicitudDao;
import pe.gob.reniec.rrcc.plataformaelectronica.exception.ApiValidateException;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.*;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.*;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.*;
import pe.gob.reniec.rrcc.plataformaelectronica.security.SecurityUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.service.*;
import pe.gob.reniec.rrcc.plataformaelectronica.service.builder.RegistradorCivilBuilder;
import pe.gob.reniec.rrcc.plataformaelectronica.service.factory.TipoSolicitudFirmaFactory;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.*;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class RegistradorCivilServiceImpl implements RegistradorCivilService {
      private RegistradorCivilDao registradorCivilDao;
      private SolicitudDao solicitudDao;
      private OficinaDao oficinaDao;
      private ApiImagenService apiImagenService;
      private ArchivoDao archivoDao;
      private ArchivoService archivoService;
      private RegistradorCivilBuilder registradorCivilBuilder;
      private TipoSolicitudFirmaFactory tipoSolicitudFirmaFactory;
      private NotificationService notificationService;

    @Override
    public String validarSituacion(ValidaRegCivilRequest request) {
        ValidaRegCivilRequest requestValid = Stream.of(request)
            .filter(input -> !StringUtils.isEmpty(input.getCodigoOrec()))
            .filter(input -> !StringUtils.isEmpty(input.getDni()))
            .filter(input -> !StringUtils.isEmpty(input.getIdTipoSolicitudFirma()))
            .findFirst()
            .orElseThrow(() -> new ApiValidateException(ConstantUtil.MSG_DATOS_INVALIDO));

        Optional<RegistradorCivilBean> registradorCivil = registradorCivilDao.buscarPorDni(requestValid.getDni());
        TipoSolicitudFirmaService tipoSolicitudFirmaService =
            tipoSolicitudFirmaFactory.create(requestValid.getIdTipoSolicitudFirma());

        return tipoSolicitudFirmaService.validarSituacion(registradorCivil, requestValid);
    }
    @Override
    public ApiPageResponse<BusqRegCivilResponse> consultarRegCivilPorOficina(BusqPorOficinaRegCivilRequest request, int page, int size) {

        RegistradorCivilBean registradorCivilBean = RegistradorCivilBean.builder()
                .oficina(OficinaBean.builder()
                        .codigoDepartamento(request.getCodigoDepartamento())
                        .codigoProvincia(request.getCodigoProvincia())
                        .codigoDistrito(request.getCodigoDistrito())
                        .codigoOrec(request.getCodigoOrec())
                        .build())
                .build();

        Page<RegistradorCivilBean> solicitudes = registradorCivilDao.consultarRegCivilPorOficina(
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
        System.out.println("response.getData() = " + response.getData());
        return response;
    }
    @Override
    public BusqRegCivilRuipinResponse consultarRegCivilPorDatosRuipin(BusqPorDatosRegCivilRuipinRequest request) {
        RegistradorCivilRuipinBean registradorCivilRuipinBean = null;
        if (request.getDni() != null && request.getPrimerApellido() != null) {
            registradorCivilRuipinBean = RegistradorCivilRuipinBean.builder()
                    .numeroDocIdentidad(request.getDni())
                    .primerApellido(request.getPrimerApellido())
                    .build();
        }

        Optional<RegistradorCivilRuipinBean> solicitudes = registradorCivilDao.consultarRegCivilRuipinPorDatos(registradorCivilRuipinBean);
        BusqRegCivilRuipinResponse solResponse = mapSolicitudToRegCivilRuipinResponse(solicitudes.get());

        return solResponse;
    }

    @Override
    public FichaRegCivilResponse consultarFicha(String dni, String nroSolicitud) {
        SolicitudBean solicitudBean = getSolicitudBean(nroSolicitud);
        DetalleSolicitudFirmaBean detSolicitudFirma = getDetSolicitudFirma(solicitudBean.getIdSolicitud(), dni);

        ValidaRegCivilRequest validaRegCivilRequest = new ValidaRegCivilRequest();
        validaRegCivilRequest.setDni(dni);
        validaRegCivilRequest.setCodigoOrec(solicitudBean.getCodigoOrec());
        validaRegCivilRequest.setIdTipoSolicitudFirma(detSolicitudFirma.getIdTipoSolicitud());
        String condicion = validarSituacion(validaRegCivilRequest);
        //primera entrada

        return buildFichaResponse(solicitudBean,
            detSolicitudFirma,
            condicion);
    }
    @Override
    @Transactional
    public void registrarFicha(RegistrarFichaRequest request) {
        TipoSolicitudFirmaService tipoSolicitudFirmaService =
            tipoSolicitudFirmaFactory.create(request.getIdTipoSolicitudFirma());
        tipoSolicitudFirmaService.registrarFicha(request);

    }
    private SolicitudBean getSolicitudBean(String nroSolicitud) {
        return solicitudDao.obtenerPorNumero(nroSolicitud)
            .orElseThrow(() -> new ApiValidateException(SolicitudConstant.SOLICITUD_NO_EXISTE));
    }
    private DetalleSolicitudFirmaBean getDetSolicitudFirma(Long idSolicitud, String dni) {
        return solicitudDao
            .listarByIdSolicitud(idSolicitud)
            .stream()
            .filter(detSol -> detSol.getNumeroDocumento().equals(dni))
            .findFirst()
            .orElseThrow(() -> new ApiValidateException(SolicitudConstant.MSG_DNI_SIN_SOLICITUD));
    }
    private FichaRegCivilResponse buildFichaResponse(SolicitudBean solicitudBean,
                                                     DetalleSolicitudFirmaBean detSolicitudFirma,
                                                     String condicion) {
        FichaRegCivilResponse fichaResponse;
        String dni = detSolicitudFirma.getNumeroDocumento();

        //Consulta del request

        switch (condicion) {
            case RegCivilConstant.SITUACION_REINGRESO: fichaResponse = buildFichaReingreso(solicitudBean, detSolicitudFirma); break;
            case RegCivilConstant.SITUACION_NUEVO: fichaResponse = buildFichaNuevo(solicitudBean, detSolicitudFirma); break;
            case RegCivilConstant.SITUACION_BAJA: fichaResponse = buildFichaBaja(solicitudBean, detSolicitudFirma); break;
            case RegCivilConstant.SITUACION_ACTUALIZA: fichaResponse = buildFichaActualiza(solicitudBean, detSolicitudFirma); break;
            default: fichaResponse = null;
        }
        if (Objects.nonNull(fichaResponse)) {
            fichaResponse.getDatoRegCivil().setFoto(apiImagenService.obtenerFotoPorDni(dni));
            fichaResponse.getDatoRegCivil().setFirma(
                Optional.ofNullable(apiImagenService.consultarPorDni(dni).getImgFirma())
                    .map(firma -> Base64.getDecoder().decode(firma))
                    .orElse(null));
        }
        return fichaResponse;
    }

    private FichaRegCivilResponse buildFichaActualiza(SolicitudBean solicitudBean, DetalleSolicitudFirmaBean detSolicitudFirma) {
        RegistradorCivilBean regCivilBean = getRegCivilByDni(detSolicitudFirma.getNumeroDocumento());
        RegistradorCivilDetalleBean regCivilDetBean = getRegCivilDetUltimoByDni(detSolicitudFirma.getNumeroDocumento());
        ArchivoBean archivoBean = getArchivoById(solicitudBean.getIdArchivoSustento());
        DetalleSolicitudArchivoFirmaBean detArchivoFirma = getDetArchivoFirmaById(detSolicitudFirma.getIdDetalleSolicitud(),
                TipoArchivoConstant.FORMATO_UNICO_REG_FIRMA_ACTUALIZA);
        ArchivoBean archivoFirmaBean = getArchivoById(detArchivoFirma.getArchivo().getId());

        return registradorCivilBuilder.buildFichaActualiza(
            solicitudBean,
            regCivilBean,
            archivoBean,
            archivoFirmaBean,
            regCivilDetBean,
            detSolicitudFirma);
    }

    private FichaRegCivilResponse buildFichaBaja(SolicitudBean solicitudBean, DetalleSolicitudFirmaBean detSolicitudFirma) {

        RegistradorCivilBean regCivilBean = getRegCivilByDni(detSolicitudFirma.getNumeroDocumento());
        RegistradorCivilDetalleBean regCivilDetBean = getRegCivilDetUltimoByDni(detSolicitudFirma.getNumeroDocumento());
        ArchivoBean archivoBean = getArchivoById(solicitudBean.getIdArchivoSustento());
        DetalleSolicitudFirmaBean detalleSolFirmaAnterior =
            solicitudDao.listarByDni(detSolicitudFirma.getNumeroDocumento())
                .stream()
                .filter(detSol -> !detSol.getIdSolicitud().equals(solicitudBean.getIdSolicitud()))
                .max(Comparator.comparing(DetalleSolicitudFirmaBean::getIdDetalleSolicitud))
                .orElseThrow(() -> new ApiValidateException(SolicitudConstant.MSG_DNI_SIN_SOLICITUD));

        DetalleSolicitudArchivoFirmaBean detArchivoFirma =
            getDetArchivoFirmaById(detalleSolFirmaAnterior.getIdDetalleSolicitud(),
            detalleSolFirmaAnterior.getIdTipoSolicitud().equals(TipoSolicitudFirmaConstant.ALTA)
                ? TipoArchivoConstant.FORMATO_UNICO_REG_FIRMA_ALTA
                : TipoArchivoConstant.FORMATO_UNICO_REG_FIRMA_ACTUALIZA);

        ArchivoBean archivoFirmaBean = getArchivoById(detArchivoFirma.getArchivo().getId());


        return registradorCivilBuilder.buildFichaBaja(
            solicitudBean,
            regCivilBean,
            archivoBean,
            archivoFirmaBean,
            regCivilDetBean,
            detSolicitudFirma);
    }

    private RegistradorCivilDetalleBean getRegCivilDetUltimoByDni(String dni) {
        return registradorCivilDao
            .consultaUltimoMovimientoPorDni(dni)
            .orElseThrow(() -> new ApiValidateException(RegCivilConstant.MSG_REG_CIVIL_NO_EXISTE));
    }

    private RegistradorCivilBean getRegCivilByDni(String dni) {
        return registradorCivilDao.buscarPorDni(dni)
            .orElseThrow(() -> new ApiValidateException(RegCivilConstant.MSG_REG_CIVIL_NO_EXISTE));
    }


    //GINO SUBIR CAMBIO INICIO
    public ConsultaFichaByDniResponse consultaRegCivilByDni(String dni) {
        ConsultaFichaByDniResponse response = new ConsultaFichaByDniResponse();
        RegistradorCivilBean bean = registradorCivilDao.buscarPorDni(dni)
                .orElseThrow(() -> new ApiValidateException(RegCivilConstant.MSG_REG_CIVIL_NO_EXISTE));
        response.setCodigoOrec(bean.getCodigoOrec());
        response.setEstadoRegistrador(bean.getEstado());
        response.setDescripcionOrec(bean.getDescripcionOrecCorta());

        return response;
    }
    //GINO SUBIR CAMBIO FIN

    private FichaRegCivilResponse buildFichaNuevo(SolicitudBean solicitudBean, DetalleSolicitudFirmaBean detSolicitudFirma) {
        OficinaBean oficinaBean = getOficinaByCodigo(solicitudBean.getCodigoOrec());
        ArchivoBean archivoBean = getArchivoById(solicitudBean.getIdArchivoSustento());
        DetalleSolicitudArchivoFirmaBean detArchivoFirma = getDetArchivoFirmaById(detSolicitudFirma.getIdDetalleSolicitud(),
            TipoArchivoConstant.FORMATO_UNICO_REG_FIRMA_ALTA);
        ArchivoBean archivoFirmaBean = getArchivoById(detArchivoFirma.getArchivo().getId());

        return registradorCivilBuilder.buildFichaNuevo(
            solicitudBean,
            detSolicitudFirma,
            oficinaBean,
            archivoBean,
            archivoFirmaBean);
    }

    private FichaRegCivilResponse buildFichaReingreso(SolicitudBean solicitudBean,
                                                      DetalleSolicitudFirmaBean detSolicitudFirma) {

        RegistradorCivilBean regCivilBean = getRegCivilByDni(detSolicitudFirma.getNumeroDocumento());
        OficinaBean oficinaBean = getOficinaByCodigo(solicitudBean.getCodigoOrec());
        ArchivoBean archivoBean = getArchivoById(solicitudBean.getIdArchivoSustento());
        DetalleSolicitudArchivoFirmaBean detArchivoFirma = getDetArchivoFirmaById(detSolicitudFirma.getIdDetalleSolicitud(),
            TipoArchivoConstant.FORMATO_UNICO_REG_FIRMA_ALTA);
        ArchivoBean archivoFirmaBean = getArchivoById(detArchivoFirma.getArchivo().getId());
        RegistradorCivilDetalleBean regCivilDetBean = getRegCivilDetUltimoByDni(detSolicitudFirma.getNumeroDocumento());

        return registradorCivilBuilder.buildFichaReingreso(
            solicitudBean,
            regCivilBean,
            detSolicitudFirma,
            oficinaBean,
            archivoBean,
            archivoFirmaBean,
            regCivilDetBean);
    }

    @Override
    @Transactional
    public void desaprobarFicha(DesaprobarFichaRequest request) {
        SolicitudBean solicitudBean = solicitudDao.obtenerPorNumero(request.getNumeroSolicitud())
            .orElseThrow(() -> new ApiValidateException(SolicitudConstant.SOLICITUD_NO_EXISTE));
        DetalleSolicitudFirmaBean detSolicitudFirma = solicitudDao
            .listarByIdSolicitud(solicitudBean.getIdSolicitud())
            .stream()
            .filter(detSol -> detSol.getNumeroDocumento().equals(request.getDni()))
            .findFirst()
            .orElseThrow(() -> new ApiValidateException(SolicitudConstant.MSG_DNI_SIN_SOLICITUD));

      if (Objects.nonNull(detSolicitudFirma.getCodigoEstadoFirma())
          && detSolicitudFirma.getCodigoEstadoFirma().equals(SolicitudConstant.ESTADO_ATENTIDO)) {
        throw new ApiValidateException(SolicitudConstant.MSG_SOLICITUD_ATENDIDA);
      }

        DetalleSolicitudArchivoFirmaBean detalleArchivoFirma = DetalleSolicitudArchivoFirmaBean.builder()
            .codigoTipoArchivo(TipoArchivoConstant.DOCUMENTO_DESAPROBADO)
            .codigoUsoArchivo(SolicitudConstant.USO_ARCH_RESPUESTA)
            .idArchivo(archivoService.getIdByCodigo(request.getCodigoNombreArchivo()))
            .idDetalleSolicitud(detSolicitudFirma.getIdDetalleSolicitud())
            .idCrea(SecurityUtil.getUserInfo().getDni())
            .build();

        solicitudDao.registrarDetalleArchivoFirma(detalleArchivoFirma);
        solicitudDao.actualizarEstadoFirmaDetSolById(detSolicitudFirma.getIdDetalleSolicitud(),
            request.getCodigoEstadoFirma());
    }

    @Override
    public void atenderFicha(AtenderFichaRequest request) {
        SolicitudBean solicitudBean = solicitudDao.obtenerPorNumero(request.getNumeroSolicitud())
            .orElseThrow(() -> new ApiValidateException(SolicitudConstant.SOLICITUD_NO_EXISTE));

        if (solicitudBean.getCodigoEstado().equals(SolicitudConstant.ESTADO_ATENTIDO)) {
            throw new ApiValidateException(SolicitudConstant.MSG_SOLICITUD_ATENDIDA);
        }

        List<DetalleSolicitudFirmaBean> detSolicitudFirma = solicitudDao
            .listarByIdSolicitud(solicitudBean.getIdSolicitud());

        long totalDetallePendiente = detSolicitudFirma.stream()
            .filter(detalle -> Objects.isNull(detalle.getCodigoEstadoFirma()))
            .count();

        if (totalDetallePendiente > 0) {
            throw new ApiValidateException(SolicitudConstant.MSG_EXISTE_SOLICITUD_FIRMA_PENDIENTE);
        }

        solicitudDao.actualizarEstadoSolicitud(solicitudBean.getIdSolicitud(), SolicitudConstant.ESTADO_ATENTIDO);

    }

    @Override
    public ConsultaFichaByDniResponse consultaFichaByDni(String dni) {
        Map<String, String> cargoRegistrador = new HashMap<>();
        cargoRegistrador.put(RegCivilConstant.COD_CARGO_REGISTRADOR, RegCivilConstant.DES_CARGO_REGISTRADOR);
        cargoRegistrador.put(RegCivilConstant.COD_CARGO_JEFE, RegCivilConstant.DES_CARGO_JEFE);

        Map<String, String> estadoRegistrador = new HashMap<>();
        estadoRegistrador.put(RegCivilConstant.ESTADO_ALTA_HABILITADO, RegCivilConstant.DES_ALTA_HABILITADO);
        estadoRegistrador.put(RegCivilConstant.ESTADO_BAJA_INHABILITADO, RegCivilConstant.DES_BAJA_INHABILITADO);

        RegistradorCivilBean registradorCivilBean = registradorCivilDao.buscarPorDni(dni)
            .orElseThrow(() -> new ApiValidateException(RegCivilConstant.MSG_REG_CIVIL_NO_EXISTE));

        OficinaBean oficinaBean = oficinaDao.obtener(registradorCivilBean.getCodigoOrec())
            .orElseThrow(() -> new ApiValidateException(ConstantUtil.MSG_OREC_NO_EXISTE));

        RegistradorCivilDetalleBean registradorCivilDetalleBean =
            registradorCivilDao.consultaUltimoMovimientoPorDni(dni)
                .orElseThrow(() -> new ApiValidateException(RegCivilConstant.MSG_REG_CIVIL_NO_EXISTE));

        Optional<DetalleSolicitudArchivoFirmaBean> detalleSolicitudArchivoFirmaBean =
            solicitudDao.listarArchivoFirmaByDetalleId(registradorCivilDetalleBean.getIdDetSolFirma())
                .stream()
                .filter(detalle -> detalle.getCodigoUsoArchivo().equals(SolicitudConstant.USO_ARCH_RESPUESTA))
                .findFirst();

        ConsultaFichaByDniResponse consultaFichaByDniResponse = new ConsultaFichaByDniResponse();
        consultaFichaByDniResponse.setNumeroDocumento(registradorCivilBean.getNumeroDocIdentidad());
        consultaFichaByDniResponse.setPrimerApellido(registradorCivilBean.getPrimerApellido());
        consultaFichaByDniResponse.setSegundoApellido(registradorCivilBean.getSegundoApellido());
        consultaFichaByDniResponse.setNombres(registradorCivilBean.getPreNombre());
        consultaFichaByDniResponse.setCelular(registradorCivilBean.getCelular());
        consultaFichaByDniResponse.setEmail(registradorCivilBean.getEmail());
        consultaFichaByDniResponse.setCodigoOrec(oficinaBean.getCodigoOrec());
        consultaFichaByDniResponse.setDescripcionOrec(oficinaBean.getDescripcionLocalCorta());
        consultaFichaByDniResponse.setDescripcionUbigeo(oficinaBean.getDescripcionUbigeoDetalle());
        consultaFichaByDniResponse.setCargoRegistrador(
            cargoRegistrador.getOrDefault(registradorCivilDetalleBean.getCodigoCargoRegistrador(), StringUtils.EMPTY));
        consultaFichaByDniResponse.setEstadoRegistrador(
            estadoRegistrador.getOrDefault(registradorCivilDetalleBean.getCodigoEstadoRegistrador(), StringUtils.EMPTY));
        consultaFichaByDniResponse.setFoto(
            Optional.ofNullable(apiImagenService.obtenerFotoPorDni(dni))
                .map(image -> Base64.getEncoder().encodeToString(image))
                .orElse(null));
        consultaFichaByDniResponse.setFirma(apiImagenService.consultarPorDni(dni).getImgFirma());
        consultaFichaByDniResponse.setFechaAlta(
            Optional.ofNullable(registradorCivilDetalleBean.getFechaAlta())
                .map(fecha -> fecha.format(DateTimeFormatter.ofPattern(ConstantUtil.DATE_FORMAT)))
                .orElse(null));
        consultaFichaByDniResponse.setFechaBaja(
            Optional.ofNullable(registradorCivilDetalleBean.getFechaBaja())
                .map(fecha -> fecha.format(DateTimeFormatter.ofPattern(ConstantUtil.DATE_FORMAT)))
                .orElse(null));

        detalleSolicitudArchivoFirmaBean.ifPresent(detalleArchivo -> {
            ArchivoBean archivoBean = archivoDao.obtener(detalleArchivo.getArchivo().getId())
                    .orElse(null);
            consultaFichaByDniResponse.setFicha(Optional.ofNullable(archivoBean)
                .map(archivo -> Base64.getEncoder().encodeToString(archivo.getArchivo()))
                .orElse(null));
            if (registradorCivilDetalleBean.getIdTipoSolFirma().equals(TipoSolicitudFirmaConstant.ALTA)) {
                consultaFichaByDniResponse.setTipoArchivoAlta(detalleArchivo.getTipoArchivo().getNombreArchivo());
                consultaFichaByDniResponse.setNombreArchivoAlta(
                    String.format("%s.%s", detalleArchivo.getArchivo().getNombreOriginal(),
                        detalleArchivo.getArchivo().getExtension()));
            } else if (registradorCivilDetalleBean.getIdTipoSolFirma().equals(TipoSolicitudFirmaConstant.BAJA)) {
                consultaFichaByDniResponse.setTipoArchivoBaja(detalleArchivo.getTipoArchivo().getNombreArchivo());
                consultaFichaByDniResponse.setNombreArchivoBaja(
                    String.format("%s.%s", detalleArchivo.getArchivo().getNombreOriginal(),
                        detalleArchivo.getArchivo().getExtension()));
            }
        });

        return consultaFichaByDniResponse;
    }

    private DetalleSolicitudArchivoFirmaBean getDetArchivoFirmaById(Long id, String idTipoArchivo) {
        return solicitudDao.listarArchivoFirmaByDetalleId(id)
            .stream()
            .filter(detArchivo -> detArchivo.getTipoArchivo().getIdTipoArchivo().equals(idTipoArchivo))
            .findFirst()
            .orElseThrow(() -> new ApiValidateException(SolicitudConstant.MSG_TIPO_ARCHIVO_NO_REGISTRADO));
    }
    private OficinaBean getOficinaByCodigo(String codigo) {
        return oficinaDao.obtener(codigo)
            .orElseThrow(() -> new ApiValidateException(ConstantUtil.MSG_OREC_NO_EXISTE));
    }
    private ArchivoBean getArchivoById(Long id) {
        return archivoDao.obtener(id)
            .orElseThrow(() -> new ApiValidateException(ArchivoConstant.MSG_ARCHIVO_NO_EXISTE));
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
    private BusqRegCivilRuipinResponse mapSolicitudToRegCivilRuipinResponse(RegistradorCivilRuipinBean regCivil) {
        BusqRegCivilRuipinResponse regCivilResponse = new BusqRegCivilRuipinResponse();

        regCivilResponse.setPrimerApellido(regCivil.getPrimerApellido());
        regCivilResponse.setSegundoApellido(regCivil.getSegundoApellido());
        regCivilResponse.setPreNombres(regCivil.getPreNombre());
        regCivilResponse.setDni(regCivil.getNumeroDocIdentidad());
        return regCivilResponse;
    }
}
