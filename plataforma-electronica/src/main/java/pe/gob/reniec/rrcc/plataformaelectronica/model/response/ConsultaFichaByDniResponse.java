package pe.gob.reniec.rrcc.plataformaelectronica.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaFichaByDniResponse {
  private String codigoOrec;
  private String descripcionOrec;
  private String descripcionUbigeo;
  private String numeroDocumento;
  private String nombres;
  private String primerApellido;
  private String segundoApellido;
  private String celular;
  private String email;
  private String cargoRegistrador;
  private String estadoRegistrador;
  private String foto;
  private String firma;
  private String ficha;
  private String fechaAlta;
  private String tipoArchivoAlta;
  private String nombreArchivoAlta;
  private String fechaBaja;
  private String tipoArchivoBaja;
  private String nombreArchivoBaja;
}
