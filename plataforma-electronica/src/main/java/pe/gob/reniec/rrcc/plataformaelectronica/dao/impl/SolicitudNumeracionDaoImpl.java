package pe.gob.reniec.rrcc.plataformaelectronica.dao.impl;

import java.sql.PreparedStatement;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.SolicitudNumeracionDao;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudNumeracionBean;

@Repository
@AllArgsConstructor
public class SolicitudNumeracionDaoImpl implements SolicitudNumeracionDao {
  private JdbcTemplate jdbcTemplate;

  @Override
  public Optional<SolicitudNumeracionBean> obtenerPorPeriodo(int periodo) {
    String sql = "SELECT ID_SOLICITUD_NUMERACION as idSolicitudNumeracion,NU_PERIODO as periodo,\n" +
        " NU_LONGITUD as longitud, NU_CORRELATIVO as correlativo \n" +
        "FROM IDO_PLATAFORMA_EXPE.EDTX_SOLICITUD_NUMERACION \n" +
        "WHERE NU_PERIODO = ? AND CO_ESTADO = '1'";
    try {
      return Optional.of(jdbcTemplate.queryForObject(sql,
          BeanPropertyRowMapper.newInstance(SolicitudNumeracionBean.class),
          periodo));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public void incrementar(SolicitudNumeracionBean bean) {
    String sql = "UPDATE IDO_PLATAFORMA_EXPE.EDTX_SOLICITUD_NUMERACION \n" +
        " SET NU_CORRELATIVO = NU_CORRELATIVO + 1, ID_ACTUALIZA = ?, FE_ACTUALIZA = SYSDATE \n" +
        "WHERE ID_SOLICITUD_NUMERACION  = ?";
    jdbcTemplate.update(sql, bean.getIdActualiza(), bean.getIdSolicitudNumeracion());
  }

  @Override
  public void registrar(SolicitudNumeracionBean bean) {
    String sql = "INSERT INTO IDO_PLATAFORMA_EXPE.EDTX_SOLICITUD_NUMERACION(ID_SOLICITUD_NUMERACION,\n" +
        "NU_PERIODO, NU_LONGITUD, NU_CORRELATIVO,CO_ESTADO,ID_CREA, FE_CREA)\n" +
        "VALUES(IDO_PLATAFORMA_EXPE.EDSE_SOL_NUMERACION.nextval,?,?,?,?,?,SYSDATE)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
      PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID_SOLICITUD_NUMERACION"});
      ps.setInt(1, bean.getPeriodo());
      ps.setInt(2, bean.getLongitud());
      ps.setInt(3, bean.getCorrelativo());
      ps.setString(4, bean.getEstado());
      ps.setString(5, bean.getIdCrea());
      return ps;
    }, keyHolder);
    bean.setIdSolicitudNumeracion(keyHolder.getKey().intValue());
  }
}
