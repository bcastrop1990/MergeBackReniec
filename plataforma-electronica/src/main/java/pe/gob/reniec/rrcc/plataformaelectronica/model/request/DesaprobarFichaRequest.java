package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DesaprobarFichaRequest {
  @NotNull(message = "El campo dni es requerido")
  private String dni;
  @NotNull(message = "El campo numeroSolicitud es requerido")
  private String numeroSolicitud;
  @NotNull(message = "El campo codigoNombreArchivo es requerido")
  private String codigoNombreArchivo;
  @NotNull(message = "El campo codigoEstadoFirma es requerido")
  private String codigoEstadoFirma;
}
