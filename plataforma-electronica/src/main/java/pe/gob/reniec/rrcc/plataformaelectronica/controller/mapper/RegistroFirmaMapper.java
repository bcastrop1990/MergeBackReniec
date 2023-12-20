package pe.gob.reniec.rrcc.plataformaelectronica.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.PersonaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.RegistradorCivilDetalleBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.TipoSolicitudRegFirmaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.SolicitudRegFirmaRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.BusqPersonaResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.TipoSolicitudRegFirmaResponse;

@Mapper(componentModel = "spring")
public interface RegistroFirmaMapper {
    @Mapping(target = "detalleSolicitudFirma", source = "request.detalleSolicitud")
    SolicitudBean RegFirmaReqToSolBean(SolicitudRegFirmaRequest request);
    TipoSolicitudRegFirmaResponse tipoSolBeanToTipoSolResponse(TipoSolicitudRegFirmaBean bean);

    BusqPersonaResponse personaBeanToBusqPersonaResponse(PersonaBean bean);
}
