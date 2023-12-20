package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class ValidarDatosRegFirmaRequest {
 @NotNull(message = "Datos de persona es requerido")
 @Valid
 private DatosPersonaRegFirmaRequest datosPersona;
 @NotNull(message = "Datos de oficinas es requerido")
 @Valid
 private DatosOficinaRegFirmaRequest datosOficina;
}
