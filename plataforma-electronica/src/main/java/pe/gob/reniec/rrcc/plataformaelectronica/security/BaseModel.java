package pe.gob.reniec.rrcc.plataformaelectronica.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseModel {
  private String codigo;
  private String descripcion;
}
