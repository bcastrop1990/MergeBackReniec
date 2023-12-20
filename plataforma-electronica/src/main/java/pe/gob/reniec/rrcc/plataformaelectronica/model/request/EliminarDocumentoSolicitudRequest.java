package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import java.util.List;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EliminarDocumentoSolicitudRequest {
    @Size(min = 1, message = "La lista esta vacia.")
    private List<String> codigoDocumento;
    private String codigoSolisitud;

}