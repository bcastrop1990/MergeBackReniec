package pe.gob.reniec.rrcc.plataformaelectronica.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.reniec.rrcc.plataformaelectronica.controller.mapper.ArchivoMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ApiResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ArchivoDownloadResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.service.ArchivoService;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;

import java.util.List;

@RestController
@RequestMapping("archivos")
@AllArgsConstructor
public class ArchivoController {
  private ArchivoService archivoService;
  private ArchivoMapper archivoMapper;

  @PostMapping(value = "upload")
  public ResponseEntity<ApiResponse<String>> upload(@RequestParam("file")  MultipartFile file) {
    return ResponseEntity.ok(
            ApiResponse.<String>builder()
                    .code(ConstantUtil.OK_CODE)
                    .message(ConstantUtil.OK_MESSAGE)
                    .data(archivoService.upload(file))
                    .build()
    );
  }
  @PostMapping(value = "uploadDocumentoAten")
  public ResponseEntity<ApiResponse<String>> uploadDocumentoAten(@RequestParam("file")  MultipartFile file) {
    return ResponseEntity.ok(
            ApiResponse.<String>builder()
                    .code(ConstantUtil.OK_CODE)
                    .message(ConstantUtil.OK_MESSAGE)
                    .data(archivoService.uploadDocAtencion(file))
                    .build()
    );
  }
  /*
  @PostMapping(value = "upload")
  public ResponseEntity<ApiResponse<List<String>>> upload(@RequestParam("file")  List<MultipartFile> files) {
    return ResponseEntity.ok(
            ApiResponse.<List<String>>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(archivoService.uploadList(files))
            .build()
    );
  }
  */

  //todo: verificar uso o eliminar
  @PostMapping(value = "delete")
  public ResponseEntity<ApiResponse<String>> delete(@RequestParam("file") MultipartFile file) {
    return ResponseEntity.ok(
            ApiResponse.<String>builder()
                    .code(ConstantUtil.OK_CODE)
                    .message(ConstantUtil.OK_MESSAGE)
                    .data(archivoService.upload(file))
                    .build()
    );
  }
  @DeleteMapping("{id}")
  public ResponseEntity<ApiResponse<String>> delete(@PathVariable String id) {
     archivoService.delete(id);
    return ResponseEntity.ok(
        ApiResponse.<String>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(id)
            .build()
    );
  }
  @GetMapping("{codigo}")
  public ResponseEntity<ApiResponse<ArchivoDownloadResponse>> download(@PathVariable String codigo) {
     return ResponseEntity.ok(
        ApiResponse.<ArchivoDownloadResponse>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(archivoMapper.archivoBeanToArchivoResponse(
                archivoService.download(codigo)))
            .build()
    );
  }

}
