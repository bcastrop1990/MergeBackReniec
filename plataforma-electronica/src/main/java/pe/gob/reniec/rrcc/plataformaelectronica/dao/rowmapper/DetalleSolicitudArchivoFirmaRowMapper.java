package pe.gob.reniec.rrcc.plataformaelectronica.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.ArchivoBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.DetalleSolicitudArchivoFirmaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.TipoArchivoBean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DetalleSolicitudArchivoFirmaRowMapper implements RowMapper<DetalleSolicitudArchivoFirmaBean> {
  @Override
  public DetalleSolicitudArchivoFirmaBean mapRow(ResultSet rs, int i) throws SQLException {
    return DetalleSolicitudArchivoFirmaBean.builder()
        .idDetalleSolicitudArchivo(rs.getLong("ID_ADJ_SOL_FIRMA"))
        .idDetalleSolicitud(rs.getLong("ID_DET_SOL_FIRMA"))
        .archivo(ArchivoBean.builder()
            .id(rs.getLong("ID_ARCHIVO"))
            .codigoNombre(rs.getString("CO_NOMBRE"))
            .nombreOriginal(rs.getString("DE_NOMBRE_ORIGINAL"))
            .extension(rs.getString("CO_EXTENSION"))
            .build())
        .tipoArchivo(TipoArchivoBean.builder()
            .idTipoArchivo(rs.getString("CO_TIPO_ARCHIVO"))
            .nombreArchivo(rs.getString("DE_NOMBRE_ARCHIVO"))
            .build())
        .codigoUsoArchivo(rs.getString("CO_USO_ARCHIVO"))
        .build();
  }
}
