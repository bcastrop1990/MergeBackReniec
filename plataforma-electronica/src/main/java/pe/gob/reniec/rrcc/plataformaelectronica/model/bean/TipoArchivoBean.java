package pe.gob.reniec.rrcc.plataformaelectronica.model.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoArchivoBean {
  private String idTipoArchivo;
  private String nombreArchivo;
  private String idTipoUso;
  private String estado;
}
