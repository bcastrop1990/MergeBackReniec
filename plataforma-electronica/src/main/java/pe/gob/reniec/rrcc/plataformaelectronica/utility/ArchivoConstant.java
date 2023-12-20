package pe.gob.reniec.rrcc.plataformaelectronica.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArchivoConstant {
  public static final String  ESTADO_PENDIENTE = "1";
  public static final String  ESTADO_ASIGNADO = "2";
  public static final String  ESTADO_ELIMINADO = "3";
  public static final String MSG_ARCHIVO_NO_DISPONIBLE = "El Archivo no se encuentra disponible.";
  public static final String MSG_ARCHIVO_NO_PERMITIDO = "Tipo de archivo no permitido";
  public static final String MSG_ARCHIVO_NO_EXISTE = "El archivo no existe.";
  public static final String MSG_ARCHIVO_ASIGNADO = "El archivo ya se encuentra asignado";
  public static final String TIPO_PERMITIDO = "pdf";
}
