/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones.fnativas;

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
public class ToString extends Instruccion{
    private Instruccion expresion;

    public ToString(Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.CADENA), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var interpretada = this.expresion.interpretar(arbol, tabla);
        
        if(interpretada instanceof Errores){
            return interpretada;
        }
            
        return interpretada.toString();
    }
    
    
    
}
