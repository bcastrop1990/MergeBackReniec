package pe.gob.reniec.rrcc.plataformaelectronica.service.impl;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.OficinaDao;
import pe.gob.reniec.rrcc.plataformaelectronica.exception.ApiValidateException;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.OficinaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.security.UserInfo;
import pe.gob.reniec.rrcc.plataformaelectronica.security.SecurityUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.service.OficinaService;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;

@Service
@AllArgsConstructor
@Slf4j
public class OficinaServiceImpl implements OficinaService {
  private OficinaDao oficinaDao;

  @Override
  public List<OficinaBean> consultar(OficinaBean oficinaBean) {
    List<OficinaBean> result = oficinaDao.consultar(oficinaBean);
    return result;
  }

  @Override
  public OficinaBean obtenerOrec() {
    UserInfo userInfo = (UserInfo)SecurityUtil.getAuthentication().getPrincipal();
    return oficinaDao.obtener(userInfo.getCodigoOrec())
        .orElseThrow(() -> new ApiValidateException(ConstantUtil.MSG_OREC_NO_EXISTE));
  }
}
