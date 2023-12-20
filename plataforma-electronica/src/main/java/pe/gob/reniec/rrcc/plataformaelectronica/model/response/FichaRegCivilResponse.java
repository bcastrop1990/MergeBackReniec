package pe.gob.reniec.rrcc.plataformaelectronica.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FichaRegCivilResponse {
  private DatoOficinaFichaResponse datoOficina;
  private DatoRegCivilFichaResponse datoRegCivil;
}
