create or replace procedure EDSP_REGCIV_DET_REGISTRAR (
p_DE_OBSERVACION VARCHAR2 
,p_NU_CELULAR VARCHAR2
,p_ID_TIPO_SOLICITUD_FIRMA VARCHAR2
,p_CO_OREC_REG_CIVIL VARCHAR2
,p_CO_ESTADO_FORMATO VARCHAR2 
,p_DE_UBIGEO_DETALLE VARCHAR2
,p_AP_PRIMER_APELLIDO VARCHAR2
,p_CO_CONDICION VARCHAR2 
,p_FE_FECHA_BAJA DATE 
,p_CO_TIPO_DOC_IDENTIDAD VARCHAR2
,p_CO_MOTIVO_ACTUALIZA VARCHAR2 
,p_FE_FECHA_ACTUALIZACION DATE 
,p_AP_SEGUNDO_APELLIDO VARCHAR2
,p_ID_CREA VARCHAR2
,p_CO_ESTADO_REGISTRADOR VARCHAR2
,p_DE_MAIL VARCHAR2
,p_FE_FECHA_ALTA DATE 
,p_DE_DETALLE_OREC_CORTA VARCHAR2
,p_FE_FECHA_INICIO DATE
,p_FE_FECHA_FIN DATE
,p_NO_PRENOMBRES VARCHAR2
,p_NU_DOC_IDENTIDAD VARCHAR2
,p_CO_CARGO_REGISTRADOR VARCHAR2
,p_ID_DET_SOL_FIRMA NUMBER
) 
AS
begin
    insert into EDTC_REG_CIVIL(
    ID_REGISTRO
    ,DE_OBSERVACION
    ,NU_CELULAR
    ,FE_CREA
    ,ID_TIPO_SOLICITUD_FIRMA  
    ,CO_OREC_REG_CIVIL
    ,CO_ESTADO_FORMATO
    ,DE_UBIGEO_DETALLE
    ,AP_PRIMER_APELLIDO
    ,CO_CONDICION
    ,FE_FECHA_BAJA
    ,CO_TIPO_DOC_IDENTIDAD
    ,CO_MOTIVO_ACTUALIZA
    ,FE_FECHA_ACTUALIZACION
    ,AP_SEGUNDO_APELLIDO
    ,ID_CREA
    ,CO_ESTADO_REGISTRADOR
    ,DE_MAIL
    ,FE_FECHA_ALTA   
    ,DE_DETALLE_OREC_CORTA   
    ,FE_FECHA_INICIO
    ,FE_FECHA_FIN
    ,NO_PRENOMBRES  
    ,NU_DOC_IDENTIDAD
    ,CO_CARGO_REGISTRADOR
    ,ID_DET_SOL_FIRMA
    ) 
    values (
    IDO_PLATAFORMA_EXPE.EDSE_CREG_CIVIL.nextval
    ,p_DE_OBSERVACION
    ,p_NU_CELULAR
    ,SYSDATE
    ,p_ID_TIPO_SOLICITUD_FIRMA   
    ,p_CO_OREC_REG_CIVIL
    ,p_CO_ESTADO_FORMATO
    ,p_DE_UBIGEO_DETALLE
    ,p_AP_PRIMER_APELLIDO
    ,p_CO_CONDICION
    ,p_FE_FECHA_BAJA
    ,p_CO_TIPO_DOC_IDENTIDAD
    ,p_CO_MOTIVO_ACTUALIZA
    ,p_FE_FECHA_ACTUALIZACION
    ,p_AP_SEGUNDO_APELLIDO
    ,p_ID_CREA
    ,p_CO_ESTADO_REGISTRADOR
    ,p_DE_MAIL
    ,p_FE_FECHA_ALTA   
    ,p_DE_DETALLE_OREC_CORTA  
    ,p_FE_FECHA_INICIO
    ,p_FE_FECHA_FIN
    ,p_NO_PRENOMBRES  
    ,p_NU_DOC_IDENTIDAD
    ,p_CO_CARGO_REGISTRADOR
    ,p_ID_DET_SOL_FIRMA
    );
end;