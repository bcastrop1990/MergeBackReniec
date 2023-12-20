package pe.gob.reniec.rrcc.plataformaelectronica.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.*;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.*;
import pe.gob.reniec.rrcc.plataformaelectronica.service.RegistradorCivilService;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("registrador-civil")
@AllArgsConstructor
public class RegistradorCivilController {
    private RegistradorCivilService regCivilService;
    @PostMapping("consultar-por-oficina")
    public ResponseEntity<ApiPageResponse<BusqRegCivilResponse>> consultarPorOficina(@RequestBody BusqPorOficinaRegCivilRequest request,
                                                                                     @RequestParam(defaultValue = "0") int page,
                                                                                     @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(regCivilService.consultarRegCivilPorOficina(request, page, size));
    }

    @PostMapping("consultar-por-datos")
    public ResponseEntity<ApiPageResponse<BusqRegCivilResponse>> consultarPorDatos(@RequestBody BusqPorDatosRegCivilRequest request,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(regCivilService.consultarRegCivilPorDatos(request, page, size));
    }

    @PostMapping("consultar-por-datos-ruipin")
    public ResponseEntity<BusqRegCivilRuipinResponse> consultarPorDatosRuipin(@RequestBody BusqPorDatosRegCivilRuipinRequest request) {
        return ResponseEntity.ok(regCivilService.consultarRegCivilPorDatosRuipin(request));
    }

    @PostMapping("validar-datos")
    public ResponseEntity<ApiResponse<Void>> consultarRegCivilDatos(@Valid @RequestBody ValidaRegCivilRequest request) {
        regCivilService.validarSituacion(request);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                .build());
    }

    @GetMapping("ficha")
    public ResponseEntity<ApiResponse<FichaRegCivilResponse>> consultarFicha(@RequestParam String dni,
                                                                             @RequestParam String nroSolicitud) {
        return ResponseEntity.ok(ApiResponse.<FichaRegCivilResponse>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .data(regCivilService.consultarFicha(dni, nroSolicitud))
            .build());
    }
    @PostMapping("ficha")
    public ResponseEntity<ApiResponse<Void>> registrarFicha(@Valid @RequestBody RegistrarFichaRequest request) {
        regCivilService.registrarFicha(request);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .build());
    }

    @PostMapping("ficha/desaprobar")
    public ResponseEntity<ApiResponse<Void>> desaprobarFicha(@Valid @RequestBody DesaprobarFichaRequest request) {
        regCivilService.desaprobarFicha(request);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .build());
    }

    @PostMapping("ficha/atender")
    public ResponseEntity<ApiResponse<Void>> atenderFicha(@Valid @RequestBody AtenderFichaRequest request) {
        regCivilService.atenderFicha(request);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .build());
    }

    @GetMapping("consultar/{dni}/ficha")
    public ResponseEntity<ApiResponse<ConsultaFichaByDniResponse>> consultarFichaPorDni(@PathVariable String dni) {
        return ResponseEntity.ok(ApiResponse.<ConsultaFichaByDniResponse>builder()
            .data(regCivilService.consultaFichaByDni(dni))
            .code(ConstantUtil.OK_CODE)
            .message(ConstantUtil.OK_MESSAGE)
            .build());
    }

    @GetMapping("consultar/{dni}")
    public ResponseEntity<ApiResponse<ConsultaFichaByDniResponse>> consultarPorDni(@PathVariable String dni) {
        return ResponseEntity.ok(
                ApiResponse.<ConsultaFichaByDniResponse>builder()
                    .data(regCivilService.consultaRegCivilByDni(dni))
                    .code(ConstantUtil.OK_CODE)
                    .message(ConstantUtil.OK_MESSAGE)
                    .build()
        );
    }
}
