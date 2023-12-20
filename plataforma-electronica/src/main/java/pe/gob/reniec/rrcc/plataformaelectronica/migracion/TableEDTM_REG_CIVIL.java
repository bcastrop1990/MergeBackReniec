package pe.gob.reniec.rrcc.plataformaelectronica.migracion;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TableEDTM_REG_CIVIL {

    public static void main(String[] args) {
        //VARIABLES:
        String detalleUbigeo = "  ";
        String localCorta = "  ";
        Integer newId = 0;

        // FORMATOS:

        SimpleDateFormat formatoHoraOrigen = new SimpleDateFormat("HHmmss");

        SimpleDateFormat formatoHoraDestino = new SimpleDateFormat("HH:mm:ss");

        SimpleDateFormat formatoFechaOrigen = new SimpleDateFormat("yyyyMMdd");

        SimpleDateFormat formatoFechaDestino = new SimpleDateFormat("dd/MM/yyyy");

        SimpleDateFormat formatoFinal = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        String origenJDBCUrl = "jdbc:oracle:thin:@151.101.120.187:1530:bdpredni";
        String destinoJDBCUrl = "jdbc:oracle:thin:@//151.101.120.191:1522/desarc";
        String usuario = "IDO_PADRON_NOM_PRE";
        String contraseña = "reniec2014";
        String usuario2 = "IDO_PLATAFORMA_EXPE";
        String contraseña2 = "plataformaexpe2022";
        try {
            // Conectando a la base de datos de origen:
            Connection conexionOrigen = DriverManager.getConnection(origenJDBCUrl, usuario, contraseña);
            // Conectando a la base de datos de destino:
            Connection conexionDestino = DriverManager.getConnection(destinoJDBCUrl, usuario2, contraseña2);

            // Consulta SQL para seleccionar datos de la tabla de origen:
            String consultaSQL = "SELECT * FROM DBALEM01.RCTV_REGISTRADOR_CIVIL WHERE ES_REGISTRADOR_CIVIL = '1'";
            PreparedStatement stmtOrigen = conexionOrigen.prepareStatement(consultaSQL);

            // Consulta SQL para insertar datos en la tabla de destino:
            String insercionSQL = "INSERT INTO IDO_PLATAFORMA_EXPE.EDTM_REG_CIVIL(ID_REG_CIVIL, ID_TIPO_SOLICITUD_FIRMA," +
                    " CO_TIPO_DOC_IDENTIDAD, NU_DOC_IDENTIDAD, AP_PRIMER_APELLIDO, AP_SEGUNDO_APELLIDO, NO_PRENOMBRES, " +
                    "CO_OREC_REG_CIVIL, DE_DETALLE_OREC_CORTA, CO_ESTADO_REGISTRADOR, ID_CREA, FE_CREA, ID_ACTUALIZA, FE_ACTUALIZA, " +
                    "FE_CAMBIO_ESTADO, NU_CELULAR, DE_MAIL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmtDestino = conexionDestino.prepareStatement(insercionSQL);

            //EJECUTAR TABLA DE ORIGEN:
            ResultSet resultadoOrigen = stmtOrigen.executeQuery();

            while (resultadoOrigen.next()){

                String co_Oficina = resultadoOrigen.getString("CO_OFICINA_RC");

                //Consulta SQL a GETR_LOCAL:
                String consultaGetr_LocalSQL = "SELECT * FROM GETR_LOCAL WHERE CO_LOCAL ="+co_Oficina+"";
                PreparedStatement stmtGetrLoalOrigen = conexionOrigen.prepareStatement(consultaGetr_LocalSQL);

                //EJECUTAR GETR_LOCAL:
                ResultSet resultadoGetrLocalOrigen = stmtGetrLoalOrigen.executeQuery();

                if (resultadoGetrLocalOrigen.next()) {
                    // OBTENER CAMPOS DE GETR_LOCAL:
                    String Cod_Cont = resultadoGetrLocalOrigen.getString("CO_CONTINENTE");
                    String Cod_Pais = resultadoGetrLocalOrigen.getString("CO_PAIS");
                    String Cod_Dep = resultadoGetrLocalOrigen.getString("CO_DEPARTAMENTO");
                    String Cod_Dist = resultadoGetrLocalOrigen.getString("CO_DISTRITO");
                    String Cod_Prov = resultadoGetrLocalOrigen.getString("CO_PROVINCIA");
                    String Cod_CentP = resultadoGetrLocalOrigen.getString("CO_CENTRO_POBLADO");

                    localCorta = resultadoGetrLocalOrigen.getString("DE_LOCAL_CORTA");

                    String consultaGevw_UbigeosSQL = "SELECT DE_UBIGEO_DETALLE FROM GEVW_UBIGEOS WHERE CO_CONTINENTE=? AND CO_PAIS=? AND CO_DEPARTAMENTO=? AND CO_DISTRITO=? AND CO_PROVINCIA=? AND CO_CENTRO_POBLADO_O=?";
                    PreparedStatement stmtGevw_UbigeosOrigen = conexionOrigen.prepareStatement(consultaGevw_UbigeosSQL);
                    stmtGevw_UbigeosOrigen.setString(1, Cod_Cont);
                    stmtGevw_UbigeosOrigen.setString(2, Cod_Pais);
                    stmtGevw_UbigeosOrigen.setString(3, Cod_Dep);
                    stmtGevw_UbigeosOrigen.setString(4, Cod_Dist);
                    stmtGevw_UbigeosOrigen.setString(5, Cod_Prov);
                    stmtGevw_UbigeosOrigen.setString(6, Cod_CentP);

                    // EJECUTAR TABLA DE ORIGEN:
                    ResultSet resultadoGevw_UbigeosOrigen = stmtGevw_UbigeosOrigen.executeQuery();

                    if (resultadoGevw_UbigeosOrigen.next()) {
                        detalleUbigeo = resultadoGevw_UbigeosOrigen.getString("DE_UBIGEO_DETALLE");
                    } else {
                        detalleUbigeo = "  ";
                    }
                    stmtGevw_UbigeosOrigen.close();
                } else {
                    detalleUbigeo = "  ";
                    localCorta = "  ";
                }
                stmtGetrLoalOrigen.close();

                //Consulta Maximo Valor del ID:
                String consultaMaxSQL = "SELECT MAX(ID_REG_CIVIL) AS MAX_ID FROM IDO_PLATAFORMA_EXPE.EDTM_REG_CIVIL";
                PreparedStatement stmtMax = conexionDestino.prepareStatement(consultaMaxSQL);

                //EJECUTAR SELECT MAX VALUE:
                ResultSet resultadoMax = stmtMax.executeQuery();

                if (resultadoMax.next()) {
                    //ID
                    newId = (resultadoMax.getInt("MAX_ID"))+1;
                } else {
                    System.err.print("Error MAX ID");
                }
                stmtMax.close();

                // Establecer valores en la consulta de inserción
                stmtDestino.setInt(1, newId);
                stmtDestino.setString(2, ConstantUtil.COD_ESTADO_ACTIVO);
                stmtDestino.setString(3, ConstantUtil.CO_DNI);
                if((resultadoOrigen.getString("NU_DNI_REGISTRADOR_CIVIL")) != null){
                    stmtDestino.setString(4, resultadoOrigen.getString("NU_DNI_REGISTRADOR_CIVIL"));
                }else{
                    stmtDestino.setString(4, ConstantUtil.EMPTY_VALUE);
                }
                if((resultadoOrigen.getString("AP_PATERNO_REGISTRADOR")) != null){
                    stmtDestino.setString(5, resultadoOrigen.getString("AP_PATERNO_REGISTRADOR"));
                }else{
                    stmtDestino.setString(5, ConstantUtil.EMPTY_VALUE);
                }
                if((resultadoOrigen.getString("AP_MATERNO_REGISTRADOR")) != null){
                    stmtDestino.setString(6, resultadoOrigen.getString("AP_MATERNO_REGISTRADOR"));
                }else{
                    stmtDestino.setString(6, ConstantUtil.EMPTY_VALUE);
                }
                if((resultadoOrigen.getString("NO_REGISTRADOR")) != null){
                    stmtDestino.setString(7, resultadoOrigen.getString("NO_REGISTRADOR"));
                }else{
                    stmtDestino.setString(7, ConstantUtil.EMPTY_VALUE);
                }
                if((resultadoOrigen.getString("CO_OFICINA_RC")) != null){
                    stmtDestino.setString(8, resultadoOrigen.getString("CO_OFICINA_RC"));
                }else{
                    stmtDestino.setString(8, ConstantUtil.EMPTY_VALUE);
                }
                if(localCorta != null){
                    stmtDestino.setString(9, localCorta);
                }else{
                    stmtDestino.setString(9, ConstantUtil.EMPTY_VALUE);
                }
                stmtDestino.setString(10, ConstantUtil.COD_ESTADO_ACTIVO);
                if((resultadoOrigen.getString("NU_DNI_CREACION")) != null){
                    stmtDestino.setString(11,  resultadoOrigen.getString("NU_DNI_CREACION"));
                }else{
                    stmtDestino.setString(11, ConstantUtil.EMPTY_VALUE);
                }
                // FECHA CREACION:
                String hoCrea = resultadoOrigen.getString("HO_CREACION");
                String feCrea = resultadoOrigen.getString("FE_CREACION");

                if (hoCrea == null || hoCrea.trim().isEmpty() || feCrea == null|| feCrea.trim().isEmpty()) {
                    stmtDestino.setNull(12, java.sql.Types.DATE);
                }else {
                    Date horaCreacion = formatoHoraOrigen.parse(hoCrea);
                    Date fechaCreacion = formatoFechaOrigen.parse(feCrea);
                    // Combinar hora y fecha y formatear en el formato deseado
                    String fechaCreaFormateada = formatoFechaDestino.format(fechaCreacion) + " " + formatoHoraDestino.format(horaCreacion);
                    Date fechaCreaMigracion = formatoFinal.parse(fechaCreaFormateada);
                    stmtDestino.setDate(12, new java.sql.Date(fechaCreaMigracion.getTime()));
                }
                if((resultadoOrigen.getString("NU_DNI_ACTUALIZACION")) != null){
                    stmtDestino.setString(13, resultadoOrigen.getString("NU_DNI_ACTUALIZACION"));
                }else{
                    stmtDestino.setString(13, ConstantUtil.EMPTY_VALUE);
                }
                // FECHA ACTUALIZACION:
                String hoActualizacion = resultadoOrigen.getString("HO_ACTUALIZACION");
                String feActualizacion = resultadoOrigen.getString("FE_ACTUALIZACION");

                if (hoActualizacion == null || hoActualizacion.trim().isEmpty() || feActualizacion == null|| feActualizacion.trim().isEmpty()) {
                    stmtDestino.setNull(14, java.sql.Types.DATE);
                }else{
                    Date horaActualizacion = formatoHoraOrigen.parse(hoActualizacion);
                    Date fechaActualizacion = formatoFechaOrigen.parse(feActualizacion);

                    // Combinar hora y fecha y formatear en el formato deseado
                    String fechaActFormateada = formatoFechaDestino.format(fechaActualizacion) + " " + formatoHoraDestino.format(horaActualizacion);
                    Date fechaActMigracion = formatoFinal.parse(fechaActFormateada);

                    stmtDestino.setDate(14, new java.sql.Date(fechaActMigracion.getTime()));

                }
                stmtDestino.setNull(15, java.sql.Types.DATE);
                stmtDestino.setString(16, ConstantUtil.EMPTY_VALUE);
                stmtDestino.setString(17, ConstantUtil.EMPTY_VALUE);

                // Ejecutar la inserción
                stmtDestino.executeUpdate();

                Thread.sleep(200);
            }

            // Cerrar conexiones
            stmtOrigen.close();
            stmtDestino.close();
            conexionOrigen.close();
            conexionDestino.close();

            System.out.println("Migración completada con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error durante la migración: " + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Error de Parseo: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
