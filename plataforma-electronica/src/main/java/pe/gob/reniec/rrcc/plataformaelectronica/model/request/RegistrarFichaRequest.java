package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegistrarFichaRequest {
  @NotNull(message = "El campo idTipoSolicitudFirma es requerido")
  private String codigoOrec;
  private String primerApellido;
  private String segundoApellido;
  private String preNombres;
  private String dni;
  private String email;
  private String celular;
  private String codigoCondicion;
  private String codigoEstadoRegistrador;
  private String fechaAlta;
  private String fechaBaja;
  private String codigoCargo;
  @NotNull(message = "El campo idTipoSolicitudFirma es requerido")
  private String idTipoSolicitudFirma;
  private String codigoEstadoFormato;
  private String fechaInicio;
  private String fechaFin;
  private String codigoNombreArchivo;
  public String codigoEstadoFirma;
  private String numeroSolicitud;
  private String codigoMotivoActualizacion;
}
