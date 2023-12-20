package pe.gob.reniec.rrcc.plataformaelectronica.service;

import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.PersonaBean;

public interface PersonaService {
  PersonaBean validarDatos(PersonaBean bean);
  PersonaBean buscarByDni(String dni);
}
