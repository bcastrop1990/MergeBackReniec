package pe.gob.reniec.rrcc.plataformaelectronica.model.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DetalleSolicitudLibroBean {
  private Long idDetalleSolLibro;
  private Long idSolicitud;
  private String codigoArticulo;
  private String codigoLengua;
  private int cantidad;
  private String numeroUltimaActa;
  private LenguaBean lenguaBean;
  private ArticuloBean articuloBean;
  private String idCrea;
  private LocalDateTime fechaCrea;
  private LocalDateTime fechaActualiza;
  private String idActualiza;
  private String estado;
}
