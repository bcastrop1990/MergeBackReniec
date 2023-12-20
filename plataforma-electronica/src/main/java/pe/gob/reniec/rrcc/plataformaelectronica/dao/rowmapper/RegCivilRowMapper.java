package pe.gob.reniec.rrcc.plataformaelectronica.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.OficinaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.RegistradorCivilBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RegCivilRowMapper implements RowMapper<RegistradorCivilBean> {
  @Override
  public RegistradorCivilBean mapRow(ResultSet rs, int i) throws SQLException {
    return RegistradorCivilBean.builder()
        .numeroDocIdentidad(rs.getString("NU_DOC_IDENTIDAD"))
        .primerApellido(rs.getString("AP_PRIMER_APELLIDO"))
        .segundoApellido(rs.getString("AP_SEGUNDO_APELLIDO"))
        .preNombre(rs.getString("NO_PRENOMBRES"))
        .celular(rs.getString("NU_CELULAR"))
        .email(rs.getString("DE_MAIL"))
        .descripcionOrecCorta(rs.getString("DE_DETALLE_OREC_CORTA"))
        .fechaCambioEstado(rs.getObject("FE_CAMBIO_ESTADO", LocalDate.class))
        .codigoOrec(rs.getString("CO_OREC_REG_CIVIL"))
        .idTipoSolFirma(rs.getString("ID_TIPO_SOLICITUD_FIRMA"))
        .estado(rs.getString("CO_ESTADO_REGISTRADOR"))
        .oficina(OficinaBean.builder()
            .nombreDepartamento(rs.getString("NO_DEPARTAMENTO"))
            .nombreProvincia(rs.getString("NO_PROVINCIA"))
            .nombreDistrito(rs.getString("NO_DISTRITO"))
            .descripcionUbigeoDetalle(rs.getString("DE_UBIGEO_DETALLE"))
            .build())
        .build();
  }
}
