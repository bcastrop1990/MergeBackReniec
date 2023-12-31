create or replace PROCEDURE                       EDSP_GESTION_SOL_CONSULTAR
(
    P_VNRO_SOLICITUD VARCHAR2,
    P_CESTADO CHAR,
    P_CTIPO_REG CHAR,
    P_VFECHA_INI VARCHAR2,
    P_VFECHA_FIN VARCHAR2,
    P_CCO_DEP CHAR,
    P_CCO_PROV CHAR,
    P_CCO_DIST CHAR,
    P_CCO_CENT_POBL CHAR,
    P_CCO_OREC CHAR,
    P_CCO_ANALISTA CHAR,
    P_NPAGE NUMBER,
    P_NSIZE NUMBER,
    P_CRESULT OUT SYS_REFCURSOR,
    P_CRTOTAL OUT SYS_REFCURSOR 
)
AS
 SQL_SELECT_COUNT VARCHAR2(8000) := 'SELECT COUNT(1) TOTAL   
    FROM IDO_PLATAFORMA_EXPE.EDTC_SOLICITUD SOL
    INNER JOIN IDO_PLATAFORMA_EXPE.EDTM_SOL_TIPO_REGISTRO STR ON SOL.ID_TIPO_REGISTRO = STR.ID_TIPO_REGISTRO 
    INNER JOIN IDO_PLATAFORMA_EXPE.EDTM_SOL_ESTADO SES ON SOL.CO_ESTADO_SOLICITUD = SES.ID_SOL_ESTADO
    LEFT JOIN IDO_PLATAFORMA_EXPE.EDTM_ANALISTA ANL ON SOL.CO_ANALISTA_ASIGNADO = ANL.CO_ANALISTA_ASIGNADO
    WHERE SOL.CO_ESTADO = ''1'' ';

 SQL_QUERY VARCHAR2(8000) := 'SELECT 
    ROW_NUMBER() OVER(ORDER BY SOL.NU_SOLICITUD_NUMERO) ROW_ID,
    SOL.NU_SOLICITUD_NUMERO,
    SOL.FE_FECHA_SOLICITUD,    
    STR.DE_DESCRIPCION AS DE_DESCRIPCION_TIPO_REG,
    SOL.DE_DETALLE_OREC_LARGA,
    SOL.FE_FECHA_RECEPCION,
    SOL.FE_FECHA_ASIGNACION,
    SOL.FE_FECHA_ATENCION,
    SES.DE_DESCRIPCION AS DE_DESCRIPCION_SOL_ESTADO,
    ANL.AP_PRIMER_APELLIDO AS AP_PRIMER_APELLIDO_ANALISTA,  
    ANL.AP_SEGUNDO_APELLIDO AS AP_SEGUNDO_APELLIDO_ANALISTA, 
    ANL.NO_PRENOMBRES AS NO_PRENOMBRES_ANALISTA
    FROM IDO_PLATAFORMA_EXPE.EDTC_SOLICITUD SOL
    INNER JOIN IDO_PLATAFORMA_EXPE.EDTM_SOL_TIPO_REGISTRO STR ON SOL.ID_TIPO_REGISTRO = STR.ID_TIPO_REGISTRO 
    INNER JOIN IDO_PLATAFORMA_EXPE.EDTM_SOL_ESTADO SES ON SOL.CO_ESTADO_SOLICITUD = SES.ID_SOL_ESTADO
    LEFT JOIN IDO_PLATAFORMA_EXPE.EDTM_ANALISTA ANL ON SOL.CO_ANALISTA_ASIGNADO = ANL.CO_ANALISTA_ASIGNADO
    WHERE SOL.CO_ESTADO = ''1'' ';

 SQL_WHERE VARCHAR2(8000) := '';   

