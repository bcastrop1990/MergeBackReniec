package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GestionConsultaSolReporteRequest {
  private String numeroDocIdentidad;
  private String primerApellido;
  private String segundoApellido;

}
