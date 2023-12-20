package pe.gob.reniec.rrcc.plataformaelectronica.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.reniec.rrcc.plataformaelectronica.controller.mapper.OficinaMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ConsultaOficinaOrecRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ApiResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.DetalleOrecResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.OficinaOrecResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.service.OficinaService;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;

@RestController
@RequestMapping("oficinas")
@AllArgsConstructor
public class OficinaController {

  private OficinaService oficinaService;
  private OficinaMapper oficinaMapper;

  @PostMapping("orec")
  public ResponseEntity<ApiResponse<List<OficinaOrecResponse>>> consultarOrec(@Valid @RequestBody ConsultaOficinaOrecRequest request) {
    return ResponseEntity.ok(ApiResponse.<List<OficinaOrecResponse>>builder()
        .code(ConstantUtil.OK_CODE)
        .message(ConstantUtil.OK_MESSAGE)
        .data(oficinaService.consultar
                (oficinaMapper.oficinaOrecReqToOficnaBean(request))
            .stream().map(oficinaMapper::oficinaBeanToOficinaOrecRes)
            .collect(Collectors.toList()))
        .build());

  }

  @GetMapping("orec/detalle")
  public ResponseEntity<ApiResponse<DetalleOrecResponse>> consultarDetalleOrec() {
  
    return ResponseEntity.ok(ApiResponse.<DetalleOrecResponse>builder()
        .code(ConstantUtil.OK_CODE)
        .message(ConstantUtil.OK_MESSAGE)
        .data(oficinaMapper.oficinaBeanToDetalleOrecRes(
            oficinaService.obtenerOrec()))
        .build());

  }
}
