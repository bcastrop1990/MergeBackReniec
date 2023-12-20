package pe.gob.reniec.rrcc.plataformaelectronica.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.RegistradorCivilDao;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.rowmapper.ConsultaRegCivilRowMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.rowmapper.ConsultaRegCivilRuipinRowMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.rowmapper.RegCivilDetalleRowMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.rowmapper.RegCivilRowMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.RegistradorCivilBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.RegistradorCivilDetalleBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.RegistradorCivilRuipinBean;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RegistradorCivilDaoImpl implements RegistradorCivilDao {
  private JdbcTemplate jdbcTemplate;

  @Override
  public Optional<RegistradorCivilBean> buscarPorDni(String dni) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("IDO_PLATAFORMA_EXPE")
        .withProcedureName("EDSP_REG_CIVIL_OBTENER_X_DNI")
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("P_VDNI", Types.VARCHAR),
            new SqlOutParameter("P_CRESULT", Types.REF_CURSOR,
                new RegCivilRowMapper()));

    SqlParameterSource prm = new MapSqlParameterSource()
        .addValue("P_VDNI", dni);
    Map<String, Object> result = simpleJdbcCall.execute(prm);

    List<RegistradorCivilBean> beanList = (List) result.get("P_CRESULT");
    return beanList.stream().findFirst();
  }

  @Override
  public Page<RegistradorCivilBean> consultarRegCivilPorOficina(RegistradorCivilBean bean, Pageable pageable) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
            .withCatalogName("IDO_PLATAFORMA_EXPE")
            .withProcedureName("EDSP_EXP_LISTAR_POR_OFICINA")
            .withoutProcedureColumnMetaDataAccess()
            .declareParameters(
                    new SqlParameter("P_CCO_DEP", Types.VARCHAR),
                    new SqlParameter("P_CCO_PROV", Types.VARCHAR),
                    new SqlParameter("P_CCO_DIST", Types.VARCHAR),
                    new SqlParameter("P_CCO_OREC", Types.VARCHAR),
                    new SqlParameter("P_NPAGE", Types.INTEGER),
                    new SqlParameter("P_NSIZE", Types.INTEGER),
                    new SqlOutParameter("P_CRESULT", Types.REF_CURSOR,
                            new ConsultaRegCivilRowMapper()),
                    new SqlOutParameter("P_CRTOTAL", Types.REF_CURSOR));

    SqlParameterSource prm = new MapSqlParameterSource()
            .addValue("P_CCO_DEP", bean.getOficina().getCodigoDepartamento())
            .addValue("P_CCO_PROV", bean.getOficina().getCodigoProvincia())
            .addValue("P_CCO_DIST", bean.getOficina().getCodigoDistrito())
            .addValue("P_CCO_OREC", bean.getOficina().getCodigoOrec())
            .addValue("P_NPAGE", pageable.getPageNumber())
            .addValue("P_NSIZE", pageable.getPageSize());
    Map<String, Object> result = simpleJdbcCall.execute(prm);

    List<RegistradorCivilBean> beanList = (List) result.get("P_CRESULT");
    List<Map> total = (List) result.get("P_CRTOTAL");
    BigDecimal totalRows = (BigDecimal) total.stream().findFirst().get().get("TOTAL");
    return new PageImpl<>(beanList, pageable, totalRows.longValue());
  }

  @Override
  public Page<RegistradorCivilBean> consultarRegCivilPorDatos(RegistradorCivilBean bean, Pageable pageable) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
            .withCatalogName("IDO_PLATAFORMA_EXPE")
            .withProcedureName("EDSP_EXP_LISTAR_POR_DATOS")
            .withoutProcedureColumnMetaDataAccess()
            .declareParameters(
                    new SqlParameter("P_VDNI", Types.VARCHAR),
                    new SqlParameter("P_VPRI_APE", Types.VARCHAR),
                    new SqlParameter("P_VSEG_APE", Types.VARCHAR),
                    new SqlParameter("P_VPRE_NOM", Types.VARCHAR),
                    new SqlParameter("P_NPAGE", Types.INTEGER),
                    new SqlParameter("P_NSIZE", Types.INTEGER),
                    new SqlOutParameter("P_CRESULT", Types.REF_CURSOR,
                            new ConsultaRegCivilRowMapper()),
                    new SqlOutParameter("P_CRTOTAL", Types.REF_CURSOR));

    SqlParameterSource prm = new MapSqlParameterSource()
            .addValue("P_VDNI", bean.getNumeroDocIdentidad())
            .addValue("P_VPRI_APE", bean.getPrimerApellido())
            .addValue("P_VSEG_APE", bean.getSegundoApellido())
            .addValue("P_VPRE_NOM", bean.getPreNombre())
            .addValue("P_NPAGE", pageable.getPageNumber())
            .addValue("P_NSIZE", pageable.getPageSize());
    Map<String, Object> result = simpleJdbcCall.execute(prm);

    List<RegistradorCivilBean> beanList = (List) result.get("P_CRESULT");
    List<Map> total = (List) result.get("P_CRTOTAL");
    BigDecimal totalRows = (BigDecimal) total.stream().findFirst().get().get("TOTAL");
    return new PageImpl<>(beanList, pageable, totalRows.longValue());
  }

  @Override
  public Optional<RegistradorCivilRuipinBean> consultarRegCivilRuipinPorDatos(RegistradorCivilRuipinBean bean) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
            .withCatalogName("IDO_PLATAFORMA_EXPE")
            .withProcedureName("VALIDAR_DATOS_BD_RUIPIN")
            .withoutProcedureColumnMetaDataAccess()
            .declareParameters(
                    new SqlParameter("P_VDNI", Types.VARCHAR),
                    new SqlParameter("P_VPRI_APE", Types.VARCHAR),
                    new SqlOutParameter("P_CRESULT", Types.REF_CURSOR,
                            new ConsultaRegCivilRuipinRowMapper()));

    SqlParameterSource prm = new MapSqlParameterSource()
            .addValue("P_VDNI", bean.getNumeroDocIdentidad())
            .addValue("P_VPRI_APE", bean.getPrimerApellido())
            .addValue("P_VSEG_APE", bean.getSegundoApellido())
            .addValue("P_VPRE_NOM", bean.getPreNombre());
    Map<String, Object> result = simpleJdbcCall.execute(prm);

    List<RegistradorCivilRuipinBean> beanList = (List) result.get("P_CRESULT");
    return beanList.stream().findFirst();
  }

  @Override
  public Optional<RegistradorCivilDetalleBean> consultaUltimoMovimientoPorDni(String dni) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("IDO_PLATAFORMA_EXPE")
        .withProcedureName("EDSP_DET_RC_ULTIMO_X_DNI")
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("P_VDNI", Types.VARCHAR),
            new SqlOutParameter("P_CRESULT", Types.REF_CURSOR,
                new RegCivilDetalleRowMapper()));

    SqlParameterSource prm = new MapSqlParameterSource()
        .addValue("P_VDNI", dni);
    Map<String, Object> result = simpleJdbcCall.execute(prm);

    List<RegistradorCivilDetalleBean> beanList = (List) result.get("P_CRESULT");
    return beanList.stream().findFirst();
  }

  @Override
  public void registrarDetalle(RegistradorCivilDetalleBean regCivilDetBean) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("IDO_PLATAFORMA_EXPE")
        .withProcedureName("EDSP_REGCIV_DET_REGISTRAR")
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("p_DE_OBSERVACION", Types.VARCHAR),
            new SqlParameter("p_NU_CELULAR", Types.VARCHAR),
            new SqlParameter("p_ID_TIPO_SOLICITUD_FIRMA", Types.VARCHAR),
            new SqlParameter("p_CO_OREC_REG_CIVIL", Types.VARCHAR),
            new SqlParameter("p_CO_ESTADO_FORMATO", Types.VARCHAR),
            new SqlParameter("p_DE_UBIGEO_DETALLE", Types.VARCHAR),
            new SqlParameter("p_AP_PRIMER_APELLIDO", Types.VARCHAR),
            new SqlParameter("p_CO_CONDICION", Types.VARCHAR),
            new SqlParameter("p_FE_FECHA_BAJA", Types.DATE),
            new SqlParameter("p_CO_TIPO_DOC_IDENTIDAD", Types.VARCHAR),
            new SqlParameter("p_CO_MOTIVO_ACTUALIZA", Types.VARCHAR),
            new SqlParameter("p_FE_FECHA_ACTUALIZACION", Types.DATE),
            new SqlParameter("p_AP_SEGUNDO_APELLIDO", Types.VARCHAR),
            new SqlParameter("p_ID_CREA", Types.VARCHAR),
            new SqlParameter("p_CO_ESTADO_REGISTRADOR", Types.VARCHAR),
            new SqlParameter("p_DE_MAIL", Types.VARCHAR),
            new SqlParameter("p_FE_FECHA_ALTA", Types.DATE),
            new SqlParameter("p_DE_DETALLE_OREC_CORTA", Types.VARCHAR),
            new SqlParameter("p_FE_FECHA_INICIO", Types.DATE),
            new SqlParameter("p_FE_FECHA_FIN", Types.DATE),
            new SqlParameter("p_NO_PRENOMBRES", Types.VARCHAR),
            new SqlParameter("p_NU_DOC_IDENTIDAD", Types.VARCHAR),
            new SqlParameter("p_CO_CARGO_REGISTRADOR", Types.VARCHAR),
            new SqlParameter("p_ID_DET_SOL_FIRMA", Types.BIGINT)
            );

    SqlParameterSource prm = new MapSqlParameterSource()
        .addValue("p_DE_OBSERVACION",  regCivilDetBean.getObservacion())
        .addValue("p_NU_CELULAR",  regCivilDetBean.getCelular())
        .addValue("p_ID_TIPO_SOLICITUD_FIRMA",  regCivilDetBean.getIdTipoSolFirma())
        .addValue("p_CO_OREC_REG_CIVIL",  regCivilDetBean.getCodigoOrec())
        .addValue("p_CO_ESTADO_FORMATO",  regCivilDetBean.getCodigoEstadoFormato())
        .addValue("p_DE_UBIGEO_DETALLE",  regCivilDetBean.getDescripcionUbigeoDetalle())
        .addValue("p_AP_PRIMER_APELLIDO",  regCivilDetBean.getPrimerApellido())
        .addValue("p_CO_CONDICION",  regCivilDetBean.getCodigoCondicion())
        .addValue("p_FE_FECHA_BAJA",  regCivilDetBean.getFechaBaja())
        .addValue("p_CO_TIPO_DOC_IDENTIDAD",  regCivilDetBean.getIdTipoDocIdentidad())
        .addValue("p_CO_MOTIVO_ACTUALIZA",  regCivilDetBean.getCodigoMotivoActualiza())
        .addValue("p_FE_FECHA_ACTUALIZACION",  regCivilDetBean.getFechaActualizacion())
        .addValue("p_AP_SEGUNDO_APELLIDO",  regCivilDetBean.getSegundoApellido())
        .addValue("p_ID_CREA",  regCivilDetBean.getIdCrea())
        .addValue("p_CO_ESTADO_REGISTRADOR",  regCivilDetBean.getEstado())
        .addValue("p_DE_MAIL",  regCivilDetBean.getEmail())
        .addValue("p_FE_FECHA_ALTA",  regCivilDetBean.getFechaAlta())
        .addValue("p_DE_DETALLE_OREC_CORTA",  regCivilDetBean.getDescripcionOrecCorta())
        .addValue("p_FE_FECHA_INICIO",  regCivilDetBean.getFechaInicio())
        .addValue("p_FE_FECHA_FIN",  regCivilDetBean.getFechaFin())
        .addValue("p_NO_PRENOMBRES",  regCivilDetBean.getPreNombre())
        .addValue("p_NU_DOC_IDENTIDAD",  regCivilDetBean.getNumeroDocIdentidad())
        .addValue("p_CO_CARGO_REGISTRADOR",  regCivilDetBean.getCodigoCargoRegistrador())
        .addValue("p_ID_DET_SOL_FIRMA",  regCivilDetBean.getIdDetSolFirma());

    simpleJdbcCall.execute(prm);

  }

  @Override
  public void registrar(RegistradorCivilBean regCivilBean) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("IDO_PLATAFORMA_EXPE")
        .withProcedureName("EDSP_REGCIV_REGISTRAR")
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("p_AP_SEGUNDO_APELLIDO", Types.VARCHAR),
            new SqlParameter("p_NU_CELULAR", Types.VARCHAR),
            new SqlParameter("p_ID_CREA", Types.VARCHAR),
            new SqlParameter("p_ID_TIPO_SOLICITUD_FIRMA", Types.VARCHAR),
            new SqlParameter("p_CO_OREC_REG_CIVIL", Types.VARCHAR),
            new SqlParameter("p_CO_ESTADO_REGISTRADOR", Types.VARCHAR),
            new SqlParameter("p_DE_MAIL", Types.VARCHAR),
            new SqlParameter("p_AP_PRIMER_APELLIDO", Types.VARCHAR),
            new SqlParameter("p_FE_CAMBIO_ESTADO", Types.DATE),
            new SqlParameter("p_DE_DETALLE_OREC_CORTA", Types.VARCHAR),
            new SqlParameter("p_NO_PRENOMBRES", Types.VARCHAR),
            new SqlParameter("p_CO_TIPO_DOC_IDENTIDAD", Types.VARCHAR),
            new SqlParameter("p_NU_DOC_IDENTIDAD", Types.VARCHAR)
            );

    SqlParameterSource prm = new MapSqlParameterSource()
        .addValue("p_AP_SEGUNDO_APELLIDO",  regCivilBean.getSegundoApellido())
        .addValue("p_NU_CELULAR",  regCivilBean.getCelular())
        .addValue("p_ID_CREA",  regCivilBean.getIdCrea())
        .addValue("p_ID_TIPO_SOLICITUD_FIRMA",  regCivilBean.getIdTipoSolFirma())
        .addValue("p_CO_OREC_REG_CIVIL",  regCivilBean.getCodigoOrec())
        .addValue("p_CO_ESTADO_REGISTRADOR",  regCivilBean.getEstado())
        .addValue("p_DE_MAIL",  regCivilBean.getEmail())
        .addValue("p_AP_PRIMER_APELLIDO",  regCivilBean.getPrimerApellido())
        .addValue("p_FE_CAMBIO_ESTADO",  regCivilBean.getFechaCambioEstado())
        .addValue("p_DE_DETALLE_OREC_CORTA",  regCivilBean.getDescripcionOrecCorta())
        .addValue("p_NO_PRENOMBRES",  regCivilBean.getPreNombre())
        .addValue("p_CO_TIPO_DOC_IDENTIDAD",  regCivilBean.getIdTipoDocIdentidad())
        .addValue("p_NU_DOC_IDENTIDAD",  regCivilBean.getNumeroDocIdentidad());

    simpleJdbcCall.execute(prm);

  }

  @Override
  public void actualizarBajaPorDni(RegistradorCivilBean regCivilBean) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("IDO_PLATAFORMA_EXPE")
        .withProcedureName("EDSP_REGCIV_ACT_BAJA")
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("p_CO_ESTADO_REGISTRADOR", Types.VARCHAR),
            new SqlParameter("p_FE_CAMBIO_ESTADO", Types.DATE),
            new SqlParameter("p_CO_TIPO_DOC_IDENTIDAD", Types.VARCHAR),
            new SqlParameter("p_NU_DOC_IDENTIDAD", Types.VARCHAR),
            new SqlParameter("p_ID_ACTUALIZA", Types.VARCHAR)
        );

    SqlParameterSource prm = new MapSqlParameterSource()
        .addValue("p_CO_ESTADO_REGISTRADOR",  regCivilBean.getEstado())
        .addValue("p_FE_CAMBIO_ESTADO",  regCivilBean.getFechaCambioEstado())
        .addValue("p_CO_TIPO_DOC_IDENTIDAD",  regCivilBean.getIdTipoDocIdentidad())
        .addValue("p_NU_DOC_IDENTIDAD",  regCivilBean.getNumeroDocIdentidad())
        .addValue("p_ID_ACTUALIZA",  regCivilBean.getIdActualiza());

    simpleJdbcCall.execute(prm);
  }

  @Override
  public void actualizarActualizacionPorDni(RegistradorCivilBean regCivilBean) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("IDO_PLATAFORMA_EXPE")
        .withProcedureName("EDSP_REGCIV_ACT_ACTUALIZA")
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("p_CO_ESTADO_REGISTRADOR", Types.VARCHAR),
            new SqlParameter("p_FE_CAMBIO_ESTADO", Types.DATE),
            new SqlParameter("p_CO_TIPO_DOC_IDENTIDAD", Types.VARCHAR),
            new SqlParameter("p_NU_DOC_IDENTIDAD", Types.VARCHAR),
            new SqlParameter("p_ID_ACTUALIZA", Types.VARCHAR),
            new SqlParameter("p_CO_OREC_REG_CIVIL", Types.VARCHAR),
            new SqlParameter("p_DE_DETALLE_OREC_CORTA", Types.VARCHAR),
            new SqlParameter("p_NU_CELULAR", Types.VARCHAR),
            new SqlParameter("p_DE_MAIL", Types.VARCHAR)
        );

    SqlParameterSource prm = new MapSqlParameterSource()
        .addValue("p_CO_ESTADO_REGISTRADOR",  regCivilBean.getEstado())
        .addValue("p_FE_CAMBIO_ESTADO",  regCivilBean.getFechaCambioEstado())
        .addValue("p_CO_TIPO_DOC_IDENTIDAD",  regCivilBean.getIdTipoDocIdentidad())
        .addValue("p_NU_DOC_IDENTIDAD",  regCivilBean.getNumeroDocIdentidad())
        .addValue("p_ID_ACTUALIZA",  regCivilBean.getIdActualiza())
        .addValue("p_CO_OREC_REG_CIVIL",  regCivilBean.getCodigoOrec())
        .addValue("p_DE_DETALLE_OREC_CORTA",  regCivilBean.getDescripcionOrecCorta())
        .addValue("p_NU_CELULAR",  regCivilBean.getNumeroDocIdentidad())
        .addValue("p_DE_MAIL",  regCivilBean.getEmail());

    simpleJdbcCall.execute(prm);
  }

  @Override
  public void actualizarReingresoPorDni(RegistradorCivilBean regCivilBean) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("IDO_PLATAFORMA_EXPE")
        .withProcedureName("EDSP_REGCIV_ACT_REINGRESO")
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("p_CO_ESTADO_REGISTRADOR", Types.VARCHAR),
            new SqlParameter("p_FE_CAMBIO_ESTADO", Types.DATE),
            new SqlParameter("p_CO_TIPO_DOC_IDENTIDAD", Types.VARCHAR),
            new SqlParameter("p_NU_DOC_IDENTIDAD", Types.VARCHAR),
            new SqlParameter("p_ID_ACTUALIZA", Types.VARCHAR),
            new SqlParameter("p_CO_OREC_REG_CIVIL", Types.VARCHAR),
            new SqlParameter("p_DE_DETALLE_OREC_CORTA", Types.VARCHAR),
            new SqlParameter("p_NU_CELULAR", Types.VARCHAR),
            new SqlParameter("p_DE_MAIL", Types.VARCHAR)
        );

    SqlParameterSource prm = new MapSqlParameterSource()
        .addValue("p_CO_ESTADO_REGISTRADOR",  regCivilBean.getEstado())
        .addValue("p_FE_CAMBIO_ESTADO",  regCivilBean.getFechaCambioEstado())
        .addValue("p_CO_TIPO_DOC_IDENTIDAD",  regCivilBean.getIdTipoDocIdentidad())
        .addValue("p_NU_DOC_IDENTIDAD",  regCivilBean.getNumeroDocIdentidad())
        .addValue("p_ID_ACTUALIZA",  regCivilBean.getIdActualiza())
        .addValue("p_CO_OREC_REG_CIVIL",  regCivilBean.getCodigoOrec())
        .addValue("p_DE_DETALLE_OREC_CORTA",  regCivilBean.getDescripcionOrecCorta())
        .addValue("p_NU_CELULAR",  regCivilBean.getNumeroDocIdentidad())
        .addValue("p_DE_MAIL",  regCivilBean.getEmail());

    simpleJdbcCall.execute(prm);
  }
}
