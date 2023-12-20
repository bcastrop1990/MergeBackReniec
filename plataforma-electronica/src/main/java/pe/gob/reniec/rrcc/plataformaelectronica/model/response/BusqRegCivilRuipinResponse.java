package pe.gob.reniec.rrcc.plataformaelectronica.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusqRegCivilRuipinResponse {
    private String dni;
    private String primerApellido;
    private String segundoApellido;
    private String preNombres;
}
