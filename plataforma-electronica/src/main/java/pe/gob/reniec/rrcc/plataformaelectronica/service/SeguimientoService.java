package pe.gob.reniec.rrcc.plataformaelectronica.service;

import java.util.List;

import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ConsultaSolSegRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.ValidarDatosSeguimientoRequest;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ApiPageResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.ConsultaSolSegResponse;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.SegSolDetFirmaResponse;

public interface SeguimientoService {
  String validarDatos(ValidarDatosSeguimientoRequest request);
  ApiPageResponse<ConsultaSolSegResponse> consultarSeguimiento(ConsultaSolSegRequest request, int page, int size);

  List<SegSolDetFirmaResponse> consultarSeguimientoSolFirma(String nroSolicitud);
}
