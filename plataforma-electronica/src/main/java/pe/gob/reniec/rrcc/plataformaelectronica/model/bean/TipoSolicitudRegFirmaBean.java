package pe.gob.reniec.rrcc.plataformaelectronica.model.bean;

import java.time.LocalDateTime;
import java.util.List;
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
public class TipoSolicitudRegFirmaBean {
  private String codigo;
  private String descripcion;
}
