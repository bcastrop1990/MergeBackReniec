package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArchivoRequest {
  @NotNull(message = "El campo codigoNombre es requerido.")
  @Size(min = 1, message = "El campo codigoNombre es requerido.")
  private String codigoNombre;
  private String tipoCodigoNombre;
}
