package pe.gob.reniec.rrcc.plataformaelectronica.model.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OficinaBean {
  private String codigoOrec;
  private String descripcionLocalLarga;
  private String descripcionLocalCorta;
  private String descripcionUbigeo;
  private String descripcionUbigeoDetalle;
  private String codigoDepartamento;
  private String codigoProvincia;
  private String codigoDistrito;
  private String codigoCentroPoblado;
  private String nombreDepartamento;
  private String nombreProvincia;
  private String nombreDistrito;
  private String descripcionCentroPoblado;
}
