package pe.gob.reniec.rrcc.plataformaelectronica.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleSolFirmaResponse {
  private String codigoOrec;
  private String descripcionOrecLarga;
  private String ubigeo;
  private List<ArchivoResponse> archivoSustento;
  private List<DetalleSolDetFirmaResponse> detalleSolicitudFirma = new ArrayList<>();
}
