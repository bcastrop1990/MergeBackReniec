package pe.gob.reniec.rrcc.plataformaelectronica.dao.impl;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.PersonaDao;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.PersonaBean;

@Repository
@AllArgsConstructor
public class PersonaDaoImpl implements PersonaDao {
  private JdbcTemplate jdbcTemplate;
  private final String SELECT_SQL = "SELECT NU_DNI as dni, AP_PRIMER as primerApellido, AP_SEGUNDO as segundoApellido" +
      ", PRENOM_INSCRITO as preNombre \n" +
      "FROM IDOTABMAESTRA.GETM_ANI_RC \n";

  @Override
  public Optional<PersonaBean> validarPersona(String dni, String digitoVerifica, String fechaEmision) {
    String sql = SELECT_SQL + " WHERE NU_DNI = ? AND DIGITO_VERIFICACION = ? AND TO_CHAR(FE_EMISION,'YYYY-MM-DD') = ? ";
    try {
      return Optional.of(jdbcTemplate.queryForObject(sql,
          BeanPropertyRowMapper.newInstance(PersonaBean.class),
          dni, digitoVerifica, fechaEmision));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<PersonaBean> buscarByDni(String dni) {
    String sql = SELECT_SQL + " WHERE NU_DNI = ?  ";
    try {
      return Optional.of(jdbcTemplate.queryForObject(sql,
          BeanPropertyRowMapper.newInstance(PersonaBean.class),
          dni));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }
}
