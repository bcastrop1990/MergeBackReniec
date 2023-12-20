package pe.gob.reniec.rrcc.plataformaelectronica.dao;

import java.util.List;
import java.util.Optional;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.OficinaBean;

public interface OficinaDao {
  List<OficinaBean> consultar(OficinaBean bean);
  Optional<OficinaBean> obtener(String codigoOrec);
}
