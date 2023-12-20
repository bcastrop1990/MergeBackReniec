package pe.gob.reniec.rrcc.plataformaelectronica.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaSolSegResponse {
  private String numeroSolicitud;
  private String fechaSolicitud;
  private String tipoRegistro;
  private String estadoSolicitud;
  private String codigoArchivoSustento;
  private String codigoArchivoRespuesta;
}
