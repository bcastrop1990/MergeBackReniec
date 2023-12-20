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
public class DetalleSolicitudFirmaBean {
  private Long idDetalleSolicitud;
  private Long idSolicitud;
  private String idTipoSolicitud;
  private String idTipoDocumento;
  private String numeroDocumento;
  private String primerApellido;
  private String segundoApellido;
  private String preNombres;
  private String celular;
  private String email;
  private String estado;
  private String codigoEstadoFirma;
 private TipoSolicitudRegFirmaBean tipoSolicitud;
  private List<DetalleSolicitudArchivoFirmaBean> detalleArchivo;
  private String idCrea;
  private LocalDateTime fechaCrea;
  private LocalDateTime fechaActualiza;
  private String idActualiza;
}
