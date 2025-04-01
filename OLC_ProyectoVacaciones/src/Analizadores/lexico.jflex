
package Analizadores;

import java_cup.runtime.Symbol;

//import java.util.LinkedList;

%%


//codigo de usuario
%{

%}

%init{ 
    yyline = 1; 
    yycolumn=1;
%init}

%cup 
%class MiAnalizadorLexico
%public 
%line 
%column
%char 
%full 
%ignorecase



IDENTIFICADOR = [a-zA-Z][a-zA-Z0-9_]*
ENTERO = [0-9]+
DOUBLER = [0-9]+\.[0-9]+
CARACTER =  \'(\\.|[^\\'])\'
CADENA = \"[^\n\r\"]*\"
COMENTARIO = "!"[^\n]*
COMENTARIO_MULTILINEA = \/\*[^*]*\*+([^/*][^*]*\*+)*\/

%%

"-"  {return new Symbol(sym.MENOS,yyline,yycolumn, yytext());}
"+"  {return new Symbol(sym.MAS,yyline,yycolumn, yytext());}
"*"  {return new Symbol(sym.ASTERISCO,yyline,yycolumn, yytext());}
"/"  {return new Symbol(sym.DIV,yyline,yycolumn, yytext());}
"%"  {return new Symbol(sym.PORCENTAJE,yyline,yycolumn, yytext());}
"="  {return new Symbol(sym.IGUAL,yyline,yycolumn, yytext());}
">"  {return new Symbol(sym.MAYORQUE,yyline,yycolumn, yytext());}
"<"  {return new Symbol(sym.MENORQUE,yyline,yycolumn, yytext());}
"^" {return new Symbol(sym.XOR,yyline,yycolumn, yytext());}
"_" {return new Symbol(sym.GUIONBAJO,yyline,yycolumn, yytext());}
"("  {return new Symbol(sym.PARENTESISIZQ,yyline,yycolumn, yytext());}
")"  {return new Symbol(sym.PARENTESISDER,yyline,yycolumn, yytext());}
"{"  {return new Symbol(sym.LLAVEIZQ,yyline,yycolumn, yytext());}
"}"  {return new Symbol(sym.LLAVEDER,yyline,yycolumn, yytext());}
";"  {return new Symbol(sym.PUNTOYCOMA,yyline,yycolumn, yytext());}
":"  {return new Symbol(sym.DOSPUNTOS,yyline,yycolumn, yytext());}
","  {return new Symbol(sym.COMA,yyline,yycolumn, yytext());}
"["  {return new Symbol(sym.CORCHETEIZQ,yyline,yycolumn, yytext());}
"]"  {return new Symbol(sym.CORCHETEDER,yyline,yycolumn, yytext());}
"!"  {return new Symbol(sym.NOT,yyline,yycolumn, yytext());}
"&"  {return new Symbol(sym.ANDS,yyline,yycolumn, yytext());}
"|"  {return new Symbol(sym.ORS,yyline,yycolumn, yytext());}
"double" {return new Symbol(sym.DOUBLET,yyline,yycolumn, yytext());}
"int" {return new Symbol(sym.INTT,yyline,yycolumn, yytext());}
"String" {return new Symbol(sym.STRINGT,yyline,yycolumn, yytext());}
"bool" {return new Symbol(sym.BOOLT,yyline,yycolumn, yytext());}
"char" {return new Symbol(sym.CHART,yyline,yycolumn, yytext());}
"true" {return new Symbol(sym.TRUER,yyline,yycolumn, yytext());}
"false" {return new Symbol(sym.FALSER,yyline,yycolumn, yytext());}
"var" {return new Symbol(sym.VARR,yyline,yycolumn, yytext());}
"const" {return new Symbol(sym.CONSTR,yyline,yycolumn, yytext());}
"if" {return new Symbol(sym.IFR,yyline,yycolumn, yytext());}
"else" {return new Symbol(sym.ELSER,yyline,yycolumn, yytext());}
"match" {return new Symbol(sym.MATCH,yyline,yycolumn, yytext());}
"while" {return new Symbol(sym.WHILER,yyline,yycolumn, yytext());}
"for" {return new Symbol(sym.FORR,yyline,yycolumn, yytext());}
"do" {return new Symbol(sym.DOR,yyline,yycolumn, yytext());}
"break" {return new Symbol(sym.BREAKR,yyline,yycolumn, yytext());}
"continue" {return new Symbol(sym.CONTINUER,yyline,yycolumn, yytext());}
"print" {return new Symbol(sym.PRINT,yyline,yycolumn, yytext());}
// "return" {return new Symbol(sym.RETURN,yyline,yycolumn, yytext());}

[ \t\n\r\f]+ {}
{IDENTIFICADOR} {return new Symbol(sym.IDENTIFICADOR,yyline,yycolumn, yytext());}
{DOUBLER} {return new Symbol(sym.DOUBLE,yyline,yycolumn, yytext());}
{ENTERO} {return new Symbol(sym.ENTERO,yyline,yycolumn, yytext());}
{CARACTER} {return new Symbol(sym.CARACTER,yyline,yycolumn, yytext().substring(1, yytext().length() - 1));}
{CADENA} {return new Symbol(sym.CADENA,yyline,yycolumn,  yytext().substring(1, yytext().length() - 1));}
{COMENTARIO} {}
{COMENTARIO_MULTILINEA} {}
. {System.out.println("Error lexico: "+yytext(),yyline,yycolumn);}









