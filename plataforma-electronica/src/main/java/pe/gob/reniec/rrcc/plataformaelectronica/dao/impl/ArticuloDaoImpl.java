package pe.gob.reniec.rrcc.plataformaelectronica.dao.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.ArticuloDao;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.ArticuloBean;

@Repository
@AllArgsConstructor
public class ArticuloDaoImpl implements ArticuloDao {
  private JdbcTemplate jdbcTemplate;
  private final String sql = "SELECT CO_ARTICULO as codigo, DE_ARTICULO as descripcion \n" +
      " FROM IDORRCC.RCTR_ARTICULO WHERE ES_ARTICULO = '1'";

  @Override
  public List<ArticuloBean> listar() {
    return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(ArticuloBean.class));
  }
}
