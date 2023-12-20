package pe.gob.reniec.rrcc.plataformaelectronica.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.gob.reniec.rrcc.plataformaelectronica.config.NumeracionProperties;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.SolicitudNumeracionDao;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.SolicitudNumeracionBean;
import pe.gob.reniec.rrcc.plataformaelectronica.security.SecurityUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.service.SolicitudNumeracionService;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ConstantUtil;

@Service
@AllArgsConstructor
public class SolicitudNumeracionServiceImpl implements SolicitudNumeracionService {
  private SolicitudNumeracionDao solicitudNumeracionDao;
  private NumeracionProperties numeracionProperties;
  @Override
  public SolicitudNumeracionBean obtener() {
    int periodo = LocalDate.now().getYear();
    Optional<SolicitudNumeracionBean> numeracionBean = solicitudNumeracionDao.obtenerPorPeriodo(periodo);
    if (!numeracionBean.isPresent()) {
      SolicitudNumeracionBean numeracionRegister =
          SolicitudNumeracionBean.builder()
              .correlativo(numeracionProperties.getInicial())
              .longitud(numeracionProperties.getLongitud())
              .periodo(periodo)
              .estado(ConstantUtil.COD_ESTADO_ACTIVO)
              .idCrea(SecurityUtil.getUserInfo().getDni())
          .build();
      solicitudNumeracionDao.registrar(numeracionRegister);
      return  numeracionRegister;
    }
    SolicitudNumeracionBean numeracionActual = SolicitudNumeracionBean.builder()
        .idSolicitudNumeracion(numeracionBean.get().getIdSolicitudNumeracion())
        .correlativo(numeracionBean.get().getCorrelativo())
        .longitud(numeracionBean.get().getLongitud())
        .periodo(numeracionBean.get().getPeriodo())
        .idActualiza(SecurityUtil.getUserInfo().getDni())
        .build();
    solicitudNumeracionDao.incrementar(numeracionActual);
    return numeracionActual;
  }
}
