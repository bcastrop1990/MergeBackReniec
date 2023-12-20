package pe.gob.reniec.rrcc.plataformaelectronica.controller.mapper;

import org.mapstruct.Mapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.UbigeoBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.UbigeoResponse;

@Mapper(componentModel = "spring")
public interface UbigeoMapper {
  UbigeoResponse ubigeoBeanToUbigeoRes(UbigeoBean ubigeoBean);
}
