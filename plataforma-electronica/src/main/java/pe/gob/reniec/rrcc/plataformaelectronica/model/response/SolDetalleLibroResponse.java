package pe.gob.reniec.rrcc.plataformaelectronica.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolDetalleLibroResponse {
  private Long idDetalleSolLibro;
  private String codigoArticulo;
  private String codigoLengua;
  private int cantidad;
  private String numeroUltimaActa;
}
