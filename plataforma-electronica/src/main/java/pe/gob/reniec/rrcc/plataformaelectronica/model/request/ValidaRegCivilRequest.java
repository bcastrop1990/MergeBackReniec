package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ValidaRegCivilRequest {
  @NotNull(message = "el campo dni es requerido.")
  private String dni;
  @NotNull(message = "el campo codigoOrec es requerido.")
  private String codigoOrec;
  @NotNull(message = "el campo idTipoSolicitudFirma es requerido.")
  private String idTipoSolicitudFirma;
}
