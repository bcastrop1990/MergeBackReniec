package pe.gob.reniec.rrcc.plataformaelectronica.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusqRegCivilResponse {
    private String dni;
    private String primerApellido;
    private String segundoApellido;
    private String preNombres;
    private String descripcionOrec;
    private String nombreDepartamento;
    private String nombreProvincia;
    private String nombreDistrito;
    private String nombreCentroPoblado;
    private String celular;
    private String email;
}
