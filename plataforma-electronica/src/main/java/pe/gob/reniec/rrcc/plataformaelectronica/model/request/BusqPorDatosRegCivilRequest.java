package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusqPorDatosRegCivilRequest {
    private String dni;
    private String primerApellido;
    private String segundoApellido;
    private String preNombres;
}
