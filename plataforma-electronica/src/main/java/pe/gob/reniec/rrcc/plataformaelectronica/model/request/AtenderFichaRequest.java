package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AtenderFichaRequest {
  @NotNull(message = "El campo numeroSolicitud es requerido")
  private String numeroSolicitud;
}
