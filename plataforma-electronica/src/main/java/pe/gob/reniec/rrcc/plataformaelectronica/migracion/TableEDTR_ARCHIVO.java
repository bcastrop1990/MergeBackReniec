package pe.gob.reniec.rrcc.plataformaelectronica.migracion;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TableEDTR_ARCHIVO {
    public static void main(String[] args) {
        //VARIABLES:
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
            String insercionSQL = "INSERT INTO \"IDO_PLATAFORMA_EXPE\".\"EDTR_ARCHIVO\" (ID_ARCHIVO, CO_NOMBRE, DE_NOMBRE_ORIGINAL, CO_EXTENSION, IM_ARCHIVO, CO_ESTADO, FE_CREA, FE_ACTUALIZA, ID_CREA, ID_ACTUALIZA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmtDestino = conexionDestino.prepareStatement(insercionSQL);

            //EJECUTAR TABLA DE ORIGEN:
            ResultSet resultadoOrigen = stmtOrigen.executeQuery();

            while (resultadoOrigen.next()) {

                //Consuñta Maximo Valor del ID:
                String consultaMaxSQL = "SELECT MAX(ID_ARCHIVO) AS MAX_ID FROM IDO_PLATAFORMA_EXPE.EDTR_ARCHIVO";
                PreparedStatement stmtMax = conexionDestino.prepareStatement(consultaMaxSQL);

                //EJECUTAR SELECT MAX VALUE:
                ResultSet resultadoMax = stmtMax.executeQuery();

                if (resultadoMax.next()) {
                    //ID
                    newId = (resultadoMax.getInt("MAX_ID")) + 1;
                }
                stmtMax.close();

                // Establecer valores en la consulta de inserción
                stmtDestino.setInt(1, newId);
                String cifrado = resultadoOrigen.getString("NU_DOC_ALTA");
                cifrado = UUID.randomUUID().toString();
                stmtDestino.setString(2, cifrado);
                if (resultadoOrigen.getString("NU_DOC_ALTA") != null && !resultadoOrigen.getString("NU_DOC_ALTA").isEmpty()) {
                    stmtDestino.setString(3, resultadoOrigen.getString("NU_DOC_ALTA"));
                } else {
                    stmtDestino.setString(3, ConstantUtil.EMPTY_VALUE);
                }
                stmtDestino.setString(4, ConstantUtil.PDF_FORMAT);
                //BLOB
                InputStream input = resultadoOrigen.getBinaryStream("BL_FICHA_REGISTRADOR");
                stmtDestino.setBinaryStream(5, input);

                stmtDestino.setInt(6, ConstantUtil.ONE_CODE);
                // FECHA CREACION:
                String hoCrea = resultadoOrigen.getString("HO_CREACION");
                String feCrea = resultadoOrigen.getString("FE_CREACION");

                if (hoCrea != null || feCrea != null || hoCrea.trim().isEmpty() || feCrea.trim().isEmpty()) {
                    stmtDestino.setNull(7, java.sql.Types.DATE);
                }else {
                    Date horaCreacion = formatoHoraOrigen.parse(hoCrea);
                    Date fechaCreacion = formatoFechaOrigen.parse(feCrea);
                    // Combinar hora y fecha y formatear en el formato deseado
                    String fechaCreaFormateada = formatoFechaDestino.format(fechaCreacion) + " " + formatoHoraDestino.format(horaCreacion);
                    Date fechaCreaMigracion = formatoFinal.parse(fechaCreaFormateada);
                    stmtDestino.setDate(7, new java.sql.Date(fechaCreaMigracion.getTime()));
                }
                // FECHA ACTUALIZACION:
                String hoActualizacion = resultadoOrigen.getString("HO_ACTUALIZACION");
                String feActualizacion = resultadoOrigen.getString("FE_ACTUALIZACION");

                if (hoActualizacion == null || hoActualizacion.trim().isEmpty() || feActualizacion == null || feActualizacion.trim().isEmpty()) {
                    stmtDestino.setNull(8, java.sql.Types.DATE);
                } else {
                    Date horaActualizacion = formatoHoraOrigen.parse(hoActualizacion);
                    Date fechaActualizacion = formatoFechaOrigen.parse(feActualizacion);
                    // Combinar hora y fecha y formatear en el formato deseado
                    String fechaActFormateada = formatoFechaDestino.format(fechaActualizacion) + " " + formatoHoraDestino.format(horaActualizacion);
                    Date fechaActMigracion = formatoFinal.parse(fechaActFormateada);
                    stmtDestino.setDate(8, new java.sql.Date(fechaActMigracion.getTime()));
                }
                if((resultadoOrigen.getString("NU_DNI_CREACION")) != null){
                    stmtDestino.setString(9, resultadoOrigen.getString("NU_DNI_CREACION"));
                }else{
                    stmtDestino.setString(9, ConstantUtil.EMPTY_VALUE);
                }
                if((resultadoOrigen.getString("NU_DNI_ACTUALIZACION")) != null){
                    stmtDestino.setString(10, resultadoOrigen.getString("NU_DNI_ACTUALIZACION"));
                }else{
                    stmtDestino.setString(10, ConstantUtil.EMPTY_VALUE);
                }

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