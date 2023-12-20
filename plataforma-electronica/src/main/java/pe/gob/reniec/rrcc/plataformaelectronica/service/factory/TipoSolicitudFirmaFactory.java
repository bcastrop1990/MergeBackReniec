package pe.gob.reniec.rrcc.plataformaelectronica.service.factory;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pe.gob.reniec.rrcc.plataformaelectronica.service.TipoSolicitudFirmaService;
import pe.gob.reniec.rrcc.plataformaelectronica.service.impl.TipoSolicitudFirmaActualizaServiceImpl;
import pe.gob.reniec.rrcc.plataformaelectronica.service.impl.TipoSolicitudFirmaAltaServiceImpl;
import pe.gob.reniec.rrcc.plataformaelectronica.service.impl.TipoSolicitudFirmaBajaServiceImpl;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.TipoSolicitudFirmaConstant;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class TipoSolicitudFirmaFactory {
  private ApplicationContext appContext;

  public TipoSolicitudFirmaService create(String tipo) {
    Map<String, TipoSolicitudFirmaService> instance = new HashMap<>();
    instance.put(TipoSolicitudFirmaConstant.ALTA, appContext.getBean(TipoSolicitudFirmaAltaServiceImpl.class));
    instance.put(TipoSolicitudFirmaConstant.BAJA, appContext.getBean(TipoSolicitudFirmaBajaServiceImpl.class));
    instance.put(TipoSolicitudFirmaConstant.ACTUALIZAR, appContext.getBean(TipoSolicitudFirmaActualizaServiceImpl.class));
    return instance.get(tipo);
  }
}
