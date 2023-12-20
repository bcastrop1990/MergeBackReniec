package pe.gob.reniec.rrcc.plataformaelectronica.controller.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ConsultaSolSegResponse;

@Mapper(componentModel = "spring")
public interface SeguimientoMapper {

  @Mapping(target = "tipoRegistro", source = "tipoRegistro.descripcion")
  @Mapping(target = "estadoSolicitud", source = "solicitudEstado.descripcion")
  @Mapping(target = "fechaSolicitud", source = "fechaSolicitud", qualifiedByName = "formatDate")
  @Mapping(target = "codigoArchivoSustento", source = "archivoSustento.codigoNombre")
  @Mapping(target = "codigoArchivoRespuesta", source = "archivoRespuesta.codigoNombre")
  ConsultaSolSegResponse solBeanToSolSegResponse(SolicitudBean bean);

  @Named("formatDate")
  static String formatDate(LocalDateTime fecha) {
    return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }
}
