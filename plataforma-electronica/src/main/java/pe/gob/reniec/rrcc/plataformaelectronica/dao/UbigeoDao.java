package pe.gob.reniec.rrcc.plataformaelectronica.dao;

import java.util.List;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.UbigeoBean;

public interface UbigeoDao {
  List<UbigeoBean> listarUbigeo(String codigo);
}
