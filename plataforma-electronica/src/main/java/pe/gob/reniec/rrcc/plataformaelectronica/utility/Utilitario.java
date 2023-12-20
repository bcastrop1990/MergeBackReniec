package pe.gob.reniec.rrcc.plataformaelectronica.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pe.gob.reniec.rrcc.plataformaelectronica.exception.ApiErrorException;
import pe.gob.reniec.rrcc.plataformaelectronica.exception.ApiValidateException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utilitario {
  public static String generateNumeracion(int periodo,int longitud, int numero) {
    return String.format("%s%0" + longitud + "d",periodo, Integer.valueOf(numero));
  }

  public static LocalDate parseOfPattern(String fecha, String pattern) {
    try {
      return LocalDate.parse(fecha, DateTimeFormatter.ofPattern(pattern));
    } catch (Exception e) {

      throw new ApiErrorException(ConstantUtil.MSG_ERROR_PARSE_FECHA);
    }
  }
}
