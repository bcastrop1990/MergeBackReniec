package pe.gob.reniec.rrcc.plataformaelectronica.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pe.gob.reniec.rrcc.plataformaelectronica.controller.mapper.ConsultaGestionSolExpRowMapper;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.SolicitudDao;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.rowmapper.*;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.*;
import pe.gob.reniec.rrcc.plataformaelectronica.security.SecurityUtil;

@Repository
@AllArgsConstructor
public class SolicitudDaoImpl implements SolicitudDao {
  private JdbcTemplate jdbcTemplate;
  @Override
  public Long registrar(SolicitudBean solicitud) {

    String sql = "INSERT INTO IDO_PLATAFORMA_EXPE.EDTC_SOLICITUD(ID_SOLICITUD,ID_TIPO_REGISTRO,\n" +
            "CO_TIPO_DOC_IDENTI_SOLICITANTE,NU_DOC_IDENTIDAD_SOLICITANTE,\n" +
            "AP_PRIMER_APELLIDO,AP_SEGUNDO_APELLIDO,NO_PRENOMBRES,NU_CELULAR,DE_MAIL,\n" +
            "DE_DETALLE_OREC_LARGA,DE_DETALLE_OREC_CORTA,CO_DEPARTAMENTO_OREC,CO_PROVINCIA_OREC,\n" +
            "CO_DISTRITO_OREC,CO_CP_OREC,FE_FECHA_SOLICITUD,CO_ESTADO_SOLICITUD,\n" +
            "CO_MOD_REGISTRO,ID_CREA,FE_CREA,CO_OREC_SOLICITUD,NU_SOLICITUD_NUMERO,CO_ESTADO, CO_TIPO_ARCHIVO_SUSTENTO, ID_ARCHIVO_SUSTENTO)\n" +
            "VALUES(IDO_PLATAFORMA_EXPE.EDSE_SOLICITUD.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,?,?,?,SYSDATE,?,?,'1',?,?)";

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
              PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID_SOLICITUD"});
              ps.setString(1, solicitud.getIdTipoRegistro());
              ps.setString(2, solicitud.getIdTipoDocumentoSolicitante());
              ps.setString(3, solicitud.getNumeroDocumentoSolicitante());
              ps.setString(4, solicitud.getPrimerApellido());
              ps.setString(5, solicitud.getSegundoApellido());
              ps.setString(6, solicitud.getPreNombres());
              ps.setString(7, solicitud.getCelular());
              ps.setString(8, solicitud.getEmail());
              ps.setString(9, solicitud.getDescripcionOrecLarga());
              ps.setString(10, solicitud.getDescripcionOrecCorta());
              ps.setString(11, solicitud.getCodigoDepartamentoOrec());
              ps.setString(12, solicitud.getCodigoProvinciaOrec());
              ps.setString(13, solicitud.getCodigoDistritoOrec());
              ps.setString(14, solicitud.getCodigoCentroPobladoOrec());
              ps.setString(15, solicitud.getCodigoEstado());
              ps.setString(16, solicitud.getCodigoModoRegistro());
              ps.setString(17, solicitud.getIdCrea());
              ps.setString(18, solicitud.getCodigoOrec());
              ps.setString(19, solicitud.getNumeroSolicitud());
              ps.setString(20, "03");
              ps.setLong(21, solicitud.getIdArchivoSustento());

              return ps;
            }, keyHolder);

    Long idSolicitud = keyHolder.getKey().longValue();
    return idSolicitud;
  }

  @Override
  public void registrarHistorial(SolicitudBean solicitud) {
    String sql2 = "INSERT INTO IDO_PLATAFORMA_EXPE.EDTV_SOLICITUD(ID_MOV_SOL, NU_SOLICITUD_NUMERO, NU_SECUENCIA, CO_ESTADO_SOLICITUD, FE_CREA, ID_CREA) " +
            "VALUES(IDO_PLATAFORMA_EXPE.EDSE_EDTV_SOL.nextval, ? , 1, '1', SYSDATE,?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(con2 -> {
      PreparedStatement ps2 = con2.prepareStatement(sql2, new String[]{"ID_MOV_SOL"});
      ps2.setString(1, solicitud.getNumeroSolicitud() );
      ps2.setString(2, solicitud.getIdCrea());
      return ps2;
    }, keyHolder);
  }
  @Override
  public void registrarDetalleFirma(DetalleSolicitudFirmaBean detalle) {
    String sql = "INSERT INTO IDO_PLATAFORMA_EXPE.EDTD_DET_SOL_FIRMA(ID_DET_SOL_FIRMA,ID_SOLICITUD,\n" +
        "ID_TIPO_SOLICITUD_FIRMA,CO_TIPO_DOC_IDENTIDAD,NU_DOC_IDENTIDAD,AP_PRIMER_APELLIDO,AP_SEGUNDO_APELLIDO,\n" +
        "NO_PRENOMBRES,NU_CELULAR,CO_ESTADO,DE_EMAIL,ID_CREA, FE_CREA)\n" +
        "VALUES(IDO_PLATAFORMA_EXPE.EDSE_DET_SOL_FIRMA.nextval,?,?,?,?,?,?,?,?,'1',?,?,SYSDATE)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
      PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID_DET_SOL_FIRMA"});
      ps.setLong(1, detalle.getIdSolicitud());
      ps.setString(2, detalle.getIdTipoSolicitud());
      ps.setString(3, detalle.getIdTipoDocumento());
      ps.setString(4, detalle.getNumeroDocumento());
      ps.setString(5, detalle.getPrimerApellido());
      ps.setString(6, detalle.getSegundoApellido());
      ps.setString(7, detalle.getPreNombres());
      ps.setString(8, detalle.getCelular());
      ps.setString(9, detalle.getEmail());
      ps.setString(10, detalle.getIdCrea());
      return ps;
    }, keyHolder);
    detalle.setIdDetalleSolicitud(keyHolder.getKey().longValue());
  }

  @Override
  public void registrarDetalleLibro(DetalleSolicitudLibroBean detalle) {
    String sql = "INSERT INTO IDO_PLATAFORMA_EXPE.EDTD_DET_SOL_LIBRO(ID_DET_SOL_LIBRO,ID_SOLICITUD,\n" +
        "CO_ARTICULO,CO_LENGUA,NU_NUM_ULTIMA_ACTA,NU_CANTIDAD,CO_ESTADO,ID_CREA, FE_CREA)\n" +
        "VALUES(IDO_PLATAFORMA_EXPE.EDSE_DET_SOL_LIBRO.nextval,?,?,?,?,?,'1',?,SYSDATE)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
      PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID_DET_SOL_lIBRO"});
      ps.setLong(1, detalle.getIdSolicitud());
      ps.setString(2, detalle.getCodigoArticulo());
      ps.setString(3, detalle.getCodigoLengua());
      ps.setString(4, detalle.getNumeroUltimaActa());
      ps.setInt(5, detalle.getCantidad());
      ps.setString(6, detalle.getIdCrea());
      return ps;
    }, keyHolder);
    detalle.setIdDetalleSolLibro(keyHolder.getKey().longValue());
  }

  @Override
  public void registrarDetalleArchivoFirma(DetalleSolicitudArchivoFirmaBean archivo) {
    String sql = "INSERT INTO IDO_PLATAFORMA_EXPE.EDTD_DET_ARCH_SOL_FIRMA(ID_ADJ_SOL_FIRMA,ID_DET_SOL_FIRMA,\n" +
        "ID_ARCHIVO,CO_ESTADO,ID_CREA,FE_CREA,CO_TIPO_ARCHIVO,CO_USO_ARCHIVO)\n" +
        "VALUES(IDO_PLATAFORMA_EXPE.EDSE_DET_ARCH_SOL_FIRMA.nextval,?,?,'1',?,SYSDATE,?,?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
      PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID_ADJ_SOL_FIRMA"});
      ps.setLong(1, archivo.getIdDetalleSolicitud());
      ps.setLong(2, archivo.getIdArchivo());
      ps.setString(3, archivo.getIdCrea());
      ps.setString(4, archivo.getCodigoTipoArchivo());
      ps.setString(5, archivo.getCodigoUsoArchivo());


      return ps;
    }, keyHolder);
    archivo.setIdDetalleSolicitudArchivo(keyHolder.getKey().longValue());
  }

  @Override
  public void actualizarIdSolicitud(DetalleSolicitudArchivoFirmaBean archivo, Long id_solicitud) {
    String sql = "UPDATE IDO_PLATAFORMA_EXPE.EDTR_ARCHIVO SET ID_ACTUALIZA = ?, FE_ACTUALIZA = SYSDATE, ID_SOLICITUD = ?" +
            " WHERE ID_ARCHIVO = ?";
    jdbcTemplate.update(sql, SecurityUtil.getUserInfo().getDni(), id_solicitud, archivo.getIdArchivo());
  }

  @Override
  public Optional<SolicitudBean> obtenerPorNumero(String numero) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("IDO_PLATAFORMA_EXPE")
        .withProcedureName("EDSP_SOL_OBTENER_POR_NUMERO") //tabla
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("P_VNUM_SOL", Types.VARCHAR),
            new SqlOutParameter("C_CRRESULT", Types.REF_CURSOR,
                new SolicitudRowMapper()));
    SqlParameterSource prm = new MapSqlParameterSource()
        .addValue("P_VNUM_SOL", numero);
    Map<String, Object> result = simpleJdbcCall.execute(prm);
    List<SolicitudBean> beanList = (List) result.get("C_CRRESULT");
    return beanList.stream().findFirst();
  }

  @Override
  public Optional<SolicitudBean> obtenerSolFirmaByDniReg(String dni) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("IDO_PLATAFORMA_EXPE")
        .withProcedureName("EDSP_SOL_FIRMA_OBT_X_DNI")
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("P_VNUM_DOC", Types.VARCHAR),
            new SqlOutParameter("C_CRRESULT", Types.REF_CURSOR,
                new SolicitudRowMapper()));
    SqlParameterSource prm = new MapSqlParameterSource()
        .addValue("P_VNUM_DOC", dni);
    Map<String, Object> result = simpleJdbcCall.execute(prm);
    List<SolicitudBean> beanList = (List) result.get("C_CRRESULT");
    return beanList.stream().findFirst();
  }

  @Override
  public List<DetalleSolicitudFirmaBean> listarByIdSolicitud(Long idSolicitud) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("IDO_PLATAFORMA_EXPE")
        .withProcedureName("EDSP_SOL_DET_FIRMA_OBTENER")
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("P_VID_SOL", Types.VARCHAR),
            new SqlOutParameter("C_CRRESULT", Types.REF_CURSOR,
                new DetalleSolicitudFirmaRowMapper()));
    SqlParameterSource prm = new MapSqlParameterSource()
        .addValue("P_VID_SOL", idSolicitud);
    Map<String, Object> result = simpleJdbcCall.execute(prm);
    List<DetalleSolicitudFirmaBean> beanList = (List) result.get("C_CRRESULT");
    return beanList;
  }

  @Override
  public List<DetalleSolicitudFirmaBean> listarByDni(String dni) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("IDO_PLATAFORMA_EXPE")
        .withProcedureName("EDSP_SOL_DET_FIRMA_LISTA_X_DNI")
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("P_VDNI", Types.VARCHAR),
            new SqlOutParameter("C_CRRESULT", Types.REF_CURSOR,
                new DetalleSolicitudFirmaRowMapper()));
    SqlParameterSource prm = new MapSqlParameterSource()
        .addValue("P_VDNI", dni);
    Map<String, Object> result = simpleJdbcCall.execute(prm);
    List<DetalleSolicitudFirmaBean> beanList = (List) result.get("C_CRRESULT");
    return beanList;
  }

  @Override
  public Page<SolicitudBean> consultarSeguimiento(String nroSolicitud,Pageable pageable, String dni, String fechaIni, String fechaFin) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("IDO_PLATAFORMA_EXPE")
        .withProcedureName("EDSP_SEG_SOLICITUD_CONSULTAR")
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("P_VNRO_SOLICITUD", Types.VARCHAR),
            new SqlParameter("P_VDNI", Types.VARCHAR),
            new SqlParameter("P_VFECHA_INI", Types.VARCHAR),
            new SqlParameter("P_VFECHAFIN", Types.VARCHAR),
            new SqlParameter("P_NPAGE", Types.INTEGER),
            new SqlParameter("P_NSIZE", Types.INTEGER),
            new SqlOutParameter("P_CRESULT", Types.REF_CURSOR, new ConsultaSegSolRowMapper()),
            new SqlOutParameter("P_CRTOTAL", Types.REF_CURSOR));

    SqlParameterSource prm = new MapSqlParameterSource()
        .addValue("P_VNRO_SOLICITUD", nroSolicitud)
        .addValue("P_VDNI", dni)
        .addValue("P_VFECHA_INI", fechaIni)
        .addValue("P_VFECHAFIN", fechaFin)
        .addValue("P_NPAGE", pageable.getPageNumber())
        .addValue("P_NSIZE", pageable.getPageSize());
    Map<String, Object> result = simpleJdbcCall.execute(prm);

    List<SolicitudBean> beanList = (List) result.get("P_CRESULT");
    List<Map> total = (List) result.get("P_CRTOTAL");
    BigDecimal totalRows = (BigDecimal) total.stream().findFirst().get().get("TOTAL");
    return new PageImpl<>(beanList, pageable, totalRows.longValue());
  }

  @Override
  public Page<SolicitudBean> consultar(SolicitudBean bean, Pageable pageable, String fechaIni, String fechaFin) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("IDO_PLATAFORMA_EXPE")
        .withProcedureName("EDSP_GESTION_SOL_CONSULTAR")
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("P_VNRO_SOLICITUD", Types.VARCHAR),
            new SqlParameter("P_CESTADO", Types.VARCHAR),
            new SqlParameter("P_CTIPO_REG", Types.VARCHAR),
            new SqlParameter("P_VFECHA_INI", Types.VARCHAR),
            new SqlParameter("P_VFECHA_FIN", Types.VARCHAR),
            new SqlParameter("P_CCO_DEP", Types.VARCHAR),
            new SqlParameter("P_CCO_PROV", Types.VARCHAR),
            new SqlParameter("P_CCO_DIST", Types.VARCHAR),
            new SqlParameter("P_CCO_CENT_POBL", Types.VARCHAR),
            new SqlParameter("P_CCO_OREC", Types.VARCHAR),
            new SqlParameter("P_CCO_ANALISTA", Types.VARCHAR),
                new SqlParameter("P_CDNI_SOL", Types.VARCHAR),
            new SqlParameter("P_NPAGE", Types.INTEGER),
            new SqlParameter("P_NSIZE", Types.INTEGER),
            new SqlOutParameter("P_CRESULT", Types.REF_CURSOR, new ConsultaGestionSolRowMapper()),
            new SqlOutParameter("P_CRTOTAL", Types.REF_CURSOR));

    SqlParameterSource prm = new MapSqlParameterSource()
        .addValue("P_VNRO_SOLICITUD", bean.getNumeroSolicitud())
        .addValue("P_CESTADO", bean.getCodigoEstado())
        .addValue("P_CTIPO_REG", bean.getIdTipoRegistro())
        .addValue("P_VFECHA_INI", fechaIni)
        .addValue("P_VFECHA_FIN", fechaFin)
        .addValue("P_CCO_DEP", bean.getCodigoDepartamentoOrec())
        .addValue("P_CCO_PROV", bean.getCodigoProvinciaOrec())
        .addValue("P_CCO_DIST", bean.getCodigoDistritoOrec())
        .addValue("P_CCO_CENT_POBL", bean.getCodigoCentroPobladoOrec())
        .addValue("P_CCO_OREC", bean.getCodigoOrec())
        .addValue("P_CCO_ANALISTA", bean.getCodigoAnalistaAsignado())
            .addValue("P_CDNI_SOL", bean.getNumeroDocumentoSolicitante())
            .addValue("P_NPAGE", pageable.getPageNumber())
        .addValue("P_NSIZE", pageable.getPageSize());
    Map<String, Object> result = simpleJdbcCall.execute(prm);

    List<SolicitudBean> beanList = (List) result.get("P_CRESULT");
    List<Map> total = (List) result.get("P_CRTOTAL");
    BigDecimal totalRows = (BigDecimal) total.stream().findFirst().get().get("TOTAL");
    return new PageImpl<>(beanList, pageable, totalRows.longValue());
  }
  /*
  @Override
  public Optional<SolicitudReportesBean> consultarReportes(SolicitudReportesBean solicitudReportesBean) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
            .withCatalogName("IDO_PLATAFORMA_EXPE")
            .withProcedureName("EDSP_REPORTES_SOL_CONSULTAR")
            .withoutProcedureColumnMetaDataAccess()
            .declareParameters(
                    new SqlParameter("P_VDNI", Types.VARCHAR),
                    new SqlParameter("P_VPRI_APE", Types.VARCHAR),
                    new SqlParameter("P_VSEG_APE", Types.VARCHAR),
                    new SqlOutParameter("P_CRESULT", Types.REF_CURSOR, new ConsultaGestionSolRowMapper()));

    SqlParameterSource prm = new MapSqlParameterSource()
            .addValue("P_VDNI", solicitudReportesBean.getNumeroDocIdentidad())
            .addValue("P_VPRI_APE", solicitudReportesBean.getPrimerApellido())
            .addValue("P_VSEG_APE", solicitudReportesBean.getSegundoApellido());
    Map<String, Object> result = simpleJdbcCall.execute(prm);

    List<SolicitudBean> beanList = (List) result.get("P_CRESULT");
    return new PageImpl<SolicitudBean>(beanList);
  }

   */
  @Override
  public Page<SolicitudBean> consultarExpediente(SolicitudBean bean, Pageable pageable, String fechaIni, String fechaFin) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
            .withCatalogName("IDO_PLATAFORMA_EXPE")
            .withProcedureName("EDSP_GESTION_EXP_CONSULTAR")
            .withoutProcedureColumnMetaDataAccess()
            .declareParameters(
                    new SqlParameter("P_VNRO_SOLICITUD", Types.VARCHAR),
                    new SqlParameter("P_CESTADO", Types.VARCHAR),
                    new SqlParameter("P_CTIPO_REG", Types.VARCHAR),
                    new SqlParameter("P_VFECHA_INI", Types.VARCHAR),
                    new SqlParameter("P_VFECHA_FIN", Types.VARCHAR),
                    new SqlParameter("P_CCO_DEP", Types.VARCHAR),
                    new SqlParameter("P_CCO_PROV", Types.VARCHAR),
                    new SqlParameter("P_CCO_DIST", Types.VARCHAR),
                    new SqlParameter("P_CCO_CENT_POBL", Types.VARCHAR),
                    new SqlParameter("P_CCO_OREC", Types.VARCHAR),
                    new SqlParameter("P_CCO_ANALISTA", Types.VARCHAR),
                    new SqlParameter("P_NPAGE", Types.INTEGER),
                    new SqlParameter("P_NSIZE", Types.INTEGER),
                    new SqlOutParameter("P_CRESULT", Types.REF_CURSOR, new ConsultaGestionSolExpRowMapper()),
                    new SqlOutParameter("P_CRTOTAL", Types.REF_CURSOR));

    SqlParameterSource prm = new MapSqlParameterSource()
            .addValue("P_VNRO_SOLICITUD", bean.getNumeroSolicitud())
            .addValue("P_CESTADO", bean.getCodigoEstado())
            .addValue("P_CTIPO_REG", bean.getIdTipoRegistro())
            .addValue("P_VFECHA_INI", fechaIni)
            .addValue("P_VFECHA_FIN", fechaFin)
            .addValue("P_CCO_DEP", bean.getCodigoDepartamentoOrec())
            .addValue("P_CCO_PROV", bean.getCodigoProvinciaOrec())
            .addValue("P_CCO_DIST", bean.getCodigoDistritoOrec())
            .addValue("P_CCO_CENT_POBL", bean.getCodigoCentroPobladoOrec())
            .addValue("P_CCO_OREC", bean.getCodigoOrec())
            .addValue("P_CCO_ANALISTA", bean.getCodigoAnalistaAsignado())
            .addValue("P_NPAGE", pageable.getPageNumber())
            .addValue("P_NSIZE", pageable.getPageSize());
    Map<String, Object> result = simpleJdbcCall.execute(prm);

    List<SolicitudBean> beanList = (List) result.get("P_CRESULT");
    List<Map> total = (List) result.get("P_CRTOTAL");
    BigDecimal totalRows = (BigDecimal) total.stream().findFirst().get().get("TOTAL");
    return new PageImpl<>(beanList, pageable, totalRows.longValue());
  }

  @Override
  public void recepcionar(String nroSolicitud, String codigoEstado, String codigoUsuario) {
    String sql = "UPDATE IDO_PLATAFORMA_EXPE.EDTC_SOLICITUD \n" +
        " SET CO_ESTADO_SOLICITUD = ? , FE_FECHA_RECEPCION = SYSDATE, CO_USUARIO_RECEPCION = ?\n" +
        " , FE_ACTUALIZA = SYSDATE, ID_ACTUALIZA = ?\n" +
        " WHERE NU_SOLICITUD_NUMERO = ?";
    jdbcTemplate.update(sql, codigoEstado, codigoUsuario, SecurityUtil.getUserInfo().getDni(), nroSolicitud);
  }
  @Override
  public void recepcionarHistorial(String nroSolicitud, String codigoEstado, String codigoUsuario) {
    String sql2 = "INSERT INTO IDO_PLATAFORMA_EXPE.EDTV_SOLICITUD(ID_MOV_SOL, NU_SOLICITUD_NUMERO, NU_SECUENCIA, CO_ESTADO_SOLICITUD, FE_CREA, ID_CREA) " +
            "VALUES(IDO_PLATAFORMA_EXPE.EDSE_EDTV_SOL.nextval, ? , ?, ?, SYSDATE,?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(con2 -> {
      PreparedStatement ps2 = con2.prepareStatement(sql2, new String[]{"ID_MOV_SOL"});
      ps2.setString(1,nroSolicitud );
      String sql = "SELECT MAX(NU_SECUENCIA) FROM IDO_PLATAFORMA_EXPE.EDTV_SOLICITUD WHERE NU_SOLICITUD_NUMERO = ?";
      Integer maxNu_Sec = jdbcTemplate.queryForObject(sql, Integer.class, nroSolicitud);
      ps2.setString(1,nroSolicitud );
      if(maxNu_Sec != null){
        Integer nuevoID = (maxNu_Sec)+1;
        ps2.setInt(2, nuevoID);
      }else{
        ps2.setInt(2, 1);
      }
      ps2.setString(3, codigoEstado);
      ps2.setString(4, codigoUsuario);
      return ps2;
    }, keyHolder);
  }
  @Override
  public void asignarAnalista(String nroSolicitud, String codigoAnalista, String codigoEstado) {
    String sql = "UPDATE IDO_PLATAFORMA_EXPE.EDTC_SOLICITUD \n" +
        " SET CO_ESTADO_SOLICITUD = ? , CO_ANALISTA_ASIGNADO = ?,FE_FECHA_ASIGNACION = SYSDATE\n" +
        " , FE_ACTUALIZA = SYSDATE, ID_ACTUALIZA = ?\n" +
        " WHERE NU_SOLICITUD_NUMERO = ?";
    jdbcTemplate.update(sql, codigoEstado, codigoAnalista, SecurityUtil.getUserInfo().getDni(), nroSolicitud);
  }
  @Override
  public void asignarAnalistaHistorial(String nroSolicitud, String codigoAnalista, String codigoEstado, String dniCoordinador) {
    System.out.print("ESTE ES EL DNI COORDINADOR: "+dniCoordinador);
    String sql2 = "INSERT INTO IDO_PLATAFORMA_EXPE.EDTV_SOLICITUD(ID_MOV_SOL, NU_SOLICITUD_NUMERO, NU_SECUENCIA, CO_ESTADO_SOLICITUD, CO_ANALISTA_ASIGNADO, FE_CREA, ID_CREA) " +
            "VALUES(IDO_PLATAFORMA_EXPE.EDSE_EDTV_SOL.nextval, ? , ?, ?, ?, SYSDATE,?)";
         KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(con2 -> {
        PreparedStatement ps2 = con2.prepareStatement(sql2, new String[]{"ID_MOV_SOL"});
        ps2.setString(1,nroSolicitud );
        String sql3 = "SELECT MAX(NU_SECUENCIA) FROM IDO_PLATAFORMA_EXPE.EDTV_SOLICITUD WHERE NU_SOLICITUD_NUMERO = ?";
        Integer maxNu_Sec = jdbcTemplate.queryForObject(sql3, Integer.class, nroSolicitud);
        if(maxNu_Sec != null){
          Integer nuevoID = (maxNu_Sec)+1;
          ps2.setInt(2, nuevoID);
        }else{
          ps2.setInt(2, 1);
        }
        ps2.setString(3, codigoEstado);
        ps2.setString(4, codigoAnalista);
        ps2.setString(5, dniCoordinador);
        return ps2;
    }, keyHolder);
  }
  @Override
  public void reasignarAnalista(String nroSolicitud, String codigoAnalista, String codigoEstado) {
    String sql = "UPDATE IDO_PLATAFORMA_EXPE.EDTC_SOLICITUD \n" +
            " SET CO_ESTADO_SOLICITUD = ? , CO_ANALISTA_ASIGNADO = ?,FE_FECHA_ASIGNACION = SYSDATE\n" +
            " , FE_ACTUALIZA = SYSDATE, ID_ACTUALIZA = ?\n" +
            " WHERE NU_SOLICITUD_NUMERO = ?";
    jdbcTemplate.update(sql, codigoEstado, codigoAnalista, SecurityUtil.getUserInfo().getDni(), nroSolicitud);
  }
  @Override
  public void reasignarAnalistaHistorial(String nroSolicitud, String codigoAnalista, String codigoEstado, String dniCoordinador) {
    String sql3 = "INSERT INTO IDO_PLATAFORMA_EXPE.EDTV_SOLICITUD(ID_MOV_SOL, NU_SOLICITUD_NUMERO, NU_SECUENCIA, CO_ESTADO_SOLICITUD, CO_ANALISTA_ASIGNADO, FE_CREA, ID_CREA) " +
            "VALUES(IDO_PLATAFORMA_EXPE.EDSE_EDTV_SOL.nextval, ?, ?, ?, ?, SYSDATE,?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(con2 -> {
      String sql2 = "SELECT MAX(NU_SECUENCIA) FROM IDO_PLATAFORMA_EXPE.EDTV_SOLICITUD WHERE NU_SOLICITUD_NUMERO = ?";
      Integer maxNu_Sec = jdbcTemplate.queryForObject(sql2, Integer.class, nroSolicitud);
      PreparedStatement ps2 = con2.prepareStatement(sql3, new String[]{"ID_MOV_SOL"});
      ps2.setString(1,nroSolicitud );
      if(maxNu_Sec != null){
        Integer nuevoID = (maxNu_Sec)+1;
        ps2.setInt(2, nuevoID);
      }else{
        ps2.setInt(2, 1);
      }
      ps2.setString(3, codigoEstado);
      ps2.setString(4, codigoAnalista);
      ps2.setString(5, dniCoordinador);
      return ps2;
    }, keyHolder);
  }
  @Override
  public List<DetalleSolicitudLibroBean> listarLibrosFullBySolicitud(Long idSolicitud) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("IDO_PLATAFORMA_EXPE")
        .withProcedureName("EDSP_DET_SOL_LIBRO_OBTENER")
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("P_VID_SOL", Types.VARCHAR),
            new SqlOutParameter("C_CRRESULT", Types.REF_CURSOR,
                new DetalleSolicitudLibroRowMapper()));
    SqlParameterSource prm = new MapSqlParameterSource()
        .addValue("P_VID_SOL", idSolicitud);
    Map<String, Object> result = simpleJdbcCall.execute(prm);
    List<DetalleSolicitudLibroBean> beanList = (List) result.get("C_CRRESULT");
    return beanList;
  }

  @Override
  public void actualizarArchivoRespuesta(Long idSolicitud, String codigoTipoArchivo) {
    String sql = "UPDATE IDO_PLATAFORMA_EXPE.EDTC_SOLICITUD \n" +
            " SET CO_TIPO_ARCHIVO_RESPUESTA = ? \n" +
        " ,ID_ACTUALIZA = ?, FE_ACTUALIZA = SYSDATE \n" +
        " WHERE ID_SOLICITUD = ?";
    jdbcTemplate.update(sql, codigoTipoArchivo, SecurityUtil.getUserInfo().getDni(), idSolicitud);
  }

  @Override
  public void actualizarEstadoDetalleSolLibroBySol(Long idSol, String estado) {
    String sql = "UPDATE IDO_PLATAFORMA_EXPE.EDTD_DET_SOL_LIBRO \n" +
        " SET CO_ESTADO = ? , ID_ACTUALIZA = ?, FE_ACTUALIZA = SYSDATE" +
        " WHERE ID_SOLICITUD = ?";
    jdbcTemplate.update(sql, estado, SecurityUtil.getUserInfo().getDni(), idSol);
  }

  @Override
  public void actualizarEstadoSolicitud(Long idSolicitud, String estado) {
    String sql = "UPDATE IDO_PLATAFORMA_EXPE.EDTC_SOLICITUD \n" +
        " SET CO_ESTADO_SOLICITUD = ? , FE_FECHA_ATENCION = SYSDATE, ID_ACTUALIZA = ?, FE_ACTUALIZA = SYSDATE\n" +
        " WHERE ID_SOLICITUD = ?";
    jdbcTemplate.update(sql, estado, SecurityUtil.getUserInfo().getDni() ,idSolicitud);
  }
  @Override
  public void actualizarEstadoSolicitudHistorial(Long idSolicitud, String estado, String numeroSolicitud) {

      String sql3 = "INSERT INTO IDO_PLATAFORMA_EXPE.EDTV_SOLICITUD(ID_MOV_SOL, NU_SOLICITUD_NUMERO, NU_SECUENCIA, CO_ESTADO_SOLICITUD, CO_ANALISTA_ASIGNADO, FE_CREA, ID_CREA) " +
              "VALUES(IDO_PLATAFORMA_EXPE.EDSE_EDTV_SOL.nextval, ?, ?, ?, ?, SYSDATE,?)";
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(con2 -> {
        PreparedStatement ps2 = con2.prepareStatement(sql3, new String[]{"ID_MOV_SOL"});
        ps2.setString(1,numeroSolicitud );
        String sql2 = "SELECT MAX(NU_SECUENCIA) FROM IDO_PLATAFORMA_EXPE.EDTV_SOLICITUD WHERE NU_SOLICITUD_NUMERO = ?";
        Integer maxNu_Sec = jdbcTemplate.queryForObject(sql2, Integer.class, numeroSolicitud);
        if(maxNu_Sec != null){
          Integer nuevoID = (maxNu_Sec)+1;
          ps2.setInt(2, nuevoID);
        }else{
          ps2.setInt(2, 1);
        }
        ps2.setString(3, estado);
        ps2.setString(4, SecurityUtil.getUserInfo().getDni());
        ps2.setString(5, SecurityUtil.getUserInfo().getDni());
        return ps2;
      }, keyHolder);

  }
  @Override
  public List<DetalleSolicitudArchivoFirmaBean> listarArchivoFirmaByDetalleId(Long idDetalle) {
    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("IDO_PLATAFORMA_EXPE")
        .withProcedureName("EDSP_DET_FIRMA_ARCHIVO_OBTENER")
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("P_VID_DET_FIRMA", Types.VARCHAR),
            new SqlOutParameter("C_CRRESULT", Types.REF_CURSOR,
                new DetalleSolicitudArchivoFirmaRowMapper()));
    SqlParameterSource prm = new MapSqlParameterSource()
        .addValue("P_VID_DET_FIRMA", idDetalle);
    Map<String, Object> result = simpleJdbcCall.execute(prm);
    List<DetalleSolicitudArchivoFirmaBean> beanList = (List) result.get("C_CRRESULT");
    return beanList;
  }

  @Override
  public void actualizarEstadoFirmaDetSolById(Long idDetalleSolicitud, String codEstadoFirma) {
    String sql = "UPDATE IDO_PLATAFORMA_EXPE.EDTD_DET_SOL_FIRMA \n" +
        " SET CO_ESTADO_FIRMA = ?, ID_ACTUALIZA = ?, FE_ACTUALIZA = SYSDATE \n" +
        " WHERE ID_DET_SOL_FIRMA = ?";
    jdbcTemplate.update(sql, codEstadoFirma, SecurityUtil.getUserInfo().getDni(), idDetalleSolicitud);
  }
  //Bruno Castro
  @Override
  public void eliminar(String nroSolicitud, String codigoEstado, String codigoUsuario) {
    String sql = "UPDATE IDO_PLATAFORMA_EXPE.EDTC_SOLICITUD \n" +
            " SET CO_ESTADO_SOLICITUD = ? , FE_FECHA_RECEPCION = SYSDATE, CO_USUARIO_RECEPCION = ?\n" +
            " , FE_ACTUALIZA = SYSDATE, ID_ACTUALIZA = ?\n" +
            " WHERE NU_SOLICITUD_NUMERO = ?";
    jdbcTemplate.update(sql, codigoEstado, codigoUsuario, SecurityUtil.getUserInfo().getDni(), nroSolicitud);
  }

}
