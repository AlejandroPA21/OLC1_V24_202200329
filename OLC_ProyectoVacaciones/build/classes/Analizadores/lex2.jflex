
package Analizadores;

//importaciones
import java_cup.runtime.Symbol;
import excepciones.Errores;
import java.util.LinkedList;

%%

//codigo de usuario
%{
 public LinkedList<Errores>listaErrores = new LinkedList<>();
%}

%init{
    yyline = 1;
    yycolumn = 1;
    listaErrores = new LinkedList<>();
%init}

//caracteristicas de jflex
%cup
%class scanner
%public
%line
%char
%column
%full
//%debug
%ignorecase


MENOS = "-"
MAS = "+"
POR = "*"
MODULO = "%"
DIVIDIDO = "/"
IGUAL = "="
GUIONBAJO = "_"
MAYOR = ">"
MENOR = "<"
XOR = "^"
PARENTESIS1 = "("
PARENTESIS2 = ")"
LLAVE1 = "{"
LLAVE2 = "}"
PUNTOYCOMA = ";"
DOSPUNTOS = ":"
COMA = ","
CORCHETE1 = "["
CORCHETE2 = "]"
NOT = "!"
AND = "&&"
OR = "||"
VARR = "var"
CONSTR = "const"
DOUBLET = "double"
INTT = "int"
CHART = "char"
STRINGT = "string"
BOOLT = "bool"
VOID = "void"
VERDADERO = "true"
FALSO = "false"
IFR = "if"
ELSER = "else"
WHILER = "while"
DOR = "do"
FORR = "for"
BREAKR = "break"
CONTINUER = "continue"
PRINTR = "println"
MATCH = "match"
NEW = "new"
LIST = "list"
APPEND = "append"
PUNTO = "."
REMOVE = "remove"
STRUCT = "struct"
START = "START_WITH"
RETURN = "return"
ROUND = "round"
LENGTH = "length"
TOSTRING = "toString"
FIND = "find"
IDENTIFICADOR = [a-zA-Z][a-zA-Z0-9_]*
DECIMAL = [0-9]+\.[0-9]+
ENTERO = [0-9]+
ESCAPE_SEQUENCE = \\[nrt'\"\\]
CARACTER =  \'(\\.|[^\\'])\'
CADENA = \"(\\.|[^\\\"\n\r])*\"
COMENTARIO = "//"[^\n]*
COMENTARIO_MULTILINEA = \/\*[^*]*\*+([^/*][^*]*\*+)*\/

%%
<YYINITIAL> {MENOS} {return new Symbol(sym.MENOS,yyline,yycolumn, yytext());}
<YYINITIAL> {MAS}  {return new Symbol(sym.MAS,yyline,yycolumn, yytext());}
<YYINITIAL> {POR}  {return new Symbol(sym.POR,yyline,yycolumn, yytext());}
<YYINITIAL> {MODULO}  {return new Symbol(sym.MODULO,yyline,yycolumn, yytext());}
<YYINITIAL> {DIVIDIDO}  {return new Symbol(sym.DIVIDIDO,yyline,yycolumn, yytext());}
<YYINITIAL> {IGUAL}  {return new Symbol(sym.IGUAL,yyline,yycolumn, yytext());}
<YYINITIAL> {GUIONBAJO}  {return new Symbol(sym.GUIONBAJO,yyline,yycolumn, yytext());}
<YYINITIAL> {MAYOR}  {return new Symbol(sym.MAYOR,yyline,yycolumn, yytext());}
<YYINITIAL> {MENOR}  {return new Symbol(sym.MENOR,yyline,yycolumn, yytext());}
<YYINITIAL> {XOR}  {return new Symbol(sym.XOR,yyline,yycolumn, yytext());}
<YYINITIAL> {PARENTESIS1}  {return new Symbol(sym.PARENTESIS1,yyline,yycolumn, yytext());}
<YYINITIAL> {PARENTESIS2}  {return new Symbol(sym.PARENTESIS2,yyline,yycolumn, yytext());}
<YYINITIAL> {LLAVE1}  {return new Symbol(sym.LLAVE1,yyline,yycolumn, yytext());}
<YYINITIAL> {LLAVE2}  {return new Symbol(sym.LLAVE2,yyline,yycolumn, yytext());}
<YYINITIAL> {PUNTOYCOMA}  {return new Symbol(sym.PUNTOYCOMA,yyline,yycolumn, yytext());}
<YYINITIAL> {DOSPUNTOS}  {return new Symbol(sym.DOSPUNTOS,yyline,yycolumn, yytext());}
<YYINITIAL> {COMA}  {return new Symbol(sym.COMA,yyline,yycolumn, yytext());}
<YYINITIAL> {CORCHETE1}  {return new Symbol(sym.CORCHETE1,yyline,yycolumn, yytext());}
<YYINITIAL> {CORCHETE2}  {return new Symbol(sym.CORCHETE2,yyline,yycolumn, yytext());}
<YYINITIAL> {NOT}  {return new Symbol(sym.NOT,yyline,yycolumn, yytext());}
<YYINITIAL> {AND}  {return new Symbol(sym.AND,yyline,yycolumn, yytext());}
<YYINITIAL> {OR}  {return new Symbol(sym.OR,yyline,yycolumn, yytext());}
<YYINITIAL> {VARR} {return new Symbol(sym.VARR,yyline,yycolumn, yytext());}
<YYINITIAL> {CONSTR} {return new Symbol(sym.CONSTR,yyline,yycolumn, yytext());}
<YYINITIAL> {DOUBLET} {return new Symbol(sym.DOUBLET,yyline,yycolumn, yytext());}
<YYINITIAL> {INTT} {return new Symbol(sym.INTT,yyline,yycolumn, yytext());}
<YYINITIAL> {CHART} {return new Symbol(sym.CHART,yyline,yycolumn, yytext());}
<YYINITIAL> {STRINGT} {return new Symbol(sym.STRINGT,yyline,yycolumn, yytext());}
<YYINITIAL> {BOOLT} {return new Symbol(sym.BOOLT,yyline,yycolumn, yytext());}
<YYINITIAL> {VERDADERO} {return new Symbol(sym.VERDADERO,yyline,yycolumn, yytext());}
<YYINITIAL> {FALSO} {return new Symbol(sym.FALSO,yyline,yycolumn, yytext());}
<YYINITIAL> {IFR} {return new Symbol(sym.IFR,yyline,yycolumn, yytext());}
<YYINITIAL> {ELSER} {return new Symbol(sym.ELSER,yyline,yycolumn, yytext());}
<YYINITIAL> {WHILER} {return new Symbol(sym.WHILER,yyline,yycolumn, yytext());}
<YYINITIAL> {DOR} {return new Symbol(sym.DOR,yyline,yycolumn, yytext());}
<YYINITIAL> {FORR} {return new Symbol(sym.FORR,yyline,yycolumn, yytext());}
<YYINITIAL> {BREAKR} {return new Symbol(sym.BREAKR,yyline,yycolumn, yytext());}
<YYINITIAL> {CONTINUER} {return new Symbol(sym.CONTINUER,yyline,yycolumn, yytext());}
<YYINITIAL> {PRINTR} {return new Symbol(sym.PRINTR,yyline,yycolumn, yytext());}
<YYINITIAL> {MATCH} {return new Symbol(sym.MATCH,yyline,yycolumn, yytext());}
<YYINITIAL> {NEW} {return new Symbol(sym.NEW,yyline,yycolumn, yytext());}
<YYINITIAL> {LIST} {return new Symbol(sym.LIST,yyline,yycolumn, yytext());}
<YYINITIAL> {APPEND} {return new Symbol(sym.APPEND,yyline,yycolumn, yytext());}
<YYINITIAL> {PUNTO} {return new Symbol(sym.PUNTO,yyline,yycolumn, yytext());}
<YYINITIAL> {REMOVE} {return new Symbol(sym.REMOVE,yyline,yycolumn, yytext());}
<YYINITIAL> {VOID} {return new Symbol(sym.VOID,yyline,yycolumn, yytext());}
<YYINITIAL> {STRUCT} {return new Symbol(sym.STRUCT,yyline,yycolumn, yytext());}
<YYINITIAL> {START} {return new Symbol(sym.START,yyline,yycolumn, yytext());}
<YYINITIAL> {RETURN} {return new Symbol(sym.RETURN,yyline,yycolumn, yytext());}
<YYINITIAL> {ROUND} {return new Symbol(sym.ROUND,yyline,yycolumn, yytext());}
<YYINITIAL> {LENGTH} {return new Symbol(sym.LENGTH,yyline,yycolumn, yytext());}
<YYINITIAL> {TOSTRING} {return new Symbol(sym.TOSTRING,yyline,yycolumn, yytext());}
<YYINITIAL> {FIND} {return new Symbol(sym.FIND,yyline,yycolumn, yytext());}
<YYINITIAL> [ \t\n\r\f]+ {}
<YYINITIAL> {IDENTIFICADOR} {return new Symbol(sym.IDENTIFICADOR,yyline,yycolumn, yytext());}
<YYINITIAL> {DECIMAL} {return new Symbol(sym.DECIMAL,yyline,yycolumn, yytext());}
<YYINITIAL> {ENTERO} {return new Symbol(sym.ENTERO,yyline,yycolumn, yytext());}
<YYINITIAL> {CARACTER} {return new Symbol(sym.CARACTER,yyline,yycolumn, yytext().substring(1, yytext().length() - 1));}
<YYINITIAL> {CADENA} {return new Symbol(sym.CADENA,yyline,yycolumn,  yytext().substring(1, yytext().length() - 1));}
<YYINITIAL> {COMENTARIO} {}
<YYINITIAL> {COMENTARIO_MULTILINEA} {}

<YYINITIAL> . {listaErrores.add(new Errores("Lexico", "Token"+yytext()+"no pertenece a el lenguaje", yyline, yycolumn));}

//System.out.println("Error léxico: "+yytext() + " en línea: " + yyline + " columna: " + yycolumn);