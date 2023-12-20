package pe.gob.reniec.rrcc.plataformaelectronica.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatoRegCivilFichaResponse {
  private String dni;
  private String primerApellido;
  private String segundoApellido;
  private String preNombres;
  private String celular;
  private String email;
  private byte[] foto;
  private byte[] firma;
  private String idTipoArchivoSusteno;
  private String nombreArchivoSustento;
  private String codigoArchivoSustento;
  private String codigoCondicion;
  private byte[] ficha;
  private String codigoEstadoRegistrador;
  private String fechaAlta;
  private String fechaBaja;
  private String codigoCargo;
  private String idTipoSolFirma;
}
