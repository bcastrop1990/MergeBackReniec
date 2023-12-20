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
public class SolicitudLibroResponse {
  private String codigoOrec;
  private String descripcionOrecLarga;
  private String ubigeo;
  private List<SolDetalleLibroResponse> detalleSolicitudLibro = new ArrayList<>();
}
