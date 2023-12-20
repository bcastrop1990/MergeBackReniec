package pe.gob.reniec.rrcc.plataformaelectronica.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.SolicitudRegLibroRequest;

@Mapper(componentModel = "spring")
public interface RegistroLibroMapper {
    @Mapping(target = "detalleSolicitudLibro", source = "request.detalleSolicitud")
    SolicitudBean RegLibroReqToSolLibroBean(SolicitudRegLibroRequest request);
}
