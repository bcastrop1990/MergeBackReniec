package pe.gob.reniec.rrcc.plataformaelectronica.service;

import org.springframework.web.multipart.MultipartFile;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.ArchivoBean;

import java.util.List;

public interface ArchivoService {

    String upload(MultipartFile file);
    String uploadDocAtencion(MultipartFile file);

    //List<String> uploadList(MultipartFile files);

    void delete(String codigo);
    ArchivoBean download(String codigo);
    Long getIdByCodigo(String codigo);
}
