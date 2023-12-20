package pe.gob.reniec.rrcc.plataformaelectronica.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.PersonaDao;
import pe.gob.reniec.rrcc.plataformaelectronica.exception.ApiValidateException;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.PersonaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.service.PersonaService;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;

@Service
@AllArgsConstructor
public class PersonaServiceImpl implements PersonaService {
  private PersonaDao personaDao;
  @Override
  public PersonaBean validarDatos(PersonaBean bean) {
    PersonaBean persona = personaDao.validarPersona(bean.getDni(),
            bean.getDigitoVerifica(), bean.getFechaEmision())
        .orElseThrow(() -> new ApiValidateException(ConstantUtil.MSG_PERSONA_NO_ENCONTRADA));
    return persona;
  }

  @Override
  public PersonaBean buscarByDni(String dni) {
    PersonaBean persona = personaDao.buscarByDni(dni)
        .orElseThrow(() -> new ApiValidateException(ConstantUtil.MSG_PERSONA_NO_ENCONTRADA));
    return persona;
  }
}
