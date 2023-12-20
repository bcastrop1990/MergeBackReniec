package pe.gob.reniec.rrcc.plataformaelectronica.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.UbigeoDao;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.rowmapper.*;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.UbigeoBean;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class UbigeoDaoImpl implements UbigeoDao {
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<UbigeoBean> listarUbigeo(String codigo) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
            .withCatalogName("IDO_PLATAFORMA_EXPE")
            .withProcedureName("EDSP_UBIGEO_LISTAR")
            .withoutProcedureColumnMetaDataAccess()
            .declareParameters(
                    new SqlParameter("P_VCOD_UBIGEO", Types.VARCHAR),
                    new SqlOutParameter("C_CRRESULT", Types.REF_CURSOR,
                            new UbigeoRowMpper()));
    SqlParameterSource prm = new MapSqlParameterSource()
            .addValue("P_VCOD_UBIGEO", codigo);
    Map<String, Object> result = simpleJdbcCall.execute(prm);
    List<UbigeoBean> beanList = (List) result.get("C_CRRESULT");
    return beanList;
  }
}
