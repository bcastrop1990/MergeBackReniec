package pe.gob.reniec.rrcc.plataformaelectronica.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GestionConsultaSolResponse {
  private String numeroSolicitud;
  private String fechaSolicitud;
  private String tipoRegistro;
  private String oficinaAutorizada;
  private String fechaRecepcion;
  private String fechaAsignacion;
  private String fechaAtencion;
  private String estadoSolicitud;
  private String analistaAsignado;
  private String dniSolicitante;
  private String codigoAnalistaAsignado;
}
