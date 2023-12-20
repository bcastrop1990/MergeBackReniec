package pe.gob.reniec.rrcc.plataformaelectronica.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.reniec.rrcc.plataformaelectronica.controller.mapper.MaestroMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.LenguaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ListarPorOficinaRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ValidaRegCivilRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.AnalistaResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ApiResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ArticuloResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.LenguaResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.SolEstadoResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.SolTipoRegistroResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.TipoArchivoResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.service.MaestroService;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("maestros")
@AllArgsConstructor
public class MaestroController {

  private MaestroService maestroService;
  private MaestroMapper maestroMapper;

  @GetMapping("tipo-archivos")
  public ResponseEntity<ApiResponse<List<TipoArchivoResponse>>> listarTipoArchivos(@RequestParam String idTipoUso) {
    return ResponseEntity.ok(
        ApiResponse.<List<TipoArchivoResponse>>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(maestroService.listarTipoArchivo(idTipoUso)
                .stream()
                .map(maestroMapper::tipoArchBeanToTipoArchRes)
                .collect(Collectors.toList()))
            .build());
  }

  @GetMapping("articulos")
  public ResponseEntity<ApiResponse<List<ArticuloResponse>>> listarArticulos() {
    return ResponseEntity.ok(
        ApiResponse.<List<ArticuloResponse>>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(maestroService.listarArticulos()
                .stream()
                .map(maestroMapper::articuloBeanToArticuloRes)
                .collect(Collectors.toList()))
            .build());
  }

  @GetMapping("lenguas")
  public ResponseEntity<ApiResponse<List<LenguaResponse>>> listarLenguas() {
    return ResponseEntity.ok(
        ApiResponse.<List<LenguaResponse>>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(maestroService.listarLenguas()
                .stream()
                .map(maestroMapper::lenguaBeanToLenguaRes)
                .collect(Collectors.toList()))
            .build());
  }
  @GetMapping("lenguas-por-oficina/{codigoOrec}")
  public ApiResponse<List<LenguaBean>> listarLenguasPorOficina(@PathVariable String codigoOrec) {
       List<LenguaBean> lenguas = maestroService.listarLenguasPorOficina(codigoOrec);
      return ApiResponse.<List<LenguaBean>>builder()
              .code(ConstantUtil.OK_CODE)
              .message(ConstantUtil.OK_MESSAGE)
              .data(lenguas)
              .build();
  }
  @GetMapping("sol-tipo-registros")
  public ResponseEntity<ApiResponse<List<SolTipoRegistroResponse>>> listarSolTipoRegistros() {
    return ResponseEntity.ok(
        ApiResponse.<List<SolTipoRegistroResponse>>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(maestroService.listarTipoRegistro()
                .stream()
                .map(maestroMapper::solTipoRegBeanToSolTipoRegResponse)
                .collect(Collectors.toList()))
            .build());
  }

  @GetMapping("sol-estados")
  public ResponseEntity<ApiResponse<List<SolEstadoResponse>>> listarSolEstados() {
    return ResponseEntity.ok(
        ApiResponse.<List<SolEstadoResponse>>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(maestroService.listarSolicitudEstado()
                .stream()
                .map(maestroMapper::solEstadoBeanToSolEstadoResponse)
                .collect(Collectors.toList()))
            .build());
  }

  @GetMapping("analistas")
  public ResponseEntity<ApiResponse<List<AnalistaResponse>>> listarAnalistas() {
    return ResponseEntity.ok(
        ApiResponse.<List<AnalistaResponse>>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(maestroService.listarAnalistas()
                .stream()
                .map(maestroMapper::analistaBeanToAnalistaResponse)
                .collect(Collectors.toList()))
            .build());
  }
}
