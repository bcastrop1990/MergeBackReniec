package pe.gob.reniec.rrcc.plataformaelectronica.controller.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.AnalistaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudEstadoBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.TipoRegistroBean;

public class ConsultaGestionSolExpRowMapper implements RowMapper<SolicitudBean> {
    @Override
    public SolicitudBean mapRow(ResultSet rs, int i) throws SQLException {
        return SolicitudBean.builder()
                .tipoRegistro(TipoRegistroBean.builder()
                        .descripcion(rs.getString("DE_DESCRIPCION_TIPO_REG"))
                        .build())
                .numeroSolicitud(rs.getString("NU_SOLICITUD_NUMERO"))
                .fechaSolicitud(rs.getObject("FE_FECHA_SOLICITUD", LocalDateTime.class))
                .fechaRecepcion(rs.getObject("FE_FECHA_RECEPCION", LocalDateTime.class))
                .fechaAsignacion(rs.getObject("FE_FECHA_ASIGNACION", LocalDateTime.class))
                .fechaAtencion(rs.getObject("FE_FECHA_ATENCION", LocalDateTime.class))
                .descripcionOrecLarga(rs.getString("DE_DETALLE_OREC_LARGA"))
                .solicitudEstado(SolicitudEstadoBean.builder()
                        .descripcion(rs.getString("DE_DESCRIPCION_SOL_ESTADO"))
                        .build())
                .analistaAsignado(AnalistaBean.builder()
                        .preNombres(rs.getString("NO_PRENOMBRES_ANALISTA"))
                        .primerApellido(rs.getString("AP_PRIMER_APELLIDO_ANALISTA"))
                        .segundoApellido(rs.getString("AP_SEGUNDO_APELLIDO_ANALISTA"))
                        .build())
                .build();
    }
}