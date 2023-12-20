package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ConsultaOficinaOrecRequest {
  @NotNull(message = "El codigo de departamento es obligatorio")
  private String codigoDepartamento;
  private String codigoProvincia;
  private String codigoDistrito;
  private String codigoCentroPoblado;
}
