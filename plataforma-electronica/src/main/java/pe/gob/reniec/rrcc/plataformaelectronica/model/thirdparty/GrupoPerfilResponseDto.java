package pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty;

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
public class GrupoPerfilResponseDto {
  private String coGrupo;
  private String deGrupo;
  private String coPerfil;
  private String dePerfil;
}
