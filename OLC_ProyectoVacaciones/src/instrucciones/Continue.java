/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import abstracto.Instruccion;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author relda
 */
public class Continue extends Instruccion{

    public Continue( int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
    }
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        return "continue";
    }
    
}
