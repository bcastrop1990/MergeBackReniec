package pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CambioClaveRequestDto {
    private String tiCodigo;
    private String coUsuario;
    private String clave;
    private String nuevaClave;
    private String coAplicativo;
    private String ip;
}
