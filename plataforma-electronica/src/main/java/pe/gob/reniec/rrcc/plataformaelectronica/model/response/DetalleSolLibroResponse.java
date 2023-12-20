package pe.gob.reniec.rrcc.plataformaelectronica.model.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleSolLibroResponse {
  private String codigoOrec;
  private String descripcionOrecLarga;
  private String ubigeo;
  private List<ArchivoResponse> archivoSustento;
  private List<DetalleSolDetLibroResponse> detalleSolicitudLibro = new ArrayList<>();
}
