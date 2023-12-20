package pe.gob.reniec.rrcc.plataformaelectronica.service;

import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.RegistradorCivilBean;
import pe.gob.reniec.rrcc.plataformaelectronica.model.request.*;
import pe.gob.reniec.rrcc.plataformaelectronica.model.response.*;

public interface RegistradorCivilService {

    String validarSituacion(ValidaRegCivilRequest request);

    ApiPageResponse<BusqRegCivilResponse> consultarRegCivilPorOficina(BusqPorOficinaRegCivilRequest request, int page, int size);

    ApiPageResponse<BusqRegCivilResponse> consultarRegCivilPorDatos(BusqPorDatosRegCivilRequest request, int page, int size);

    BusqRegCivilRuipinResponse consultarRegCivilPorDatosRuipin(BusqPorDatosRegCivilRuipinRequest request);
    FichaRegCivilResponse consultarFicha(String dni, String nroSolicitud);

    void registrarFicha(RegistrarFichaRequest request);

    void desaprobarFicha(DesaprobarFichaRequest request);

    void atenderFicha(AtenderFichaRequest request);
    ConsultaFichaByDniResponse consultaFichaByDni(String dni);

    //GINO SUBIR CAMBIO
    ConsultaFichaByDniResponse consultaRegCivilByDni(String dni);

    }
