package pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FirmaHuellaResponseDto {
  private String coRespuestaWs;
  private String deRespuestaWs;
  private String imgFirma;
  private String imgHuellaDerecha;
  private String imgHuellaIzquierda;
}
