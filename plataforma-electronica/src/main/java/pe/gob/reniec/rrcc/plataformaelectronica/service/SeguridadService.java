package pe.gob.reniec.rrcc.plataformaelectronica.service;

import pe.gob.reniec.rrcc.plataformaelectronica.model.request.CambioClaveRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.LoginRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.security.UserInfo;

public interface SeguridadService {
  String identificarDni(LoginRequest login);
  String cambiarClave(CambioClaveRequest request);
  String refreshToken();
  UserInfo getUserAuth();
}
