package pe.gob.reniec.rrcc.plataformaelectronica.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.RowMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.ArchivoBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudEstadoBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.TipoRegistroBean;

public class ConsultaSegSolRowMapper implements RowMapper<SolicitudBean> {
  @Override
  public SolicitudBean mapRow(ResultSet rs, int i) throws SQLException {
    return SolicitudBean.builder()
        .idSolicitud(rs.getLong("ID_SOLICITUD"))
        .idTipoRegistro(rs.getString("ID_TIPO_REGISTRO"))
        .tipoRegistro(TipoRegistroBean.builder()
            .descripcion(rs.getString("DE_DESCRIPCION_TIPO_REG"))
            .build())
        .numeroSolicitud(rs.getString("NU_SOLICITUD_NUMERO"))
        .fechaSolicitud(rs.getObject("FE_FECHA_SOLICITUD", LocalDateTime.class))
        .codigoEstado(rs.getString("CO_ESTADO_SOLICITUD"))
        .solicitudEstado(SolicitudEstadoBean.builder()
            .descripcion(rs.getString("DE_DESCRIPCION_SOL_ESTADO"))
            .build())
        .archivoSustento(ArchivoBean.builder()
            .codigoNombre(rs.getString("CO_NOMBRE_ARCH_SUSTENTO"))
            .build())
        .archivoRespuesta(ArchivoBean.builder()
            .codigoNombre(rs.getString("CO_NOMBRE_ARCH_RESPUESTA"))
            .build())
        .build();
  }
}
