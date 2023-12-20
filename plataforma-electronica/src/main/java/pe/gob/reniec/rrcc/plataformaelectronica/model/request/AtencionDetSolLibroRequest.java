package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AtencionDetSolLibroRequest {
  @NotNull(message = "el campo idDetalleSolLibro es requerido.")
  private Long idDetalleSolLibro;
  @NotNull(message = "el campo codigoArticulo es requerido.")
  private String codigoArticulo;
  @NotNull(message = "el campo codigoLengua es requerido.")
  private String codigoLengua;
  @NotNull(message = "el campo numeroUltimaActa es requerido.")
  private String numeroUltimaActa;
  @NotNull(message = "el campo cantidad es requerido.")
  private Integer cantidad;
}
