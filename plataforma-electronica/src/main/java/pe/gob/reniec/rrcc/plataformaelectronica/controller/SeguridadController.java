package pe.gob.reniec.rrcc.plataformaelectronica.controller;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.CambioClaveRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.LoginRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ApiResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ArchivoDownloadResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.service.SeguridadService;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;

@RestController
@RequestMapping("seguridad")
@AllArgsConstructor
public class SeguridadController {
  private SeguridadService seguridadService;

  @PostMapping("identificar")
  public ResponseEntity<ApiResponse<String>> identificar(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(
        ApiResponse.<String>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(seguridadService.identificarDni(request))
            .build()
    );
  }

  @PostMapping("cambio-clave")
  public ResponseEntity<ApiResponse<String>> cambiarClave(@Valid @RequestBody CambioClaveRequest request) {
    return ResponseEntity.ok(
            ApiResponse.<String>builder()
                    .code(ConstantUtil.OK_CODE)
                    .message(ConstantUtil.OK_MESSAGE)
                    .data(seguridadService.cambiarClave(request))
                    .build()
    );
  }

  @GetMapping("refresh-token")
  public ResponseEntity<ApiResponse<String>> refreshToken() {
    return ResponseEntity.ok(
        ApiResponse.<String>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(seguridadService.refreshToken())
            .build()
    );
  }
}
