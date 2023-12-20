package pe.gob.reniec.rrcc.plataformaelectronica.model.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudNumeracionBean {
  private Integer idSolicitudNumeracion;
  private int correlativo;
  private int longitud;
  private int periodo;
  private String idCrea;
  private LocalDateTime fechaCrea;
  private LocalDateTime fechaActualiza;
  private String idActualiza;
  private String estado;
}
