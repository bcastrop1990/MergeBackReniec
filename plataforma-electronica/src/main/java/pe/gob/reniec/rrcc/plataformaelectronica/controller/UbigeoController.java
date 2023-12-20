package pe.gob.reniec.rrcc.plataformaelectronica.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.reniec.rrcc.plataformaelectronica.controller.mapper.UbigeoMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ApiResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.UbigeoResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.service.UbigeoService;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;

@RestController
@RequestMapping("ubigeos")
@AllArgsConstructor
public class UbigeoController {
  private UbigeoService ubigeoService;
  private UbigeoMapper ubigeoMapper;

  @GetMapping("departamentos")
  public ResponseEntity<ApiResponse<List<UbigeoResponse>>> listarDepartamentos() {
    return ResponseEntity.ok(
        ApiResponse.<List<UbigeoResponse>>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(ubigeoService.listarDepartamentos()
                .stream()
                .map(ubigeoMapper::ubigeoBeanToUbigeoRes)
                .collect(Collectors.toList()))
            .build());
  }

  @GetMapping("provincias")
  public ResponseEntity<ApiResponse<List<UbigeoResponse>>> listarProvincias(@RequestParam String idDepartamento) {
    return ResponseEntity.ok(ApiResponse.<List<UbigeoResponse>>builder()
        .code(ConstantUtil.OK_CODE)
        .message(ConstantUtil.OK_MESSAGE)
        .data(ubigeoService
            .listarProvincias(idDepartamento)
            .stream()
            .map(ubigeoMapper::ubigeoBeanToUbigeoRes)
            .collect(Collectors.toList()))
        .build());
  }

  @GetMapping("distritos")
  public ResponseEntity<ApiResponse<List<UbigeoResponse>>> listarDistritos(@RequestParam String idDepartamento,
                                                                           @RequestParam String idProvincia) {
    return ResponseEntity.ok(ApiResponse.<List<UbigeoResponse>>builder()
        .code(ConstantUtil.OK_CODE)
        .message(ConstantUtil.OK_MESSAGE)
        .data(ubigeoService
            .listarDistritos(idDepartamento, idProvincia)
            .stream()
            .map(ubigeoMapper::ubigeoBeanToUbigeoRes)
            .collect(Collectors.toList()))
        .build());
  }

  @GetMapping("centro-poblados")
  public ResponseEntity<ApiResponse<List<UbigeoResponse>>> listarCentroPoblados(@RequestParam String idDepartamento,
                                                                                @RequestParam String idProvincia,
                                                                                @RequestParam String idDistrito) {
    return ResponseEntity.ok(ApiResponse.<List<UbigeoResponse>>builder()
        .code(ConstantUtil.OK_CODE)
        .message(ConstantUtil.OK_MESSAGE)
        .data(ubigeoService
            .listarCentroPoblado(idDepartamento, idProvincia, idDistrito)
            .stream()
            .map(ubigeoMapper::ubigeoBeanToUbigeoRes)
            .collect(Collectors.toList()))
        .build());
  }
}
