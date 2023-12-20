package pe.gob.reniec.rrcc.plataformaelectronica.model.bean;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistradorCivilBean {
  private Long idRegCivil;
  private String idTipoSolFirma;
  private String idTipoDocIdentidad;
  private String numeroDocIdentidad;
  private String primerApellido;
  private String segundoApellido;
  private String preNombre;
  private String codigoOrec;
  private String descripcionOrecCorta;
  private String celular;
  private String email;
  private LocalDate fechaCambioEstado;
  private OficinaBean oficina;
  private String idCrea;
  private LocalDateTime fechaCrea;
  private LocalDateTime fechaActualiza;
  private String idActualiza;
  private String estado;
}
