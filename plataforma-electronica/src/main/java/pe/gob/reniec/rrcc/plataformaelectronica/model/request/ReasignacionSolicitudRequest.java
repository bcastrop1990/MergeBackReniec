package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReasignacionSolicitudRequest {
    @Size(min = 1, message = "La lista esta vacia.")
    private List<String> solicitudes;
    @NotNull(message = "El campo codigoAnalista es obligatorio.")
    private String codigoAnalista;
    private String dniCoordinador;

}