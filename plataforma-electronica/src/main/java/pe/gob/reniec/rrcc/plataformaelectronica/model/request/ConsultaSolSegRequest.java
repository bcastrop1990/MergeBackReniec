package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConsultaSolSegRequest {
  private String numeroSolicitud;
  private String fechaIni;
  private String fechaFin;
}
