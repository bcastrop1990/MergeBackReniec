package pe.gob.reniec.rrcc.plataformaelectronica.service;

import pe.gob.reniec.rrcc.plataformaelectronica.model.thirdparty.FirmaHuellaResponseDto;

public interface ApiImagenService {
  byte[] obtenerFotoPorDni(String dni);
  FirmaHuellaResponseDto consultarPorDni(String dni);
}
