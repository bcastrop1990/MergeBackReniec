package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SolicitudRegLibroRequest {
  @NotNull(message = "El campo celular es requerido.")
  private String celular;
  private String email;
  private String codigoTipoArchivoSustento;

  @NotNull(message = "El campo archivoSustento es requerido")
  @Valid
  private List<ArchivoRequest> listArchivoSustento;

  @NotNull(message = "El campo codigoModoRegistro es requerido.")
  private String codigoModoRegistro;
  @NotNull(message = "El campo detalleSolicitud es requerido")
  @Size(min = 1, message = "La solicitud no tiene detalle.")
  @Valid
  private List<DetalleSolicitudRegLibroRequest> detalleSolicitud;
}
