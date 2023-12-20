package pe.gob.reniec.rrcc.plataformaelectronica.service.builder;

import org.springframework.stereotype.Component;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.*;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.DatoOficinaFichaResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.DatoRegCivilFichaResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.FichaRegCivilResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.RegCivilConstant;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class RegistradorCivilBuilder {

  public FichaRegCivilResponse buildFichaNuevo(SolicitudBean solicitudBean,
                                               DetalleSolicitudFirmaBean detSolicitudFirma,
                                               OficinaBean oficinaBean,
                                               ArchivoBean archivoBean,
                                               ArchivoBean archivoFirmaBean) {

    DatoOficinaFichaResponse oficinaFicha = DatoOficinaFichaResponse.builder()
        .codigoOrec(solicitudBean.getCodigoOrec())
        .descripcionORec(solicitudBean.getDescripcionOrecCorta())
        .descripcionUbigeo(oficinaBean.getDescripcionUbigeoDetalle())
        .build();

    DatoRegCivilFichaResponse regCivilFicha = DatoRegCivilFichaResponse.builder()
        .dni(detSolicitudFirma.getNumeroDocumento())
        .preNombres(detSolicitudFirma.getPreNombres())
        .primerApellido(detSolicitudFirma.getPrimerApellido())
        .segundoApellido(detSolicitudFirma.getSegundoApellido())
        .celular(detSolicitudFirma.getCelular())
        .email(detSolicitudFirma.getEmail())
        .codigoCondicion(RegCivilConstant.COD_SITUACION_ALTA)
        .codigoEstadoRegistrador(RegCivilConstant.ESTADO_ALTA_HABILITADO)
        .idTipoArchivoSusteno(solicitudBean.getTipoArchivoSustento().getIdTipoArchivo())
        .nombreArchivoSustento(archivoBean.getNombreOriginal())
        .codigoArchivoSustento(archivoBean.getCodigoNombre())
        .ficha(archivoFirmaBean.getArchivo())
        .idTipoSolFirma(detSolicitudFirma.getIdTipoSolicitud())
        .build();

    return FichaRegCivilResponse.builder()
        .datoOficina(oficinaFicha)
        .datoRegCivil(regCivilFicha)
        .build();
  }

  public FichaRegCivilResponse buildFichaReingreso(SolicitudBean solicitudBean,
                                                   RegistradorCivilBean regCivilBean,
                                                   DetalleSolicitudFirmaBean detSolicitudFirma,
                                                   OficinaBean oficinaBean,
                                                   ArchivoBean archivoBean,
                                                   ArchivoBean archivoFirmaBean,
                                                   RegistradorCivilDetalleBean regCivilDetBean) {

    DatoOficinaFichaResponse oficinaFicha = DatoOficinaFichaResponse.builder()
        .codigoOrec(solicitudBean.getCodigoOrec())
        .descripcionORec(solicitudBean.getDescripcionOrecCorta())
        .descripcionUbigeo(oficinaBean.getDescripcionUbigeoDetalle())
        .build();

    DatoRegCivilFichaResponse regCivilFicha = DatoRegCivilFichaResponse.builder()
        .dni(regCivilBean.getNumeroDocIdentidad())
        .preNombres(regCivilBean.getPreNombre())
        .primerApellido(regCivilBean.getPrimerApellido())
        .segundoApellido(regCivilBean.getSegundoApellido())
        .celular(detSolicitudFirma.getCelular())
        .email(detSolicitudFirma.getEmail())
        .codigoCondicion(RegCivilConstant.COD_SITUACION_REINGRESO)
        .codigoEstadoRegistrador(RegCivilConstant.ESTADO_ALTA_HABILITADO)
        .idTipoArchivoSusteno(solicitudBean.getTipoArchivoSustento().getIdTipoArchivo())
        .nombreArchivoSustento(archivoBean.getNombreOriginal())
        .codigoArchivoSustento(archivoBean.getCodigoNombre())
        .ficha(archivoFirmaBean.getArchivo())
        .codigoCargo(regCivilDetBean.getCodigoCargoRegistrador())
        .idTipoSolFirma(detSolicitudFirma.getIdTipoSolicitud())
        .build();

    return FichaRegCivilResponse.builder()
        .datoOficina(oficinaFicha)
        .datoRegCivil(regCivilFicha)
        .build();
  }

  public FichaRegCivilResponse buildFichaActualiza(SolicitudBean solicitudBean,
                                                   RegistradorCivilBean regCivilBean,
                                                   ArchivoBean archivoBean,
                                                   ArchivoBean archivoFirmaBean,
                                                   RegistradorCivilDetalleBean regCivilDetBean,
                                                   DetalleSolicitudFirmaBean detSolicitudFirma) {

    DatoOficinaFichaResponse oficinaFicha = DatoOficinaFichaResponse.builder()
        .codigoOrec(regCivilBean.getCodigoOrec())
        .descripcionORec(regCivilBean.getDescripcionOrecCorta())
        .descripcionUbigeo(regCivilBean.getOficina().getDescripcionUbigeoDetalle())
        .build();

    DatoRegCivilFichaResponse regCivilFicha = DatoRegCivilFichaResponse.builder()
        .dni(regCivilBean.getNumeroDocIdentidad())
        .preNombres(regCivilBean.getPreNombre())
        .primerApellido(regCivilBean.getPrimerApellido())
        .segundoApellido(regCivilBean.getSegundoApellido())
        .celular(regCivilBean.getCelular())
        .email(regCivilBean.getEmail())
        .codigoCondicion(RegCivilConstant.COD_SITUACION_ALTA)
        .codigoEstadoRegistrador(RegCivilConstant.ESTADO_ALTA_HABILITADO)
        .idTipoArchivoSusteno(solicitudBean.getTipoArchivoSustento().getIdTipoArchivo())
        .nombreArchivoSustento(archivoBean.getNombreOriginal())
        .codigoArchivoSustento(archivoBean.getCodigoNombre())
        .ficha(archivoFirmaBean.getArchivo())
        .fechaAlta(Optional.ofNullable(regCivilDetBean.getFechaAlta())
            .map(date -> date.format(DateTimeFormatter.ofPattern(ConstantUtil.DATE_FORMAT_BASIC)))
            .orElse(null))
        .codigoCargo(regCivilDetBean.getCodigoCargoRegistrador())
        .idTipoSolFirma(detSolicitudFirma.getIdTipoSolicitud())
        .build();

    return FichaRegCivilResponse.builder()
        .datoOficina(oficinaFicha)
        .datoRegCivil(regCivilFicha)
        .build();
  }

  public FichaRegCivilResponse buildFichaBaja(SolicitudBean solicitudBean,
                                              RegistradorCivilBean regCivilBean,
                                              ArchivoBean archivoBean,
                                              ArchivoBean archivoFirmaBean,
                                              RegistradorCivilDetalleBean regCivilDetBean,
                                              DetalleSolicitudFirmaBean detSolicitudFirma) {


    DatoOficinaFichaResponse oficinaFicha = DatoOficinaFichaResponse.builder()
        .codigoOrec(regCivilBean.getCodigoOrec())
        .descripcionORec(regCivilBean.getDescripcionOrecCorta())
        .descripcionUbigeo(regCivilBean.getOficina().getDescripcionUbigeoDetalle())
        .build();

    DatoRegCivilFichaResponse regCivilFicha = DatoRegCivilFichaResponse.builder()
        .dni(regCivilBean.getNumeroDocIdentidad())
        .preNombres(regCivilBean.getPreNombre())
        .primerApellido(regCivilBean.getPrimerApellido())
        .segundoApellido(regCivilBean.getSegundoApellido())
        .celular(regCivilBean.getCelular())
        .email(regCivilBean.getEmail())
        .codigoCondicion(RegCivilConstant.COD_SITUACION_BAJA)
        .codigoEstadoRegistrador(RegCivilConstant.ESTADO_BAJA_INHABILITADO)
        .idTipoArchivoSusteno(solicitudBean.getTipoArchivoSustento().getIdTipoArchivo())
        .nombreArchivoSustento(archivoBean.getNombreOriginal())
        .codigoArchivoSustento(archivoBean.getCodigoNombre())
        .ficha(archivoFirmaBean.getArchivo())
        .fechaAlta(Optional.ofNullable(regCivilDetBean.getFechaAlta())
            .map(date -> date.format(DateTimeFormatter.ofPattern(ConstantUtil.DATE_FORMAT_BASIC)))
            .orElse(null))
        .codigoCargo(regCivilDetBean.getCodigoCargoRegistrador())
        .idTipoSolFirma(detSolicitudFirma.getIdTipoSolicitud())
        .build();

    return FichaRegCivilResponse.builder()
        .datoOficina(oficinaFicha)
        .datoRegCivil(regCivilFicha)
        .build();
  }

}
