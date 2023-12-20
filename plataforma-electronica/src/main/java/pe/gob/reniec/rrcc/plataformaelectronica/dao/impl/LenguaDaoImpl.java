package pe.gob.reniec.rrcc.plataformaelectronica.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.LenguaDao;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.LenguaBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ListarPorOficinaRequest;

@Repository
@AllArgsConstructor
public class LenguaDaoImpl implements LenguaDao {
    private JdbcTemplate jdbcTemplate;
    private final String sql = "SELECT CO_LENGUA as codigo, DE_LENGUA as descripcion \n" +
            " FROM IDORRCC.RCTR_LENGUA WHERE ES_LENGUA = '1' AND TIPO_LENGUA = 'O' AND IN_ACTA = '1'";

    @Override
    public List<LenguaBean> listar() {

        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(LenguaBean.class));
    }
    public List<LenguaBean> listarLenguasPorOficina(String codigoOrec) {


        String sql2 = "SELECT A.CO_OREC,\n" +
                " (SELECT l.de_local_larga\n" +
                "  FROM getr_local l\n" +
                "  WHERE l.co_local = a.co_orec) AS DE_LOCAL,\n" +
                " A.CO_LENGUA,\n" +
                " (SELECT g.de_lengua\n" +
                "  FROM idorrcc.rctr_lengua g\n" +
                "  WHERE g.co_lengua = a.co_lengua) AS DE_LENGUA\n" +
                "FROM IDORRCC.RCTV_OREC_LENGUA A\n" +
                "WHERE A.ES_REGISTRO = '1' AND A.CO_OREC = '"+codigoOrec+"' ORDER BY A.CO_OREC, A.CO_LENGUA ASC";
        List<Map<String, Object>> resultadoLenguas = jdbcTemplate.queryForList(sql2);
        List<LenguaBean> listaLenguasDefault = this.listar();
        List<LenguaBean> listaLenguas = new ArrayList<>();

        if(!resultadoLenguas.isEmpty()) {
            for (Map<String, Object> resultado : resultadoLenguas) {
                LenguaBean lenguaBean = new LenguaBean();
                lenguaBean.setCodigo(resultado.get("CO_LENGUA").toString());
                lenguaBean.setDescripcion(resultado.get("DE_LENGUA").toString());
                listaLenguas.add(lenguaBean);
            }
            return listaLenguas;
        }
        else{
            return listaLenguasDefault;
        }
    }
}


