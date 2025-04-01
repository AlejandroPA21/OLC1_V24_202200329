/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author relda
 */
public class Return extends Instruccion{
    public Instruccion expresion;
    public tablaSimbolos tablaExpresion;
    
    public Return( int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.tablaExpresion = new tablaSimbolos();
    }

    public Return(Instruccion expresion,  int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.expresion = expresion;
        this.tablaExpresion = null;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        if(this.expresion != null && tablaExpresion == null){
            var resultado = this.expresion.interpretar(arbol, tabla);
            
            if(resultado instanceof Errores){
                return resultado;
            }
            
            if(this.expresion instanceof Llamada call){
                var tipo2 = call.tipo.getTipo();
                this.tipo.setTipo(tipo2);
                return resultado;
            }
            
            this.tipo.setTipo(this.getTipoDato(resultado));
            return resultado;
            
        }
        
        if(this.expresion != null && tablaExpresion != null){
            var resultado = this.expresion.interpretar(arbol, this.tablaExpresion);
            
            if(resultado instanceof Errores){
                return resultado;
            }
            
            if(this.expresion instanceof Llamada llamada){
                var tipo2 = llamada.tipo.getTipo();
                this.tipo.setTipo(tipo2);
                return resultado;
            }
            
            this.tipo.setTipo(this.getTipoDato(resultado));
            return resultado;
            
        }
        
        
            
        return null;
    }
    
    public tipoDato getTipoDato(Object a) {
        if (a instanceof String) {
            return tipoDato.CADENA;
        } else if (a instanceof Integer) {
            return tipoDato.ENTERO;
        } else if (a instanceof Double) {
            return tipoDato.DECIMAL;
        } else if (a instanceof Boolean) {
            return tipoDato.BOOL;
        } else if (a instanceof Character) {
            return tipoDato.CARACTER;
        } else {
            return tipoDato.VOID;
        }
    }
    
    
    
    
}
