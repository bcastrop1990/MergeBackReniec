package pe.gob.reniec.rrcc.plataformaelectronica.service;

import java.util.List;

import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.OficinaBean;

public interface OficinaService {
  List<OficinaBean> consultar(OficinaBean oficinaBean);
  OficinaBean obtenerOrec();
}
