package pe.gob.reniec.rrcc.plataformaelectronica.controller;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.reniec.rrcc.plataformaelectronica.controller.mapper.RegistroLibroMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.BusqPorDatosRegCivilRuipinRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.SolicitudRegLibroRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ValidarDatosRegLibroRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ApiResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.BusqRegCivilRuipinResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.LenguaResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.service.RegistroFirmaService;
import pe.gob.reniec.rrcc.plataformaelectronica.service.RegistroLibroService;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("registro-libros")
@AllArgsConstructor
public class RegistroLibroController {

  private RegistroLibroService registroLibroService;
  private RegistroLibroMapper registroLibroMapper;
  private RegistroFirmaService registroFirmaService;

  @PostMapping("")
  public ResponseEntity<ApiResponse<String>> registrar(@Valid @RequestBody SolicitudRegLibroRequest request) {
    return ResponseEntity.ok(
        ApiResponse.<String>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(registroLibroService
                .registrar(registroLibroMapper
                    .RegLibroReqToSolLibroBean(request)))
            .build()
    );
  }

  @PostMapping("validar-datos")
  public ResponseEntity<ApiResponse<String>> validarDatos(@Valid @RequestBody ValidarDatosRegLibroRequest request) {
     return ResponseEntity.ok(
        ApiResponse.<String>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(registroLibroService.validarDatos(request))
            .build()
    );
  }
  @PostMapping("consultar-por-datos-ruipin")
  public ResponseEntity<ApiResponse<BusqRegCivilRuipinResponse>> consultarPorDatosRuipin(@RequestBody BusqPorDatosRegCivilRuipinRequest request) {
    return ResponseEntity.ok(registroFirmaService.consultarRegCivilPorDatosRuipin(request));
  }
}
