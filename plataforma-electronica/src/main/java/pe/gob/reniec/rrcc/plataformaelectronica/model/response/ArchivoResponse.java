package pe.gob.reniec.rrcc.plataformaelectronica.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArchivoResponse {
    private String tipoArchivo;
    private String nombreOriginal;
    private String codigo;
}
