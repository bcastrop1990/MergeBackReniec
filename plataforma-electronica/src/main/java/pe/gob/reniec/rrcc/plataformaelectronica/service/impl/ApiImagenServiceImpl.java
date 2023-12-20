package pe.gob.reniec.rrcc.plataformaelectronica.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pe.gob.reniec.rrcc.plataformaelectronica.config.ApiImagenesProperties;
import pe.gob.reniec.rrcc.plataformaelectronica.exception.ApiErrorException;
import pe.gob.reniec.rrcc.plataformaelectronica.exception.ApiValidateException;
import pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty.FirmaHuellaRequestDto;
import pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty.FirmaHuellaResponseDto;
import pe.gob.reniec.rrcc.plataformaelectronica.service.ApiImagenService;

import java.util.Base64;

@Service
@AllArgsConstructor
public class ApiImagenServiceImpl implements ApiImagenService {
  private ApiImagenesProperties apiImagenesProperties;
  private RestTemplate restTemplate;

  @Override
  public byte[] obtenerFotoPorDni(String dni) {
    String urlFoto = apiImagenesProperties.getUrlFoto().concat("/").concat(dni);
    try {
      ResponseEntity<byte[]> fotoResponse = restTemplate.getForEntity(urlFoto, byte[].class);
      return fotoResponse.getBody();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public FirmaHuellaResponseDto consultarPorDni(String dni) {
    String urlFirmaHuella = apiImagenesProperties.getUrlFirmaHuella();
    FirmaHuellaRequestDto firmaHuellaRequestDto = FirmaHuellaRequestDto.builder()
        .nuDni(Base64.getEncoder().encodeToString(dni.getBytes()))
        .build();
    try {
      ResponseEntity<FirmaHuellaResponseDto> firmaHuellaResponse = restTemplate.postForEntity(urlFirmaHuella,
          firmaHuellaRequestDto,
          FirmaHuellaResponseDto.class);
      return firmaHuellaResponse.getBody();
    } catch (Exception e) {
      return null;
    }
  }
}
