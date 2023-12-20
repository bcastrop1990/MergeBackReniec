package pe.gob.reniec.rrcc.plataformaelectronica.dao;

import java.util.List;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.LenguaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ListarPorOficinaRequest;

public interface LenguaDao {
  List<LenguaBean> listar();
  List<LenguaBean> listarLenguasPorOficina(String  codigoOrec);

}
