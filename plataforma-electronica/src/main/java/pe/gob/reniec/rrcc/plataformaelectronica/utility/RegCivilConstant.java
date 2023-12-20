package pe.gob.reniec.rrcc.plataformaelectronica.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegCivilConstant {
  public static final String ESTADO_ALTA_HABILITADO = "1";
  public static final String ESTADO_BAJA_INHABILITADO = "2";
  public static final String SITUACION_REINGRESO = "REINGRESO";
  public static final String SITUACION_NUEVO = "NUEVO";
  public static final String SITUACION_ACTUALIZA = "ACTUALIZA";
  public static final String SITUACION_BAJA = "BAJA";
  public static final String SITUACION_NINGUNA = "NINGUNA";
  public static final String MSG_REG_CIVIL_NO_EXISTE = "El registrado civil no existe en el padrón Reg. Civil.";
  public static final String MSG_REG_CIVIL_INHABILITADO = "El registrador se encuentra inhabilitado.";
  public static final String MSG_REG_CIVIL_HABILITADO = "El registrador se encuentra habilitado.";
  public static final String MSG_REG_CIVIL_INHABILITADO_DESDE = "El registrador se encuentra inhabilitado desde la fecha %s.";
  public static final String MSG_REG_CIVIL_HABILITADO_OREC = "El registrador se encuentra habilitado en la OREC %s.";
  public static final String COD_SITUACION_ALTA = "1";
  public static final String COD_SITUACION_REINGRESO = "2";
  public static final String COD_SITUACION_BAJA = "3";
  public static final String COD_SITUACION_DESCANSO_TEMPORAL = "4";

  public static final String COD_CARGO_REGISTRADOR = "1";
  public static final String COD_CARGO_JEFE = "2";
  public static final String DES_CARGO_REGISTRADOR = "REGISTRADOR";
  public static final String DES_CARGO_JEFE = "JEFE";
  public static final String DES_ALTA_HABILITADO = "ALTA HABILITADO";
  public static final String DES_BAJA_INHABILITADO = "BAJA INHABILITADO";

}
