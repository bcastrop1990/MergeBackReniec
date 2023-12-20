create or replace procedure  EDSP_REGCIV_ACT_ACTUALIZA (
p_CO_ESTADO_REGISTRADOR VARCHAR2
,p_FE_CAMBIO_ESTADO DATE 
,p_CO_TIPO_DOC_IDENTIDAD VARCHAR2
,p_NU_DOC_IDENTIDAD VARCHAR2 
,p_ID_ACTUALIZA VARCHAR2
,p_CO_OREC_REG_CIVIL VARCHAR2
,p_DE_DETALLE_OREC_CORTA VARCHAR2
,p_NU_CELULAR VARCHAR2
,p_DE_MAIL VARCHAR2
) AS
    begin
    update EDTM_REG_CIVIL
    SET        
    CO_ESTADO_REGISTRADOR = p_CO_ESTADO_REGISTRADOR   
    ,FE_CAMBIO_ESTADO  = p_FE_CAMBIO_ESTADO 
    ,ID_ACTUALIZA = p_ID_ACTUALIZA
    ,FE_ACTUALIZA = SYSDATE
    ,CO_OREC_REG_CIVIL = p_CO_OREC_REG_CIVIL
    ,DE_DETALLE_OREC_CORTA = p_DE_DETALLE_OREC_CORTA
    ,NU_CELULAR = p_NU_CELULAR
    ,DE_MAIL = p_DE_MAIL
    WHERE CO_TIPO_DOC_IDENTIDAD = p_CO_TIPO_DOC_IDENTIDAD
    AND NU_DOC_IDENTIDAD = p_NU_DOC_IDENTIDAD;    

end;