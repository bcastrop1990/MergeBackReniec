package pe.gob.reniec.rrcc.plataformaelectronica.dao.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.TipoRegistroDao;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.TipoRegistroBean;

@Repository
@AllArgsConstructor
public class TipoRegistroDaoImpl implements TipoRegistroDao {
  private JdbcTemplate jdbcTemplate;
  private final static String SQL_SELECT ="SELECT ID_TIPO_REGISTRO as idTipoRegistro,\n" +
      "DE_DESCRIPCION as descripcion,CO_ESTADO as estado\n" +
      "FROM IDO_PLATAFORMA_EXPE.EDTM_SOL_TIPO_REGISTRO\n" +
      "WHERE CO_ESTADO = '1'";
  @Override
  public List<TipoRegistroBean> listar() {
    return jdbcTemplate.query(SQL_SELECT,
        BeanPropertyRowMapper.newInstance(TipoRegistroBean.class));
  }
}
