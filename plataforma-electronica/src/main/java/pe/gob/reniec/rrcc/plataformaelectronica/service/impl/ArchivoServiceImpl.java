package pe.gob.reniec.rrcc.plataformaelectronica.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.reniec.rrcc.plataformaelectronica.dao.ArchivoDao;
import pe.gob.reniec.rrcc.plataformaelectronica.exception.ApiErrorException;
import pe.gob.reniec.rrcc.plataformaelectronica.exception.ApiValidateException;
import pe.gob.reniec.rrcc.plataformaelectronica.model.bean.ArchivoBean;
import pe.gob.reniec.rrcc.plataformaelectronica.security.SecurityUtil;
import pe.gob.reniec.rrcc.plataformaelectronica.service.ArchivoService;
import pe.gob.reniec.rrcc.plataformaelectronica.utility.ArchivoConstant;

@Service
@AllArgsConstructor
@Slf4j
public class ArchivoServiceImpl implements ArchivoService {
    private ArchivoDao archivoDao;

    @Override
    public String upload(MultipartFile file) {
        try {
            String codigoNombre = UUID.randomUUID().toString();
            ArchivoBean archivoBean = ArchivoBean.builder()
                    .archivo(file.getBytes())
                    .nombreOriginal(StringUtils.stripFilenameExtension(file.getOriginalFilename()))
                    .codigoNombre(codigoNombre)
                    .extension(StringUtils.getFilenameExtension(file.getOriginalFilename()))
                    .estado(ArchivoConstant.ESTADO_PENDIENTE)
                    .idCrea(SecurityUtil.getUserInfo().getDni())
                    .build();
            if (!archivoBean.getExtension().equalsIgnoreCase(ArchivoConstant.TIPO_PERMITIDO)) {
                throw new ApiValidateException(ArchivoConstant.MSG_ARCHIVO_NO_PERMITIDO);
            }
            archivoDao.registrar(archivoBean);
            System.out.print("codigoNombre:"+codigoNombre);
            return codigoNombre;
        }
        catch (ApiValidateException e){
            throw new ApiValidateException(e.getMessage());
        }
        catch (Exception e){
            throw new ApiErrorException(e);
        }
    }
    @Override
    public String uploadDocAtencion(MultipartFile file) {
        try {
            String codigoNombre = UUID.randomUUID().toString();
            ArchivoBean archivoBean = ArchivoBean.builder()
                    .archivo(file.getBytes())
                    .nombreOriginal(StringUtils.stripFilenameExtension(file.getOriginalFilename()))
                    .codigoNombre(codigoNombre)
                    .extension(StringUtils.getFilenameExtension(file.getOriginalFilename()))
                    .estado(ArchivoConstant.ESTADO_PENDIENTE)
                    .idCrea(SecurityUtil.getUserInfo().getDni())
                    .build();
            if (!archivoBean.getExtension().equalsIgnoreCase(ArchivoConstant.TIPO_PERMITIDO)) {
                throw new ApiValidateException(ArchivoConstant.MSG_ARCHIVO_NO_PERMITIDO);
            }
            archivoDao.registrarDocAtencion(archivoBean);
            System.out.print("codigoNombre:"+codigoNombre);
            return codigoNombre;
        }
        catch (ApiValidateException e){
            throw new ApiValidateException(e.getMessage());
        }
        catch (Exception e){
            throw new ApiErrorException(e);
        }
    }

/*
    @Override
    public List<String> uploadList(MultipartFile files) {
        List<String> codigoNombres = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String codigoNombre = UUID.randomUUID().toString();
                ArchivoBean archivoBean = ArchivoBean.builder()
                        .archivo(file.getBytes())
                        .nombreOriginal(StringUtils.stripFilenameExtension(file.getOriginalFilename()))
                        .codigoNombre(codigoNombre)
                        .extension(StringUtils.getFilenameExtension(file.getOriginalFilename()))
                        .estado(ArchivoConstant.ESTADO_PENDIENTE)
                        .idCrea(SecurityUtil.getUserInfo().getDni())
                        .build();

                if (!archivoBean.getExtension().equalsIgnoreCase(ArchivoConstant.TIPO_PERMITIDO)) {
                    throw new ApiValidateException(ArchivoConstant.MSG_ARCHIVO_NO_PERMITIDO);
                }

                archivoDao.registrar(archivoBean);
                codigoNombres.add(codigoNombre);
            } catch (ApiValidateException e) {
                throw new ApiValidateException(e.getMessage());
            } catch (Exception e) {
                throw new ApiErrorException(e);
            }
        }

        return codigoNombres;
    }
*/

    @Override
    public void delete(String codigo) {
        ArchivoBean archivo = archivoDao.obtenerPorCodigo(codigo)
            .orElseThrow(() -> new ApiValidateException(ArchivoConstant.MSG_ARCHIVO_NO_EXISTE));
        if (archivo.getEstado().equals(ArchivoConstant.ESTADO_ASIGNADO)) {
            throw new ApiValidateException(ArchivoConstant.MSG_ARCHIVO_ASIGNADO);
        }
        archivoDao.actualizarEstado(archivo.getId(), ArchivoConstant.ESTADO_ELIMINADO);
    }

    @Override
    public ArchivoBean download(String codigo) {
        ArchivoBean archivo = archivoDao.obtenerPorCodigo(codigo)
            .orElseThrow(() -> new ApiValidateException(ArchivoConstant.MSG_ARCHIVO_NO_EXISTE));
        return archivo;
    }

    @Override
    public Long getIdByCodigo(String codigo) {
        ArchivoBean archivo = archivoDao.obtenerPorCodigo(codigo)
            .orElseThrow(() -> new ApiValidateException(ArchivoConstant.MSG_ARCHIVO_NO_EXISTE));
        if (!archivo.getEstado().equals(ArchivoConstant.ESTADO_PENDIENTE)) {
            throw new ApiValidateException(ArchivoConstant.MSG_ARCHIVO_NO_DISPONIBLE);
        }
        return archivo.getId();
    }
}
