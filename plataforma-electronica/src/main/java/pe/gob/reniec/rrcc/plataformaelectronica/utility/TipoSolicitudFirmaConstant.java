package pe.gob.reniec.rrcc.plataformaelectronica.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TipoSolicitudFirmaConstant {
  public static final String ALTA = "1";
  public static final String BAJA = "2";
  public static final String ACTUALIZAR = "3";
  public static final String MSG_TIPO_NO_ENCONTRADO = "El tipo de solicitud no existe.";
}
