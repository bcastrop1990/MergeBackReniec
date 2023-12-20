package pe.gob.reniec.rrcc.plataformaelectronica.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.AnalistaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.ArticuloBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.LenguaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudEstadoBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.TipoArchivoBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.TipoRegistroBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.AnalistaResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ArticuloResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.LenguaResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.SolEstadoResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.SolTipoRegistroResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.TipoArchivoResponse;

@Mapper(componentModel = "spring")
public interface MaestroMapper {
  @Mapping(target = "codigo", source = "idTipoArchivo")
  @Mapping(target = "descripcion", source = "nombreArchivo")
  TipoArchivoResponse tipoArchBeanToTipoArchRes(TipoArchivoBean bean);
  ArticuloResponse articuloBeanToArticuloRes(ArticuloBean bean);
  LenguaResponse lenguaBeanToLenguaRes(LenguaBean bean);
  @Mapping(target = "codigo", source = "codigoAnalista")
  @Mapping(target = "descripcion", source = "bean", qualifiedByName = "analistaDescripcion")
  AnalistaResponse analistaBeanToAnalistaResponse(AnalistaBean bean);

  @Mapping(target = "codigo", source = "idTipoRegistro")
  SolTipoRegistroResponse solTipoRegBeanToSolTipoRegResponse(TipoRegistroBean bean);

  @Mapping(target = "codigo", source = "idSolicitudEstado")
  SolEstadoResponse solEstadoBeanToSolEstadoResponse(SolicitudEstadoBean bean);

  @Named("analistaDescripcion")
  static String analistaDescripcion(AnalistaBean bean) {
    return String.format("%s %s %s", bean.getPrimerApellido(),
        bean.getSegundoApellido(),
        bean.getPreNombres());
  }
}
