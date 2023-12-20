package pe.gob.reniec.rrcc.plataformaelectronica.model.response;

import java.util.List;
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
public class ApiPageResponse<T> {
  private String code;
  private String message;
  private int page;
  private int size;
  private int totalPage;
  private long totalElements;
  private int numberOfElements;
  private List<T> data;
}
