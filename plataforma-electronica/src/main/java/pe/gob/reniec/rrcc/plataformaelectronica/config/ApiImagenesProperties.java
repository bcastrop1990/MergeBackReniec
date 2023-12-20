package pe.gob.reniec.rrcc.plataformaelectronica.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties("application.ws-imagenes")
@Getter
@Setter
public class ApiImagenesProperties {
  private String urlFoto;
  private String urlFirmaHuella;
}
