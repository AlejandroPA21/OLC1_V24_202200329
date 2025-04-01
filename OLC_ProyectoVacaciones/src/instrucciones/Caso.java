/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author relda
 */
public class Caso extends Instruccion {
    public Instruccion condicion;
    private LinkedList<Instruccion> bloqueCaso;
    private boolean casoNormal;

    public Caso(Instruccion condicion, LinkedList<Instruccion> bloqueCaso, boolean casoNormal,  int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.bloqueCaso = bloqueCaso;
        this.casoNormal = casoNormal;
    }

    public Caso(LinkedList<Instruccion> bloqueCaso, boolean casoNormal, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.bloqueCaso = bloqueCaso;
        this.casoNormal = casoNormal;
    }
    
    
    
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        tablaSimbolos tablaNueva = new tablaSimbolos(tabla);
        for(Instruccion ins: this.bloqueCaso){
            if(ins instanceof Continue){
                return ins;
            }
            
            if (ins instanceof Return newReturn) {
                newReturn.tablaExpresion = tablaNueva;
                return newReturn;
            }
            
            
            var result = ins.interpretar(arbol, tablaNueva);
            if(result instanceof Errores){
                return result;
            }
            
            if(result instanceof Continue){
                return result;
            }
        }
        return true;
    }
    
}
