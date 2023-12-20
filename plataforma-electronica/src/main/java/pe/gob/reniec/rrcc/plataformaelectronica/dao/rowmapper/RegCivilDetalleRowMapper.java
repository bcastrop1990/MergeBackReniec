package pe.gob.reniec.rrcc.plataformaelectronica.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.RegistradorCivilDetalleBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RegCivilDetalleRowMapper implements RowMapper<RegistradorCivilDetalleBean> {
    @Override
    public RegistradorCivilDetalleBean mapRow(ResultSet rs, int i) throws SQLException {
        return RegistradorCivilDetalleBean.builder()
            .numeroDocIdentidad(rs.getString("NU_DOC_IDENTIDAD"))
            .primerApellido(rs.getString("AP_PRIMER_APELLIDO"))
            .segundoApellido(rs.getString("AP_SEGUNDO_APELLIDO"))
            .preNombre(rs.getString("NO_PRENOMBRES"))
            .celular(rs.getString("NU_CELULAR"))
            .email(rs.getString("DE_MAIL"))
            .descripcionOrecCorta(rs.getString("DE_DETALLE_OREC_CORTA"))
            .fechaAlta(rs.getObject("FE_FECHA_ALTA", LocalDate.class))
            .fechaBaja(rs.getObject("FE_FECHA_BAJA", LocalDate.class))
            .fechaActualizacion(rs.getObject("FE_FECHA_ACTUALIZACION", LocalDate.class))
            .codigoCondicion(rs.getString("CO_CONDICION"))
            .codigoEstadoFormato(rs.getString("CO_ESTADO_FORMATO"))
            .codigoCargoRegistrador(rs.getString("CO_CARGO_REGISTRADOR"))
            .codigoEstadoRegistrador(rs.getString("CO_ESTADO_REGISTRADOR"))
            .idDetSolFirma(rs.getLong("ID_DET_SOL_FIRMA"))
            .idTipoSolFirma(rs.getString("ID_TIPO_SOLICITUD_FIRMA"))
            .build();
    }
}
