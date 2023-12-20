package pe.gob.reniec.rrcc.plataformaelectronica.model.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleSolicitudArchivoFirmaBean {

  private Long idDetalleSolicitudArchivo;
  private Long idDetalleSolicitud;
  private Long idArchivo;
  private ArchivoBean archivo;
  private String codigoTipoArchivo;
  private TipoArchivoBean tipoArchivo;
  private String codigoUsoArchivo;
  private String idCrea;
  private LocalDateTime fechaCrea;
  private LocalDateTime fechaActualiza;
  private String idActualiza;
  private String estado;
  private Long id_solicitud;
}
