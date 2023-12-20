package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DatosPersonaRegFirmaRequest {
  @NotNull(message = "El numero de documento es requerido")
  private String dni;
  @NotNull(message = "El digito de verificación es requerido")
  private String digitoVerifica;
  @NotNull(message = "La fecha de emisión es requerido")
  @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))",
      message = "Formato de fecha requerido yyyy-MM-dd")
  private String fechaEmision;
}
