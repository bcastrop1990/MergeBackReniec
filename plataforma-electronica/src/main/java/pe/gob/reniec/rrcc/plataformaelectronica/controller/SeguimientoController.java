package pe.gob.reniec.rrcc.plataformaelectronica.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.reniec.rrcc.plataformaelectronica.controller.mapper.SeguimientoMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ConsultaSolSegRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ValidarDatosSeguimientoRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ApiPageResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ApiResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ConsultaSolSegResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.SegSolDetFirmaResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.service.SeguimientoService;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;


@RestController
@RequestMapping("seguimientos")
@AllArgsConstructor
public class SeguimientoController {
  private SeguimientoService seguimientoService;
  private SeguimientoMapper seguimientoMapper;

  @PostMapping("validar-datos")
  public ResponseEntity<ApiResponse<String>> validarDatos(@Valid @RequestBody ValidarDatosSeguimientoRequest request) {
    return ResponseEntity.ok(
        ApiResponse.<String>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(seguimientoService.validarDatos(request))
            .build());
  }

  @PostMapping("solicitudes")
  public ResponseEntity<ApiPageResponse<ConsultaSolSegResponse>> consultaSolicitud(@Valid @RequestBody ConsultaSolSegRequest request,
                                                                                   @RequestParam(defaultValue = "1") int page,
                                                                                   @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.ok(seguimientoService.consultarSeguimiento(request, page, size));
  }

  @GetMapping("solicitudes/firma/{nroSolicitud}")
  public ResponseEntity<ApiResponse<List<SegSolDetFirmaResponse>>> seguimientoSolFirma(@PathVariable String nroSolicitud) {
    return ResponseEntity.ok(
        ApiResponse.<List<SegSolDetFirmaResponse>>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(seguimientoService.consultarSeguimientoSolFirma(nroSolicitud))
            .build());
  }
}
