package pe.gob.reniec.rrcc.plataformaelectronica.model.bean;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonaBean {
  private String dni;
  private String primerApellido;
  private String segundoApellido;
  private String preNombre;
  private String digitoVerifica;
  private String fechaEmision;
  private LocalDate fechaNacimiento;
}
