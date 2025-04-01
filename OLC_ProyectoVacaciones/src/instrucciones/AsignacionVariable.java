/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author relda
 */
public class AsignacionVariable extends Instruccion {
    private String id;
    private Instruccion exprecionNueva;

    public AsignacionVariable(String id, Instruccion exprecionNueva,  int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
        this.exprecionNueva = exprecionNueva;
    }
    
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        //corroborando que exista la variable en el hash
        Simbolo sim = tabla.getVariable(this.id);
        if(sim == null){
            return new Errores("Semantico", "La variable"+this.id+"no existe", this.linea, this.columna);
        }
        //interpretando nueva expresion para cambiar el dato
        var expInterpretada = this.exprecionNueva.interpretar(arbol, tabla);
        if(expInterpretada instanceof Errores){
            return expInterpretada;
        }
        
        // validando tipos
        if(sim.getTipo().getTipo() != this.exprecionNueva.tipo.getTipo()){
            return new Errores("Semantico", "No es posible cambiar de tipo a "+this.id, this.linea, this.columna);
        }
        
        //comprobando mutabilidad
        if(!sim.isMutabilidad()){
            return new Errores("Semantico", "No es posible cambiar el valor de constr "+this.id, this.linea, this.columna);
        }
        
        sim.setValor(expInterpretada);
        return null;
    }
    
}
