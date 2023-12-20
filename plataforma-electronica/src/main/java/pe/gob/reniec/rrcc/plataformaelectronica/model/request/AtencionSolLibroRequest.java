package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AtencionSolLibroRequest {
  @NotNull(message = "El campo numeroSolicitud es requerido.")
  private String numeroSolicitud;
  @NotNull(message = "El campo codigoTipoArchivoRespuesta es requerido.")
  private String codigoTipoArchivoRespuesta;
  @NotNull(message = "El campo archivoSustento es requerido")
  @Valid
  private ArchivoRequest archivoRespuesta;
  @NotNull(message = "El campo detalleSolicitud es requerido")
  @Size(min = 1, message = "La solicitud no tiene detalle.")
  @Valid
  private List<AtencionDetSolLibroRequest> detalleSolicitud;

}
