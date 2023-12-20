package pe.gob.reniec.rrcc.plataformaelectronica.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.RegistradorCivilBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.RegistradorCivilDetalleBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.RegistradorCivilRuipinBean;

import java.util.Optional;

public interface RegistradorCivilDao {
  Optional<RegistradorCivilBean> buscarPorDni(String dni);
  Page<RegistradorCivilBean> consultarRegCivilPorOficina(RegistradorCivilBean bean, Pageable pageable);
  Page<RegistradorCivilBean> consultarRegCivilPorDatos(RegistradorCivilBean bean, Pageable pageable);
  Optional<RegistradorCivilRuipinBean> consultarRegCivilRuipinPorDatos(RegistradorCivilRuipinBean bean);
  Optional<RegistradorCivilDetalleBean> consultaUltimoMovimientoPorDni(String dni);

  void registrarDetalle(RegistradorCivilDetalleBean regCivilDetBean);

  void registrar(RegistradorCivilBean regCivilBean);

  void actualizarBajaPorDni(RegistradorCivilBean regCivilBean);

  void actualizarActualizacionPorDni(RegistradorCivilBean regCivilBean);

  void actualizarReingresoPorDni(RegistradorCivilBean regCivilBean);
}
