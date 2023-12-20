package pe.gob.reniec.rrcc.plataformaelectronica.dao.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.AnalistaDao;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.AnalistaBean;


@Repository
@AllArgsConstructor
public class AnalistaDaoImpl implements AnalistaDao {
  private JdbcTemplate jdbcTemplate;
  private final static String SQL_SELECT ="SELECT CO_ANALISTA_ASIGNADO as codigoAnalista,\n" +
      "AP_PRIMER_APELLIDO as primerApellido,AP_SEGUNDO_APELLIDO as segundoApellido,\n" +
      "NO_PRENOMBRES as prenombres,CO_ESTADO as estado\n" +
      "FROM IDO_PLATAFORMA_EXPE.EDTM_ANALISTA\n" +
      "WHERE CO_ESTADO = '1'";

  @Override
  public List<AnalistaBean> listar() {
    return jdbcTemplate.query(SQL_SELECT,
        BeanPropertyRowMapper.newInstance(AnalistaBean.class));
  }
}
