package pe.gob.reniec.rrcc.plataformaelectronica.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.OficinaDao;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.OficinaBean;

@Repository
@AllArgsConstructor
@Slf4j
public class OficinaDaoImpl implements OficinaDao {

  private JdbcTemplate jdbcTemplate;

  @Override
  public List<OficinaBean> consultar(OficinaBean bean) {
    StringBuilder sql = new StringBuilder();
    List<Object> parameters = new ArrayList<>();
    sql.append(" SELECT CO_OREC as codigoOrec, DE_LOCAL_LARGA as descripcionLocalLarga");
    sql.append(" , DE_LOCAL_CORTA as descripcionLocalCorta,DE_UBIGEO as descripcionUbigeo");
    sql.append(" , DE_UBIGEO_DETALLE as descripcionUbigeoDetalle");
    sql.append(" FROM IDORRCC.RCVM_OREC");
    sql.append(" WHERE CO_DEPARTAMENTO = TO_CHAR(?) ");
    parameters.add(bean.getCodigoDepartamento());

    if (StringUtils.hasLength(bean.getCodigoProvincia())) {
      parameters.add(bean.getCodigoProvincia());
      sql.append(" AND CO_PROVINCIA = TO_CHAR(?) ");
    }
    if (StringUtils.hasLength(bean.getCodigoDistrito())) {
      parameters.add(bean.getCodigoDistrito());
      sql.append(" AND CO_DISTRITO = TO_CHAR(?) ");
    }
    if (StringUtils.hasLength(bean.getCodigoCentroPoblado())) {
      parameters.add(bean.getCodigoCentroPoblado());
      sql.append(" AND CO_CENTRO_POBLADO = TO_CHAR(?) ");
    }

    return jdbcTemplate.query(sql.toString(),
        BeanPropertyRowMapper.newInstance(OficinaBean.class),
        parameters.toArray());
  }

  @Override
  public Optional<OficinaBean> obtener(String codigoOrec) {
    String sql = "SELECT CO_OREC as codigoOrec,DE_LOCAL_LARGA as descripcionLocalLarga,DE_LOCAL_CORTA as descripcionLocalCorta, \n" +
        "NO_DEPARTAMENTO as nombreDepartamento,NO_PROVINCIA as nombreProvincia,NO_DISTRITO as nombreDistrito,\n" +
        "DE_CENTRO_POBLADO as descripcionCentroPoblado, CO_DEPARTAMENTO as codigoDepartamento, CO_PROVINCIA as codigoProvincia, \n" +
        "CO_DISTRITO as codigoDistrito, CO_CENTRO_POBLADO as codigoCentroPoblado, DE_UBIGEO_DETALLE as descripcionUbigeoDetalle \n" +
        "FROM IDORRCC.RCVM_OREC WHERE CO_OREC = ?";
    try {
      return Optional.of(jdbcTemplate.queryForObject(sql,
          BeanPropertyRowMapper.newInstance(OficinaBean.class),
          codigoOrec));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }
}
