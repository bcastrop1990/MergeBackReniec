package pe.gob.reniec.rrcc.plataformaelectronica.dao;

import java.util.List;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.ArticuloBean;

public interface ArticuloDao {
  List<ArticuloBean> listar();
}
