package mx.uach.proy_compiladores;
import java.io.*;
/**
 * Verifica el arvhivo de texto
 * 
 * <ul>
 * <li>>prog -> conjprod;</li>
 * <li>>conjprod -> conjprod | prod </li>
 * <li>>prod -> variable DEF Expr;</li>
 * <li>>expr -> Expr ALT term | term</li>
 * <li>>term -> term & factor  | factor</li>
 * <li>>factor -> {Expr} | [Expr] | Primario</li>
 * <li>>Primario -> (Expr) | variable | terminal</li>
 * </ul>
 * 
 * @author Luis Alberto Hernández Domínguez
 * @version 1.0
 * @since 16/03/2015
 */

public class Analizador{
    /**
     * Verifica el archivo
     * @param args the command line arguments
     */
    public static void main(String[] args){
        Parser parser = new Parser();
        parser.parse(Lex.getTokens(getInputFrom("Archivo.txt")));
        System.out.println(parser.salida());
    }

    /**
     * Obtiene la cadena de caracteres de un archivo.
     *
     * @param file Nombre del archivo.
     * @return Cadena de caracteres.
     */
    private static String getInputFrom(String file){
        String texto = "";
        FileReader Archivo = null;
        String line = "";
        try
        {
            Archivo = new FileReader(file);
            BufferedReader buff = new BufferedReader(Archivo);
            while ((line = buff.readLine()) != null){
                texto = String.format("%s%s", texto, line);
            }
        }
        catch (FileNotFoundException ex){
            throw new RuntimeException("Archivo no encontrado!");
        }catch (IOException ex){
            throw new RuntimeException("Error de entrada/saladia!");
        }
        finally
        {
            if (Archivo != null){
                try{
                    Archivo.close();
                }catch (IOException ex){
                    throw new RuntimeException("Error al cerrar el archivo!");
                }
            }
        }
        return texto;
    }
}

