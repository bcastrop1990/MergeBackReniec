package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ValidarDatosSeguimientoRequest {
  @NotNull(message = "El campo dni es requerido")
  private String dni;
  @NotNull(message = "El campo digitoVerifica es requerido")
  private String digitoVerifica;
  @NotNull(message = "El campo fechaEmision es requerido")
  @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))",
      message = "Formato de fecha inválida")
  private String fechaEmision;
  @NotNull(message = "El campo numeroSolicitud es requerido")
  private String numeroSolicitud;
}
