package pe.gob.reniec.rrcc.plataformaelectronica.dao;

import java.util.Optional;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudNumeracionBean;

public interface SolicitudNumeracionDao {
  Optional<SolicitudNumeracionBean> obtenerPorPeriodo(int periodo);
  void incrementar(SolicitudNumeracionBean bean);
  void registrar(SolicitudNumeracionBean bean);
}
