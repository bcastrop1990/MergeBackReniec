package pe.gob.reniec.rrcc.plataformaelectronica.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.numeracion")
@Getter
@Setter
public class NumeracionProperties {
  private Integer inicial;
  private Integer longitud;
}
