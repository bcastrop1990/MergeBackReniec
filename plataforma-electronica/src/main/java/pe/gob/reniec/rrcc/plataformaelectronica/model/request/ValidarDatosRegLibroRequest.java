package pe.gob.reniec.rrcc.plataformaelectronica.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ValidarDatosRegLibroRequest {
 @NotNull(message = "Datos de persona es requerido")
 @Valid
 private DatosPersonaRegLibroRequest datosPersona;
 @NotNull(message = "Datos de oficinas es requerido")
 @Valid
 private DatosOficinaRegLibroRequest datosOficina;
}
