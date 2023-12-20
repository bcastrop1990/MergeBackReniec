package pe.gob.reniec.rrcc.plataformaelectronica.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pe.gob.reniec.rrcc.plataformaelectronica.config.ApiGestionUsuarioProperties;
import pe.gob.reniec.rrcc.plataformaelectronica.exception.ApiValidateException;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.PersonaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty.ApiResponseDto;
import pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty.CambioClaveRequestDto;
import pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty.GrupoPerfilRequestDto;
import pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty.GrupoPerfilResponseDto;
import pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty.LoginDniRequestDto;
import pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty.PermisoRequestDto;
import pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty.PermisoResponseDto;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.CambioClaveRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.LoginRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.security.BaseModel;
import pe.gob.reniec.rrcc.plataformaelectronica.security.JWTUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.security.UserInfo;
import pe.gob.reniec.rrcc.plataformaelectronica.security.SecurityUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.service.PersonaService;
import pe.gob.reniec.rrcc.plataformaelectronica.service.SeguridadService;

@Service
@AllArgsConstructor
public class SeguridadServiceImpl implements SeguridadService {

  private RestTemplate restTemplate;
  private ApiGestionUsuarioProperties properties;
  private PersonaService personaService;
  private JWTUtil jwtUtil;

  @Override
  public String identificarDni(LoginRequest login) {

    LoginDniRequestDto loginDniRequestDto = LoginDniRequestDto.builder()
        .coUsuario(login.getUsername())
        .clave(login.getPassword())
        .coAplicativo(properties.getCodigoAplicativo())
        .ip(properties.getIp())
        .build();

    HttpEntity<LoginDniRequestDto> loginEntity = new HttpEntity<>(loginDniRequestDto);
    ResponseEntity<ApiResponseDto> responseDto = restTemplate.exchange(
            properties.getUrlIdentificarDni(),
            HttpMethod.POST,
            loginEntity,
            ApiResponseDto.class);

    if (!Objects.equals(responseDto.getStatusCode(), HttpStatus.OK)) {
      throw new ApiValidateException("Servicio no disponible");
    }
    ApiResponseDto responseBody = responseDto.getBody();


    if (!Objects.equals(responseBody.getCoRespuesta(), "00")) {
      throw new ApiValidateException(responseBody.getDeRespuesta());
    }


    PersonaBean persona = personaService.buscarByDni(login.getUsername());
    UserInfo userInfo = mapToPersonInfo(persona, null);
    List<GrupoPerfilResponseDto> grupoPerfiles = obtenerGrupoPerfil(login);
    if (!grupoPerfiles.isEmpty()
        && grupoPerfiles.stream().anyMatch(gp -> properties.getGrupos().contains(gp.getCoGrupo()))) {
      GrupoPerfilResponseDto grupoPerfil = grupoPerfiles.stream().findFirst().get();
      userInfo.setGrupo(BaseModel.builder()
              .codigo(grupoPerfil.getCoGrupo())
              .descripcion(grupoPerfil.getDeGrupo())
          .build());
      userInfo.setPerfil(BaseModel.builder()
              .codigo(grupoPerfil.getCoPerfil())
              .descripcion(grupoPerfil.getDePerfil())
          .build());

      List<PermisoResponseDto> permisos = obtenerPermiso(login);
      userInfo.setPermisos(permisos.stream().map(p -> properties.getPermisos().get(p.getCoOpcion()))
          .collect(Collectors.toList()));
    }
    return jwtUtil.createExternalToken(userInfo);
  }

  @Override
  public String cambiarClave(CambioClaveRequest request) {

    if (!Objects.equals(request.getClaveNueva(), request.getConfirmaClaveNueva())) {
      throw new ApiValidateException("La nueva clave no coincide.");
    }

    CambioClaveRequestDto cambioClaveRequestDto = CambioClaveRequestDto.builder()
            .tiCodigo(properties.getTiCodigo())
            .coUsuario(request.getUsuario())
            .clave(request.getClaveAnterior())
            .nuevaClave(request.getClaveNueva())
            .coAplicativo(properties.getCodigoAplicativo())
            .ip(properties.getIp())
            .build();

    HttpEntity<CambioClaveRequestDto> cambioClaveEntity = new HttpEntity<>(cambioClaveRequestDto);
    ResponseEntity<ApiResponseDto> responseDto = restTemplate.exchange(
            properties.getUrlCambioClave(),
            HttpMethod.POST,
            cambioClaveEntity,
            ApiResponseDto.class);

    if (!Objects.equals(responseDto.getStatusCode(), HttpStatus.OK)) {
      throw new ApiValidateException("Servicio no disponible");
    }
    ApiResponseDto responseBody = responseDto.getBody();
    if (!Objects.equals(responseBody.getCoRespuesta(), "00")) {
      throw new ApiValidateException(responseBody.getDeRespuesta());
    }
    return responseBody.getDeRespuesta();
  }

  @Override
  public String refreshToken() {
    UserInfo userInfo = (UserInfo) SecurityUtil.getAuthentication().getPrincipal();
    return jwtUtil.createExternalToken(userInfo);
  }

  @Override
  public UserInfo getUserAuth() {
    return (UserInfo) SecurityUtil.getAuthentication().getPrincipal();
  }

  private UserInfo mapToPersonInfo(PersonaBean personaBean, String orec) {
    return UserInfo.builder()
        .dni(personaBean.getDni())
        .primerApellido(personaBean.getPrimerApellido())
        .segundoApellido(personaBean.getSegundoApellido())
        .preNombre(personaBean.getPreNombre())
        .codigoOrec(orec)
        .build();
  }
  private List<GrupoPerfilResponseDto> obtenerGrupoPerfil(LoginRequest login) {

    GrupoPerfilRequestDto requestDto = GrupoPerfilRequestDto.builder()
        .tiCodigo(properties.getTiCodigo())
        .coUsuario(login.getUsername())
        .clave(login.getPassword())
        .coAplicativo(properties.getCodigoAplicativo())
        .ip(properties.getIp())
        .build();

    HttpEntity<GrupoPerfilRequestDto> entity = new HttpEntity<>(requestDto);
    ResponseEntity<ApiResponseDto<List<GrupoPerfilResponseDto>>> responseDto = restTemplate.exchange(
        properties.getUrlObtenerGrupoPerfiles(),
        HttpMethod.POST,
        entity,
        new ParameterizedTypeReference<ApiResponseDto<List<GrupoPerfilResponseDto>>>() {});
    if (!Objects.equals(responseDto.getStatusCode(), HttpStatus.OK)) {
      throw new ApiValidateException("Servicio no disponible");
    }

    return responseDto.getBody().getResponse();
  }
  private List<PermisoResponseDto> obtenerPermiso(LoginRequest login) {

    PermisoRequestDto requestDto = PermisoRequestDto.builder()
        .tiCodigo(properties.getTiCodigo())
        .coUsuario(login.getUsername())
        .clave(login.getPassword())
        .coAplicativo(properties.getCodigoAplicativo())
        .ip(properties.getIp())
        .build();

    HttpEntity<PermisoRequestDto> entity = new HttpEntity<>(requestDto);
    ResponseEntity<ApiResponseDto<List<PermisoResponseDto>>> responseDto = restTemplate.exchange(
        properties.getUrlObtenerPermisos(),
        HttpMethod.POST,
        entity,
        new ParameterizedTypeReference<ApiResponseDto<List<PermisoResponseDto>>>(){});

    if (!Objects.equals(responseDto.getStatusCode(), HttpStatus.OK)) {
      throw new ApiValidateException("Servicio no disponible");
    }
    return responseDto.getBody().getResponse();
  }
}
