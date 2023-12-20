package pe.gob.reniec.rrcc.plataformaelectronica.service;

import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ValidarDatosRegLibroRequest;

public interface RegistroLibroService {
    String registrar(SolicitudBean solicitudBean);
    String validarDatos(ValidarDatosRegLibroRequest datosRequest);

}
