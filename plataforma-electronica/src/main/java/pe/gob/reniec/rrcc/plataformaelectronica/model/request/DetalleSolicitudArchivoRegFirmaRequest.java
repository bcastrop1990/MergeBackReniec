package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DetalleSolicitudArchivoRegFirmaRequest {
  private ArchivoRequest archivo;
  private String codigoTipoArchivo;
}
