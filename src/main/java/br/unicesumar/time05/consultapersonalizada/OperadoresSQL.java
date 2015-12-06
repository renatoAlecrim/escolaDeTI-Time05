package br.unicesumar.time05.consultapersonalizada;

public final class OperadoresSQL {
    
    public static final String SEPARADOR_CAMPOS_CONSULTA = "&";
    
    public static final String SELECT = " SELECT ";
    public static final String FROM = " FROM ";
    public static final String WHERE = " WHERE ";
    public static final String AND = " AND ";
    public static final String OR = " OR ";
    public static final String ILIKE = " ILIKE ";
    public static final String IGUAL = " = ";
    public static final String LIMIT = " LIMIT ";
    public static final String OFFSET = " OFFSET ";
    public static final String ORDER_BY = " ORDER BY ";
    public static final String DESC = " DESC ";    
    public static final String GROUP_BY = " GROUP BY ";
        
    //Agregação
    public static final String SUM = " SUM ";
    public static final String COUNT = " COUNT ";
    public static final String AVG = " AVG ";
    public static final String MAX = " MAX ";
    public static final String MIN = " MIN ";
           
    public static final String PARAMETRO_PARA_LIKE = ":palavrachavelike";
    public static final String PARAMETRO_PARA_IGUAL = ":palavrachaveigual";
    public static final String NOME_PARAMETRO_PARA_LIKE = "palavrachavelike";
    public static final String NOME_PARAMETRO_PARA_IGUAL = "palavrachaveigual";
}
