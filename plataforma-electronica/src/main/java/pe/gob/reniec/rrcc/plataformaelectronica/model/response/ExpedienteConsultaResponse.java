package pe.gob.reniec.rrcc.plataformaelectronica.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpedienteConsultaResponse {
  private String numeroSolicitud;
  private String fechaSolicitud;
  private String tipoRegistro;
  private String oficinaAutorizada;
  private String fechaRecepcion;
  private String fechaAsignacion;
  private String fechaAtencion;
  private String estadoSolicitud;
  private String analistaAsignado;
}
