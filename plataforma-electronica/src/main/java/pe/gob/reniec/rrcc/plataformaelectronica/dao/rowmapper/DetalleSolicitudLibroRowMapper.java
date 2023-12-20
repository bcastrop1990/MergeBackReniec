package pe.gob.reniec.rrcc.plataformaelectronica.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.ArticuloBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.DetalleSolicitudLibroBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.LenguaBean;

public class DetalleSolicitudLibroRowMapper implements RowMapper<DetalleSolicitudLibroBean> {
  @Override
  public DetalleSolicitudLibroBean mapRow(ResultSet rs, int i) throws SQLException {
    return DetalleSolicitudLibroBean.builder()
        .idDetalleSolLibro(rs.getLong("ID_DET_SOL_LIBRO"))
        .cantidad(rs.getInt("NU_CANTIDAD"))
        .codigoLengua(rs.getString("CO_LENGUA"))
        .numeroUltimaActa(rs.getString("NU_NUM_ULTIMA_ACTA"))
        .idSolicitud(rs.getLong("ID_SOLICITUD"))
        .codigoArticulo(rs.getString("CO_ARTICULO"))
        .articuloBean(ArticuloBean.builder()
            .codigo(rs.getString("CO_ARTICULO"))
            .descripcion(rs.getString("DE_ARTICULO"))
            .build())
        .lenguaBean(LenguaBean.builder()
            .codigo(rs.getString("CO_LENGUA"))
            .descripcion(rs.getString("DE_LENGUA"))
            .build())
        .build();
  }
}
