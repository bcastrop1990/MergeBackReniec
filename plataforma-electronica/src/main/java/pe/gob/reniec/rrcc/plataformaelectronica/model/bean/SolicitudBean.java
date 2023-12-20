package pe.gob.reniec.rrcc.plataformaelectronica.model.bean;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SolicitudBean {
  private Long idSolicitud;
  private String idTipoRegistro;
  private String idTipoDocumentoSolicitante;
  private String numeroDocumentoSolicitante;
  private String primerApellido;
  private String segundoApellido;
  private String preNombres;
  private String celular;
  private String email;
  private String codigoOrec;
  private String descripcionOrecCorta;
  private String descripcionOrecLarga;
  private String codigoDepartamentoOrec;
  private String codigoProvinciaOrec;
  private String codigoDistritoOrec;
  private String codigoCentroPobladoOrec;
  private LocalDateTime fechaSolicitud;
  private LocalDateTime fechaRecepcion;
  private LocalDateTime fechaAtencion;
  private LocalDateTime fechaAsignacion;
  private String codigoUsuarioRecepcion;
  private String codigoEstado;
  private String codigoAnalistaAsignado;
  private String codigoTipoArchivoSustento;
  private List<Long> listIdArchivoSustento;
  private Long IdArchivoSustento;
  private String codigoTipoArchivoRespuesta;
  private Long idArchivoRespuesta;
  private String codigoModoRegistro;
  private String numeroSolicitud;
  private TipoArchivoBean tipoArchivoSustento;
  private List<ArchivoBean> listArchivoSustento;
  private ArchivoBean archivoSustento;
  private ArchivoBean archivoRespuesta;
  private List<DetalleSolicitudFirmaBean> detalleSolicitudFirma;
  private List<DetalleSolicitudLibroBean> detalleSolicitudLibro;
  private TipoRegistroBean tipoRegistro;
  private SolicitudEstadoBean solicitudEstado;
  private AnalistaBean analistaAsignado;
  private String idCrea;
  private LocalDateTime fechaCrea;
  private LocalDateTime fechaActualiza;
  private String idActualiza;
  private String estado;
}
