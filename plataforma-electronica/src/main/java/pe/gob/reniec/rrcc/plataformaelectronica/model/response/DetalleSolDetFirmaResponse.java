package pe.gob.reniec.rrcc.plataformaelectronica.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleSolDetFirmaResponse {
  private String idTipoSolicitud;
  private String tipoSolicitud;
  private String numeroDocumento;
  private String primerApellido;
  private String segundoApellido;
  private String preNombres;
  private String celular;
  private String email;
  private String codigoEstadoFirma;
  private List<ArchivoResponse> archivos;
}
