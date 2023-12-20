package pe.gob.reniec.rrcc.plataformaelectronica.model.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudEstadoBean {
  private String idSolicitudEstado;
  private String descripcion;
  private String estado;
}
