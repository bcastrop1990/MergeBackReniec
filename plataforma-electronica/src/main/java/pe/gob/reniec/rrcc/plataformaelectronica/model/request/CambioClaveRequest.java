package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CambioClaveRequest {
    @NotNull(message = "El campo usuario es requerido.")
    private String usuario;
    @NotNull(message = "El campo claveAnterior es requerido.")
    private String claveAnterior;
    @NotNull(message = "El campo claveNueva es requerido.")
    private String claveNueva;
    @NotNull(message = "El campo confirmaClaveNueva es requerido.")
    private String confirmaClaveNueva;
}
