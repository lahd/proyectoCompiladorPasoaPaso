package mx.uach.proy_compiladores;

import java.util.ArrayList;

/**
 * Valida la gramatica
 * 
 * <ul>
 * <li>>prog -> conj;</li>
 * <li>>conj -> conj | prod </li>
 * <li>>prod -> variable DEF Expr;</li>
 * <li>>expr -> Expr ALT term | term</li>
 * <li>>term -> term & factor  | factor</li>
 * <li>>factor -> {Expr} | [Expr] | Primario</li>
 * <li>>Primario -> (Expr) | variable | terminal</li>
 * <li>>num -> [0 - 9]</li>
 * <li>>ID -> [a - z]</li>
 * </ul>
 * 
 * @author Luis Alberto Hernández Domínguez
 * @author Julio Alberto Alvarez Navarrete
 * @version 1.0
 * @since 16/03/2015
 */
public class Parser
{
    private ArrayList<Token> tokens;
    private int next = 0;
    private Token tokenActual;
    private String output = "";

    /**
     * Obtiene la salida del analizador.
     */
    public String salida(){
        return output;
    }

    /**
     * Inicia el analisis.
     * @param tokens para verificar.
     */
    
    public void parse(ArrayList<Token> tokens){
        this.tokens = tokens;
        tokenActual = tokens.get(next++);
        prog();
    }

    private void prog(){
        conj();
        if (tokenActual.getType() != Token.tipo_char.EOF){
            throw new Error(String.format
                ("Error de sintaxis. Se esperaba: %s",
                    tokenActual.getData(), (char) Token.tipo_char.EOF));
        }
    }

    private void conj(){
        prod();
        while (tokenActual.getType() == Token.tipo_char.FIN_PROD){
            output = String.format("%s\n", output);
            tokenActual = tokens.get(next++);
            if (tokenActual.getType() == Token.tipo_char.EOF){
                break;
            }
            prod();
        }
    }

    private void prod(){
        if (tokenActual.getType() == Token.tipo_char.VARIABLE){
            output = String.format("%s%s", output, tokenActual.getData());
        }else{
            throw new Error
            ("Error de sintaxis: "
                    + "Toda produccion debe de iniciar con una variable.");
        }
        tokenActual = tokens.get(next++);
        if (tokenActual.getType() == Token.tipo_char.DEFINICION){
            tokenActual = tokens.get(next++);
            expr();
            output = String.format("%s%s", output, "::=");
        }else{
            throw new Error("Error de sintaxis: Se esperaba: ::=");
        }
    }

    private void expr(){
        term();
        while (tokenActual.getType() == Token.tipo_char.ALTERNACION){
            tokenActual = tokens.get(next++);
            expr();
            output = String.format(
                    "%s%s", output, (char) Token.tipo_char.ALTERNACION);
        }
    }
    
    private void term(){
        factor();
        tokenActual = tokens.get(next++);
        while (tokenActual.getType() == Token.tipo_char.CONCATENACION){
        output = String.format("%s%s", output, (char) 
                Token.tipo_char.CONCATENACION);
        tokenActual = tokens.get(next++);
        term();
        }
    }
    private void factor(){
        if (tokenActual.getType() == Token.tipo_char.CERR_0_N_IZQ){
            tokenActual = tokens.get(next++);
            expr();
            if (tokenActual.getType() == Token.tipo_char.CERR_0_N_DER){
                output = String.format("%s%s", output, tokenActual.getData());
            }else{
                throw new Error
                (String.format
                  ("Error de sintaxis. Se tiene: %s ~~~ Se esperaba: %s",
                   tokenActual.getData(), (char) Token.tipo_char.CERR_0_N_DER));
            }
        }
        else if (tokenActual.getType() == Token.tipo_char.CERR_0_1_IZQ)
        {tokenActual = tokens.get(next++);
            expr();
            if (tokenActual.getType() == Token.tipo_char.CERR_0_1_DER){
                output = String.format("%s%s", output, tokenActual.getData());
            }else{
                throw new Error
                (String.format
                  ("Error de sintaxis. Se tiene: %s ~~~ Se esperaba: %s",
                   tokenActual.getData(), (char) Token.tipo_char.CERR_0_1_DER));
            }
        }else{
            primario();
        }
    }
    private void primario(){
        if (tokenActual.getType() == Token.tipo_char.PAR_IZQ){
            tokenActual = tokens.get(next++);
            expr();
            if (tokenActual.getType() == Token.tipo_char.PAR_DER){
                output = String.format("%s%s", output, tokenActual.getData());
            }else{throw new Error(String.format("Error de sintaxis. Se tiene: "
                        + "%s ~~~ Se esperaba: %s",tokenActual.getData(), 
                        (char) Token.tipo_char.PAR_DER));
            }
        }
        else if (tokenActual.getType() == Token.tipo_char.VARIABLE
              || tokenActual.getType() == Token.tipo_char.TERMINAL)
        {output = String.format("%s%s", output, tokenActual.getData());
        }else{throw new Error(String.format(
                "Error de sintaxis. Se tiene: %s ~~~ " + "Se esperaba: '(' || "
                        + "'VAR' || 'TERML'.",tokenActual.getData()));
        }
    }
}