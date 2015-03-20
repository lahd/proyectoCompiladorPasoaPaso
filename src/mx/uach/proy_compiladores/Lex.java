package mx.uach.proy_compiladores;

import java.util.ArrayList;

/**
 * Se encarga de la coherencia y validacion de caracteres
 *
 * Se encarga de que tenga los tipos de caracter declarados en el token
 *
 * @author Luis Alberto Hernández Domínguez
 * @author Julio Alberto Alvarez Navarrete
 * @version 1.0
 * @since 16/03/2015
 */
public class Lex{
    /**
     * Transforma una cadena de texto en tokens.
     *
     * @param input Cadena de texto.
     * @return Lista de tokens.
     */
    public static ArrayList<Token> getTokens(String input){
        // esto es para que no tome en cuenta los espacios
        input = input.replaceAll("\\s", "");
        // Lista de tokens
        ArrayList<Token> tokens = new ArrayList<>();
        // Esto es para que analice caracter por carater.
        for (int i = 0; i < input.length(); i++)
        {
            // Si el caracter es diferente a cualquiera de estos
            if( input.charAt(i) != Token.tipo_char.EOF
             && input.charAt(i) != Token.tipo_char.FIN_PROD
             && input.charAt(i) != Token.tipo_char.CONCATENACION
             && input.charAt(i) != Token.tipo_char.ALTERNACION
             && input.charAt(i) != Token.tipo_char.CERR_0_N_DER
             && input.charAt(i) != Token.tipo_char.CERR_0_N_IZQ
             && input.charAt(i) != Token.tipo_char.CERR_0_1_DER
             && input.charAt(i) != Token.tipo_char.CERR_0_1_IZQ
             && input.charAt(i) != Token.tipo_char.PAR_DER
             && input.charAt(i) != Token.tipo_char.PAR_IZQ)
            {
                /** Como la definicion se compone de mas de un caracter es 
                 * necesario tener una forma de juntarlos asi que aqui esta,
                 * si el primer caracter es ':' se le suma 1 para que avance 
                 * al siguiente caracter y de igual forma verifica, como no
                 * es un ciclo for, en lugar de sumarle 1, le sumamos 2 para
                 * que avance al tercer caracter y si es '=' se agrega la
                 * DEFINICION
                 */
                if (input.charAt(i) == ':'
                 && (i + 3) <= input.length()
                 && input.charAt(i + 1) == ':'
                 && input.charAt(i + 2) == '=')
                {
                    tokens.add(new Token(Token.tipo_char.DEFINICION, "::="));
                    i += 2;
                }else if
                /** como la terminal '+' y '-' no estan dentro de los operandos
                 * se ponen aqui entre '' para que quede como en el ejemplo
                 */
                 (input.charAt(i) == '\''
                      && input.indexOf('\'', i + 1) != -1)
                {
                    int ni = input.indexOf('\'', i + 1);
                    tokens.add
                    (   new Token
                        (   Token.tipo_char.TERMINAL,
                            input.substring
                            (   i,
                                ni + 1)
                        )
                    );
                    i = ni;
                }
                /** Si no es ni definicion ni terminal tiene que ser una 
                 * variable, y tal y como esta en el modo de entrada tiene
                 * que ir entre '<>'
                 */
                else if (input.charAt(i)=='<'&& input.indexOf('>', i + 1) != -1
                      && (i + 1) < input.length()&& 
                        Character.isLetter(input.charAt(i + 1)) == true)
                {
                    // Obtiene el index del proximo >.
                    int ni = input.indexOf('>', i + 1);

                    // Esto es para los simbolos <> y la variable.
                    String sim = input.substring(i, ni + 1);
                    // Valida que sean letras y numeros.
                    for (int j = 1; j < sim.length() - 1; j++)
                    {
                        // Si no son ni numeros ni letras
                        if (Character.isDigit(sim.charAt(j)) != true
                         && Character.isLetter(sim.charAt(j)) != true)
                        {
                            //lanzamos un error
                            throw new Error
                            (String.format("Error lexico en columna: %d", i));
                        }
                    }
                    // agreha el token en la lista
                    tokens.add
                    (new Token(Token.tipo_char.VARIABLE,sim));
                    i = ni;
                }else{ /**Si no es ninguno de esos, entonces es un error léxico
                 * debido a que no tiene caracteres válidos
                 */
                    throw new Error
                    (String.format("Error lexico en columna: %d", i));
                }
            }else{
                tokens.add
                (new Token(input.charAt(i), String.valueOf(input.charAt(i))));
            }
        } 
        return tokens;
    }
}
