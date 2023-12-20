package pe.gob.reniec.rrcc.plataformaelectronica.service.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.UbigeoDao;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.UbigeoBean;
import pe.gob.reniec.rrcc.plataformaelectronica.service.UbigeoService;

@Service
@AllArgsConstructor
@Slf4j
public class UbigeoServiceImpl implements UbigeoService {
  private UbigeoDao ubigeoDao;
  @Override
  public List<UbigeoBean> listarDepartamentos() {
    return this.listar(StringUtils.EMPTY);
  }

  @Override
  public List<UbigeoBean> listarProvincias(String idDepartamento) {
    return this.listar(idDepartamento);
  }

  @Override
  public List<UbigeoBean> listarDistritos(String idDepartamento, String idProvincia) {
    return this.listar(idDepartamento.concat(idProvincia));
  }

  @Override
  public List<UbigeoBean> listarCentroPoblado(String idDepartamento, String idProvincia, String idDistrito) {
    return this.listar(idDepartamento.concat(idProvincia).concat(idDistrito));
  }

  private List<UbigeoBean> listar(String codigo) {
    return ubigeoDao.listarUbigeo(codigo);
  }
}
