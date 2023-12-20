package pe.gob.reniec.rrcc.plataformaelectronica.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.UbigeoBean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UbigeoRowMpper implements RowMapper<UbigeoBean> {
  @Override
  public UbigeoBean mapRow(ResultSet rs, int i) throws SQLException {
    return UbigeoBean.builder()
        .codigo(rs.getString("CO_UBIGEO"))
        .descripcion(rs.getString("DE_UBIGEO"))
        .build();
  }
}
