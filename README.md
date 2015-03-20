# proyectoCompiladorPasoaPaso
Se eleboro un compilador de solo un paso con las siguentes especificaciones otorgadas por el profesor

construir un CSP para un lenguaje de producciones gramaticales, 
el alfabeto se compone por los siguientes simbolos:

Variable: Una variable tiene la estructura <Nombre>
donde nombre es una secuencia de uno o mas caracteres tomados del 
conjunto de letral y digitos. una variable debe iniciar con letra

Operadores
a) Concatenacion: caracter &
b)Alternacion: caracter '|'
c)Cerradura cero o mas: se representa con los caracteres '{' y '}' ejemplo {<adjunto>}
d)Cerradura cero o mas: se representa por los caracteres '[' y ']' 
Ejemplo: [+|-} significa que se puede tomar el caracter + o - de manera opcional.
e)Definicion: se representa por la secuencia de caracteres "::="
f) fin de produccion: caracter ";"
g) fin de archivo: caracter '.'

Terminal es cualquier secuencia de caracteres entre apostrofes.
La sintaxis  y  la semantica del lenguaje se representan con  la siguente G.L.C.
prog -> conjuntoProds
conjprods -> conj prods | prod
prod -> variable DEF(definicion) expr;
expr -> expr ALT(alternacion) term | term
term -> term & factor | factor
factor -> {expr} |[expr]|primario
primario -> (expr) |variable|termina|

entra
<entero>::={{['+'|'-'] &<variable>&(['+'|'-'])&{variable2>}} & {['+'|'-'] & <variables>};


Salida
<entero> '+''-'|]&<variable1>}&'+''-'|]&<variable2>}} & '+''-'|] &<variable3>}::=

entrada por archivos
codigo funcional
java doc
estandar codificacion
tecnica de P.R.D
entregar a la cuneta lramirez@uach.mx


