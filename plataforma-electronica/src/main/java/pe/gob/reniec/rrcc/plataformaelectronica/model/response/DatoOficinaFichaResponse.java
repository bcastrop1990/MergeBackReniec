package pe.gob.reniec.rrcc.plataformaelectronica.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatoOficinaFichaResponse {
  private String codigoOrec;
  private String descripcionORec;
  private String descripcionUbigeo;
}
