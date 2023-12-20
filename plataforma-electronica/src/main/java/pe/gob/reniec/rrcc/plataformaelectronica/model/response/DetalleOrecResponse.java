package pe.gob.reniec.rrcc.plataformaelectronica.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DetalleOrecResponse {
  private String codigoOrec;
  private String descripcionLocalLarga;
  private String nombreDepartamento;
  private String nombreProvincia;
  private String nombreDistrito;
  private String descripcionCentroPoblado;
}
