package pe.gob.reniec.rrcc.plataformaelectronica.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SolicitudConstant {
  public static final String ESTADO_REGISTRADO = "1";
  public static final String ESTADO_RECEPCIONADO = "2";
  public static final String ESTADO_ASIGNADO = "3";
  public static final String ESTADO_ATENTIDO = "4";
  public static final String ESTADO_REASIGNADO = "5";
  public static final String TIPO_FIRMA = "2";
  public static final String TIPO_LIBRO = "1";
  public static final String TIPO_DOC_DNI = "01";
  public static final String SOLICITUD_NO_EXISTE = "La solicitud no existe.";
  public static final String ACTIVO = "1";
  public static final String INACTIVO = "0";
  public static final String USO_ARCH_SUSTENTO = "S";
  public static final String USO_ARCH_RESPUESTA = "R";
  public static final String MSG_DNI_SIN_SOLICITUD = "El dni no tiene solicitud de firma";
  public static final String MSG_TIPO_ARCHIVO_NO_REGISTRADO = "El tipo de archivo no existe en el detalle";
  public static final String MSG_SOLICITUD_ATENDIDA = "La solicitud ya se encuentra atendida.";
  public static final String MSG_EXISTE_SOLICITUD_FIRMA_PENDIENTE = "Primero debe terminar de atender todas las solicitudes.";
  public static final String SOLICITUD_LIBRO = "Libro de actas registrales";
  public static final String SOLICITUD_FIRMA = "Registro de Firmas";
}
