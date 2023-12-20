package pe.gob.reniec.rrcc.plataformaelectronica.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.RegistradorCivilRuipinBean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaRegCivilRuipinRowMapper implements RowMapper<RegistradorCivilRuipinBean> {
    @Override
    public RegistradorCivilRuipinBean mapRow(ResultSet rs, int i) throws SQLException {
        return RegistradorCivilRuipinBean.builder()
                .numeroDocIdentidad(rs.getString("NU_DNI"))
                .primerApellido(rs.getString("AP_PRIMER"))
                .segundoApellido(rs.getString("AP_SEGUNDO"))
                .preNombre(rs.getString("PRENOM_INSCRITO"))
                .build();
    }
}
