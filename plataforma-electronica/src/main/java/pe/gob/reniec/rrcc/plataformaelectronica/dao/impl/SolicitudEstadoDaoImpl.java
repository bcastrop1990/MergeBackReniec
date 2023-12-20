package pe.gob.reniec.rrcc.plataformaelectronica.dao.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.SolicitudEstadoDao;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudEstadoBean;

@Repository
@AllArgsConstructor
public class SolicitudEstadoDaoImpl implements SolicitudEstadoDao {
  private JdbcTemplate jdbcTemplate;
  private final static String SQL_SELECT ="SELECT ID_SOL_ESTADO as idSolicitudEstado,\n" +
      "DE_DESCRIPCION as descripcion,CO_ESTADO as estado\n" +
      "FROM IDO_PLATAFORMA_EXPE.EDTM_SOL_ESTADO\n" +
      "WHERE CO_ESTADO = '1'";
  @Override
  public List<SolicitudEstadoBean> listar() {
    return jdbcTemplate.query(SQL_SELECT,
        BeanPropertyRowMapper.newInstance(SolicitudEstadoBean.class));
  }
}
