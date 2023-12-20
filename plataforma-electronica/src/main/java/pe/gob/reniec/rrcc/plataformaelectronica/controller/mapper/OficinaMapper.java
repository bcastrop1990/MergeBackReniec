package pe.gob.reniec.rrcc.plataformaelectronica.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.OficinaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ConsultaOficinaOrecRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.DetalleOrecResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.OficinaOrecResponse;

@Mapper(componentModel = "spring")
public interface OficinaMapper {
  OficinaBean oficinaOrecReqToOficnaBean(ConsultaOficinaOrecRequest oficina);

  @Mapping(target = "codigo", source = "oficina.codigoOrec")
  @Mapping(target = "descripcion", source = "oficina.descripcionLocalLarga")
  OficinaOrecResponse oficinaBeanToOficinaOrecRes(OficinaBean oficina);

  DetalleOrecResponse oficinaBeanToDetalleOrecRes(OficinaBean oficinaBean);
}
