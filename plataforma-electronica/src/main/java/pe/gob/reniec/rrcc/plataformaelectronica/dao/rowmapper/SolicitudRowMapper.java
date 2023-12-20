package pe.gob.reniec.rrcc.plataformaelectronica.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.TipoArchivoBean;

public class SolicitudRowMapper implements RowMapper<SolicitudBean> {
  @Override
  public SolicitudBean mapRow(ResultSet rs, int i) throws SQLException {
    return SolicitudBean.builder()
        .idSolicitud(rs.getLong("ID_SOLICITUD"))
        .idTipoRegistro(rs.getString("ID_TIPO_REGISTRO"))
        .idTipoDocumentoSolicitante(rs.getString("CO_TIPO_DOC_IDENTI_SOLICITANTE"))
        .numeroDocumentoSolicitante(rs.getString("NU_DOC_IDENTIDAD_SOLICITANTE"))
        .primerApellido(rs.getString("AP_PRIMER_APELLIDO"))
        .segundoApellido(rs.getString("AP_SEGUNDO_APELLIDO"))
        .preNombres(rs.getString("NO_PRENOMBRES"))
        .celular(rs.getString("NU_CELULAR"))
        .email(rs.getString("DE_MAIL"))
        .numeroSolicitud(rs.getString("NU_SOLICITUD_NUMERO"))
        .fechaSolicitud(rs.getObject("FE_FECHA_SOLICITUD", LocalDateTime.class))
        .codigoEstado(rs.getString("CO_ESTADO_SOLICITUD"))
        .codigoOrec(rs.getString("CO_OREC_SOLICITUD"))
        .descripcionOrecLarga(rs.getString("DE_DETALLE_OREC_LARGA"))
        .descripcionOrecCorta(rs.getString("DE_DETALLE_OREC_CORTA"))
        .build();
  }
}
