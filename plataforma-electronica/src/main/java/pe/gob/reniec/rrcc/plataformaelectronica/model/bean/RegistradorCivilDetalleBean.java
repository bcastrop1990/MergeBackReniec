package pe.gob.reniec.rrcc.plataformaelectronica.model.bean;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistradorCivilDetalleBean {
  private Long idRegCivil;
  private String idTipoSolFirma;
  private String idTipoDocIdentidad;
  private String numeroDocIdentidad;
  private String primerApellido;
  private String segundoApellido;
  private String preNombre;
  private String codigoOrec;
  private String descripcionOrecCorta;
  private String estado;
  private String celular;
  private String email;
  private LocalDate fechaAlta;
  private LocalDate fechaBaja;
  private LocalDate fechaActualizacion;
  private LocalDate fechaInicio;
  private LocalDate fechaFin;
  private String codigoCondicion;
  private String codigoEstadoFormato;
  private String codigoCargoRegistrador;
  private String observacion;
  private OficinaBean oficina;
  private String descripcionUbigeoDetalle;
  private String codigoMotivoActualiza;
  private Long idDetSolFirma;
  private String codigoEstadoRegistrador;
  private String idCrea;
  private LocalDateTime fechaCrea;
  private LocalDateTime fechaActualiza;
  private String idActualiza;
}
