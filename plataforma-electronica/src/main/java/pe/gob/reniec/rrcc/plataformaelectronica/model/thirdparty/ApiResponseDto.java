package pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDto<T> {
  private String coRespuesta;
  private String deRespuesta;
  private T response;
}
