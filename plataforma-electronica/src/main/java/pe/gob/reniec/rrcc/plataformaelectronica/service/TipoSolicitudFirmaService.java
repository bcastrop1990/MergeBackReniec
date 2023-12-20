package pe.gob.reniec.rrcc.plataformaelectronica.service;

import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.RegistradorCivilBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.RegistrarFichaRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ValidaRegCivilRequest;

import java.util.Optional;

public interface TipoSolicitudFirmaService {
  String validarSituacion(Optional<RegistradorCivilBean> registradorCivil, ValidaRegCivilRequest request);
  void registrarFicha(RegistrarFichaRequest request);
}
