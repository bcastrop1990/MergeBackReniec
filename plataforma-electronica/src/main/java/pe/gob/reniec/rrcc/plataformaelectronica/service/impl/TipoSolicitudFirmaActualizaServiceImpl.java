package pe.gob.reniec.rrcc.plataformaelectronica.service.impl;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.OficinaDao;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.RegistradorCivilDao;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.SolicitudDao;
import pe.gob.reniec.rrcc.plataformaelectronica.exception.ApiValidateException;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.*;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.RegistrarFichaRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ValidaRegCivilRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.security.SecurityUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.security.UserInfo;
import pe.gob.reniec.rrcc.plataformaelectronica.service.ArchivoService;
import pe.gob.reniec.rrcc.plataformaelectronica.service.TipoSolicitudFirmaService;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TipoSolicitudFirmaActualizaServiceImpl implements TipoSolicitudFirmaService {
  private RegistradorCivilDao registradorCivilDao;
  private SolicitudDao solicitudDao;
  private ArchivoService archivoService;
  private OficinaDao oficinaDao;
  @Override
  public String validarSituacion(Optional<RegistradorCivilBean> registradorCivil, ValidaRegCivilRequest request) {
    if (!registradorCivil.isPresent())
      throw new ApiValidateException(RegCivilConstant.MSG_REG_CIVIL_NO_EXISTE);

    String estado = registradorCivil.get().getEstado();
    if (estado.equals(RegCivilConstant.ESTADO_BAJA_INHABILITADO))
      throw new ApiValidateException(RegCivilConstant.MSG_REG_CIVIL_INHABILITADO);

    if (!Objects.equals(request.getCodigoOrec(), registradorCivil.get().getCodigoOrec()))
      throw new ApiValidateException(String.format(RegCivilConstant.MSG_REG_CIVIL_HABILITADO_OREC,
          registradorCivil.get().getDescripcionOrecCorta()));

    return RegCivilConstant.SITUACION_ACTUALIZA;
  }

  @Override
  public void registrarFicha(RegistrarFichaRequest request) {
    OficinaBean oficinaBean = oficinaDao.obtener(request.getCodigoOrec())
        .orElseThrow(() -> new ApiValidateException(ConstantUtil.MSG_OREC_NO_EXISTE));
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
    UserInfo userInfo = (UserInfo) SecurityUtil.getAuthentication().getPrincipal();
    RegistradorCivilDetalleBean regCivilDetBean = RegistradorCivilDetalleBean.builder()
        .codigoOrec(request.getCodigoOrec())
        .primerApellido(request.getPrimerApellido())
        .segundoApellido(request.getSegundoApellido())
        .preNombre(request.getPreNombres())
        .celular(request.getCelular())
        .email(request.getEmail())
        .fechaAlta(Optional.ofNullable(request.getFechaAlta())
            .filter(date -> !StringUtils.isEmpty(date))
            .map(date -> Utilitario.parseOfPattern(date, ConstantUtil.DATE_FORMAT_BASIC))
            .orElse(null))
        .fechaActualizacion(LocalDate.now())
        .fechaInicio(Optional.ofNullable(request.getFechaInicio())
            .filter(date -> !StringUtils.isEmpty(date))
            .map(date -> Utilitario.parseOfPattern(date, ConstantUtil.DATE_FORMAT_BASIC))
            .orElse(null))
        .fechaFin(Optional.ofNullable(request.getFechaFin())
            .filter(date -> !StringUtils.isEmpty(date))
            .map(date -> Utilitario.parseOfPattern(date, ConstantUtil.DATE_FORMAT_BASIC))
            .orElse(null))
        .codigoMotivoActualiza(request.getCodigoMotivoActualizacion())
        .codigoCargoRegistrador(request.getCodigoCargo())
        .codigoEstadoFormato(request.getCodigoEstadoFormato())
        .codigoCondicion(request.getCodigoCondicion())
        .idTipoSolFirma(request.getIdTipoSolicitudFirma())
        .numeroDocIdentidad(request.getDni())
        .idTipoDocIdentidad(SolicitudConstant.TIPO_DOC_DNI)
        .estado(request.getCodigoEstadoRegistrador())
        .descripcionUbigeoDetalle(oficinaBean.getDescripcionUbigeoDetalle())
        .descripcionOrecCorta(oficinaBean.getDescripcionLocalCorta())
        .idDetSolFirma(detSolicitudFirma.getIdDetalleSolicitud())
        .idCrea(userInfo.getDni())
        .build();

    RegistradorCivilBean regCivilBean = RegistradorCivilBean.builder()
        .numeroDocIdentidad(request.getDni())
        .celular(request.getCelular())
        .email(regCivilDetBean.getEmail())
        .idTipoDocIdentidad(SolicitudConstant.TIPO_DOC_DNI)
        .fechaCambioEstado(regCivilDetBean.getFechaActualizacion())
        .codigoOrec(request.getCodigoOrec())
        .descripcionOrecCorta(oficinaBean.getDescripcionLocalCorta())
        .estado(request.getCodigoEstadoRegistrador())
        .idActualiza(userInfo.getDni())
        .build();

    DetalleSolicitudArchivoFirmaBean detalleArchivoFirma = DetalleSolicitudArchivoFirmaBean.builder()
        .codigoTipoArchivo(TipoArchivoConstant.DOCUMENTO_APROBADO)
        .codigoUsoArchivo(SolicitudConstant.USO_ARCH_RESPUESTA)
        .idArchivo(archivoService.getIdByCodigo(request.getCodigoNombreArchivo()))
        .idDetalleSolicitud(detSolicitudFirma.getIdDetalleSolicitud())
        .idCrea(userInfo.getDni())
        .build();

    registradorCivilDao.registrarDetalle(regCivilDetBean);
    registradorCivilDao.actualizarActualizacionPorDni(regCivilBean);
    solicitudDao.registrarDetalleArchivoFirma(detalleArchivoFirma);
    solicitudDao.actualizarIdSolicitud(detalleArchivoFirma, solicitudBean.getIdSolicitud());
    solicitudDao.actualizarEstadoFirmaDetSolById(detSolicitudFirma.getIdDetalleSolicitud(),
        request.getCodigoEstadoFirma());
  }
}
