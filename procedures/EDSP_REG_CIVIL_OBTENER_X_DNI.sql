create or replace PROCEDURE EDSP_REG_CIVIL_OBTENER_X_DNI (
P_VDNI VARCHAR2,
P_CRRESULT OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN P_CRRESULT FOR
    SELECT RCV.ID_REG_CIVIL ,
    RCV.ID_TIPO_SOLICITUD_FIRMA,
    RCV.CO_TIPO_DOC_IDENTIDAD,
    RCV.NU_DOC_IDENTIDAD,
    RCV.AP_PRIMER_APELLIDO,
    RCV.AP_SEGUNDO_APELLIDO,
    RCV.NO_PRENOMBRES, 
    RCV.FE_CAMBIO_ESTADO,
    RCV.CO_OREC_REG_CIVIL,
    RCV.DE_DETALLE_OREC_CORTA,
    RCV.CO_ESTADO_REGISTRADOR,
    RCV.NU_CELULAR,
    RCV.DE_MAIL,
    ORE.NO_DEPARTAMENTO,
    ORE.NO_PROVINCIA,
    ORE.NO_DISTRITO,
    ORE.DE_UBIGEO_DETALLE
    FROM IDO_PLATAFORMA_EXPE.EDTM_REG_CIVIL RCV
    INNER JOIN IDORRCC.RCVM_OREC ORE ON ORE.CO_OREC = RCV.CO_OREC_REG_CIVIL 
    WHERE NU_DOC_IDENTIDAD = P_VDNI;
END;