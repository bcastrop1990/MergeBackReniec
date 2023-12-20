package pe.gob.reniec.rrcc.plataformaelectronica.dao.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.TipoArchivoDao;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.TipoArchivoBean;

@Repository
@AllArgsConstructor
public class TipoArchivoDaoImpl implements TipoArchivoDao {
  private JdbcTemplate jdbcTemplate;
  private final String sql = "SELECT ID_TIPO_ARCHIVO as idTipoArchivo, DE_NOMBRE_ARCHIVO as nombreArchivo,\n" +
      " CO_TIPO_USO as idTipoUso, CO_ESTADO as estado \n" +
      " FROM IDO_PLATAFORMA_EXPE.EDTV_TIPO_ARCHIVO WHERE CO_TIPO_USO = ? AND CO_ESTADO = '1'";
  @Override
  public List<TipoArchivoBean> listarPorTipoUso(String idTipoUso) {
    return jdbcTemplate.query(sql,
        BeanPropertyRowMapper.newInstance(TipoArchivoBean.class),
        idTipoUso);
  }
}
