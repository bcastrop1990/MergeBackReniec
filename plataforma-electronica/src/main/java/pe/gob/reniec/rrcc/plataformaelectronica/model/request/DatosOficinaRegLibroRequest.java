package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DatosOficinaRegLibroRequest {
  @NotNull(message = "El codigo de Orec es requerido")
  private String codigoOrec;

}
