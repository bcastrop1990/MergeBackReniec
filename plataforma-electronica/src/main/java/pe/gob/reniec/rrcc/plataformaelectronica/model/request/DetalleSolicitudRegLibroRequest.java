package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DetalleSolicitudRegLibroRequest {
  @NotNull(message = "el campo codigoArticulo es requerido.")
  private String codigoArticulo;
  @NotNull(message = "el campo codigoLengua es requerido.")
  private String codigoLengua;
  @NotNull(message = "el campo numeroUltimaActa es requerido.")
  @Size(min = 1, max = 15, message = "El campo numeroUltimaActa debe ser entre 1 y 15 caracteres.")
  private String numeroUltimaActa;
  @NotNull(message = "el campo cantidad es requerido.")
  private Integer cantidad;
}
