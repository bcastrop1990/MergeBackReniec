package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusqPorOficinaRegCivilRequest {
    private String codigoDepartamento;
    private String codigoProvincia;
    private String codigoDistrito;
    private String codigoOrec;
}
