package mx.uach.proy_compiladores;

/**
 * Todo los operandos y simbolos que componen al Token
 * 
 * @author Luis Alberto Hernández Domínguez
 * @version 1.0
 * @since 16/03/2015
 */

public class Token{
    public class tipo_char{
        public static final int EOF = '.';
        public static final int FIN_PROD = ';';
        public static final int CONCATENACION = '&';
        public static final int ALTERNACION = '|';
        public static final int CERR_0_N_DER = '}';
        public static final int CERR_0_N_IZQ = '{';
        public static final int CERR_0_1_DER = ']';
        public static final int CERR_0_1_IZQ = '[';
        public static final int PAR_DER = ')';
        public static final int PAR_IZQ = '(';
        public static final int DEFINICION = 700;
        public static final int TERMINAL = 800;
        public static final int VARIABLE = 900;
    }
    private int type;
    private String data;
    Token(int type, String data){
        this.type = type;
        this.data = data;
    }
    public int getType(){
        return this.type;
    }
    public void setType(int type){
        this.type = type;
    }
    public String getData(){
        return this.data;
    }
    public void setData(String data){
        this.data = data;
    }
    
    @Override
    public String toString(){
        return
            String.format("Tipo: %s y Dato: %s",this.type, this.data);
    }
}
