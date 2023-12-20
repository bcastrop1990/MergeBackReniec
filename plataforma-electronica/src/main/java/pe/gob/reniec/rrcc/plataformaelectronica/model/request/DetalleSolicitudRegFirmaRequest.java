package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DetalleSolicitudRegFirmaRequest {
  @NotNull(message = "el campo idTipoSolicitud es requerido.")
  private Integer idTipoSolicitud;
  @NotNull(message = "el campo numeroDocumento es requerido.")
  @Size(max = 8, message = "el campo numeroDocumento debe ser máximo de 8 caracteres")
  private String numeroDocumento;
  @NotNull(message = "el campo primerApellido es requerido.")
  @Size(max = 40, message = "el campo primerApellido debe ser máximo de 40 caracteres")
  private String primerApellido;
  @NotNull(message = "el campo segundoApellido es requerido.")
  @Size(max = 40, message = "el campo segundoApellido debe ser máximo de 40 caracteres")
  private String segundoApellido;
  @NotNull(message = "el campo preNombres es requerido.")
  @Size(max = 60, message = "el campo preNombres debe ser máximo de 60 caracteres")
  private String preNombres;
  @NotNull(message = "el campo celular es requerido.")
  @Size(min = 7, max = 12, message = "el campo celular debe ser de 7 a 12 caracteres.")
  private String celular;
  @NotNull(message = "el campo email es requerido.")
  @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "El email no es válido.")
  private String email;
  List<DetalleSolicitudArchivoRegFirmaRequest> detalleArchivo;

}
