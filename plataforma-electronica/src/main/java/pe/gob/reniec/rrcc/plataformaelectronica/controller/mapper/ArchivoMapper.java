package pe.gob.reniec.rrcc.plataformaelectronica.controller.mapper;

import java.util.Base64;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.ArchivoBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ArchivoDownloadResponse;

@Mapper(componentModel = "spring")
public interface ArchivoMapper {
  @Mapping(target = "nombre", source = "nombreOriginal")
  @Mapping(target = "archivo", source = "archivo", qualifiedByName = "toBase64")
  ArchivoDownloadResponse archivoBeanToArchivoResponse(ArchivoBean bean);

  @Named("toBase64")
  static String toBase64(byte[] archivo) {
    return Base64.getEncoder().encodeToString(archivo);
  }
}
