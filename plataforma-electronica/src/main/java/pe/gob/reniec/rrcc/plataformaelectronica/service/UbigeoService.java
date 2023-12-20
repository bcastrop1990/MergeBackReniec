package pe.gob.reniec.rrcc.plataformaelectronica.service;

import java.util.List;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.UbigeoBean;

public interface UbigeoService {
  List<UbigeoBean> listarDepartamentos();
  List<UbigeoBean> listarProvincias(String idDepartamento);
  List<UbigeoBean> listarDistritos(String idDepartamento, String idProvincia);
  List<UbigeoBean> listarCentroPoblado(String idDepartamento, String idProvincia, String idDistrito);
}
