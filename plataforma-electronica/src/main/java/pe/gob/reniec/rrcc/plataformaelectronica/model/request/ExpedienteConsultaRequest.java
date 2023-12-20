package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpedienteConsultaRequest {
  private String numeroSolicitud;
  private String codigoEstado;
  private String codigoTipoRegistro;
  private String fechaIni;
  private String fechaFin;
  private String codigoDepartamento;
  private String codigoProvincia;
  private String codigoDistrito;
  private String codigoCentroPoblado;
  private String codigoAnalistaAsignado;
  private String codigoOrec;
}
