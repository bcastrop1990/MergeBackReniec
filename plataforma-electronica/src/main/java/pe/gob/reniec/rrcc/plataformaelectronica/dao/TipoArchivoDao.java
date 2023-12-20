package pe.gob.reniec.rrcc.plataformaelectronica.dao;

import java.util.List;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.TipoArchivoBean;

public interface TipoArchivoDao {
  List<TipoArchivoBean> listarPorTipoUso(String idTipoUso);
}
