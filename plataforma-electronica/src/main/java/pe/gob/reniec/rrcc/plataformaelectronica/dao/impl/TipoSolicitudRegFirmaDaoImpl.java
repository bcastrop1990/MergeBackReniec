package pe.gob.reniec.rrcc.plataformaelectronica.dao.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.TipoSolicitudRegFrimaDao;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.TipoSolicitudRegFirmaBean;

@Repository
@AllArgsConstructor
public class TipoSolicitudRegFirmaDaoImpl implements TipoSolicitudRegFrimaDao {
  private JdbcTemplate jdbcTemplate;
  @Override
  public List<TipoSolicitudRegFirmaBean> listar() {
    String sql = "SELECT ID_TIPO_SOLICITUD_FIRMA AS codigo, DE_DESCRIPCION AS descripcion \n" +
        "FROM IDO_PLATAFORMA_EXPE.EDTM_TIPO_SOLICITUD_FIRMA";
    return jdbcTemplate.query(sql,
        BeanPropertyRowMapper.newInstance(TipoSolicitudRegFirmaBean.class));
  }
}