BEGIN

   IF (P_VNRO_SOLICITUD IS NOT NULL) THEN
    SQL_WHERE := SQL_WHERE || ' AND SOL.NU_SOLICITUD_NUMERO = ''' || TRIM(P_VNRO_SOLICITUD) || '''';
   END IF;

  IF (P_CESTADO IS NOT NULL) THEN    
    SQL_WHERE := SQL_WHERE || ' AND SOL.CO_ESTADO_SOLICITUD = '''|| TRIM(P_CESTADO) || '''' ;
    
    IF (P_CESTADO = '1') THEN
        IF (P_VFECHA_INI IS NOT NULL) THEN
            SQL_WHERE := SQL_WHERE || ' AND TO_CHAR(SOL.FE_FECHA_SOLICITUD,''YYYY-MM-DD'') >= ''' || TRIM(P_VFECHA_INI) || ''''; 
        END IF;
        IF (P_VFECHA_FIN IS NOT NULL) THEN
            SQL_WHERE := SQL_WHERE || ' AND TO_CHAR(SOL.FE_FECHA_SOLICITUD,''YYYY-MM-DD'') <= ''' || TRIM(P_VFECHA_FIN) || ''''; 
        END IF;
    ELSIF (P_CESTADO = '2') THEN   
        IF (P_VFECHA_INI IS NOT NULL) THEN
            SQL_WHERE := SQL_WHERE || ' AND TO_CHAR(SOL.FE_FECHA_RECEPCION,''YYYY-MM-DD'') >= ''' || TRIM(P_VFECHA_INI) || ''''; 
        END IF;
        IF (P_VFECHA_FIN IS NOT NULL) THEN
            SQL_WHERE := SQL_WHERE || ' AND TO_CHAR(SOL.FE_FECHA_RECEPCION,''YYYY-MM-DD'') <= ''' || TRIM(P_VFECHA_FIN) || ''''; 
        END IF;
     ELSIF (P_CESTADO = '3') THEN       
        IF (P_VFECHA_INI IS NOT NULL) THEN
            SQL_WHERE := SQL_WHERE || ' AND TO_CHAR(SOL.FE_FECHA_ASIGNACION,''YYYY-MM-DD'') >= '''  || TRIM(P_VFECHA_INI) || ''''; 
        END IF;
        IF (P_VFECHA_FIN IS NOT NULL) THEN
            SQL_WHERE := SQL_WHERE || ' AND TO_CHAR(SOL.FE_FECHA_ASIGNACION,''YYYY-MM-DD'') <= ''' || TRIM(P_VFECHA_FIN) || '''';
        END IF;
    ELSIF (P_CESTADO = '4') THEN  
        IF (P_VFECHA_INI IS NOT NULL) THEN
            SQL_WHERE := SQL_WHERE || ' AND TO_CHAR(SOL.FE_FECHA_ATENCION,''YYYY-MM-DD'') >= ''' || TRIM(P_VFECHA_INI) || '''';  
        END IF;
        IF (P_VFECHA_FIN IS NOT NULL) THEN
            SQL_WHERE := SQL_WHERE || ' AND TO_CHAR(SOL.FE_FECHA_ATENCION,''YYYY-MM-DD'') <= ''' || TRIM(P_VFECHA_FIN) || '''';
        END IF;
    END IF;  
  ELSE
    IF (P_VFECHA_INI IS NOT NULL) THEN
        SQL_WHERE := SQL_WHERE || ' AND TO_CHAR(SOL.FE_FECHA_SOLICITUD,''YYYY-MM-DD'') >= ''' || TRIM(P_VFECHA_INI) || '''';  
    END IF;
    IF (P_VFECHA_FIN IS NOT NULL) THEN
        SQL_WHERE := SQL_WHERE || ' AND TO_CHAR(SOL.FE_FECHA_SOLICITUD,''YYYY-MM-DD'') <= ''' || TRIM(P_VFECHA_FIN) || ''''; 
    END IF;
  END IF;

  IF (P_CTIPO_REG IS NOT NULL) THEN
    SQL_WHERE := SQL_WHERE || ' AND SOL.ID_TIPO_REGISTRO = ''' || TRIM(P_CTIPO_REG) || '''';
  END IF;

  IF (P_CCO_DEP IS NOT NULL) THEN
    SQL_WHERE := SQL_WHERE || ' AND SOL.CO_DEPARTAMENTO_OREC = ''' || TRIM(P_CCO_DEP) || '''';
  END IF;

  IF (P_CCO_PROV IS NOT NULL) THEN
    SQL_WHERE := SQL_WHERE || ' AND SOL.CO_PROVINCIA_OREC = ''' || TRIM(P_CCO_PROV) || '''';
  END IF;

  IF (P_CCO_DIST IS NOT NULL) THEN
    SQL_WHERE := SQL_WHERE || ' AND SOL.CO_DISTRITO_OREC = ''' || TRIM(P_CCO_DIST) || '''';
  END IF;

  IF (P_CCO_CENT_POBL IS NOT NULL) THEN
    SQL_WHERE := SQL_WHERE || ' AND SOL.CO_CP_OREC = ''' || TRIM(P_CCO_CENT_POBL) || '''';
  END IF;

  IF (P_CCO_OREC IS NOT NULL) THEN
    SQL_WHERE := SQL_WHERE || ' AND SOL.CO_OREC_SOLICITUD = ''' || TRIM(P_CCO_OREC) || '''';
  END IF;

  IF (P_CCO_ANALISTA IS NOT NULL) THEN
    SQL_WHERE := SQL_WHERE || ' AND SOL.CO_ANALISTA_ASIGNADO = ''' || TRIM(P_CCO_ANALISTA) || '''';
  END IF; 

  SQL_QUERY := 'SELECT * FROM ( ' || SQL_QUERY || SQL_WHERE || ' )TB WHERE TB.ROW_ID > ' || P_NPAGE*P_NSIZE  || ' AND TB.ROW_ID <= ' || (P_NPAGE+1)*P_NSIZE || '';
  SQL_SELECT_COUNT := SQL_SELECT_COUNT || SQL_WHERE; 
  
  OPEN P_CRESULT FOR SQL_QUERY;   
  OPEN P_CRTOTAL FOR SQL_SELECT_COUNT;
END;