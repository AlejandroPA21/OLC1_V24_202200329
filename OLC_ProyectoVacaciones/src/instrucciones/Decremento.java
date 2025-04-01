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
public class Decremento extends Instruccion {
    private String id;

    public Decremento(String id, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.id = id;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Simbolo sim = tabla.getVariable(this.id);
        Object nuevo;
        if(sim == null){
            return new Errores("Semantico", "La variable"+this.id+"no existe", this.linea, this.columna);
        }
        if(!sim.isMutabilidad()){
            return new Errores("Semantico", "No es posible cambiar el valor de constr "+this.id, this.linea, this.columna);
        }
        if(sim.getTipo().getTipo() == tipoDato.ENTERO){
            nuevo = (int)sim.getValor()-1;
        }else if(sim.getTipo().getTipo() == tipoDato.DECIMAL){
            nuevo= (double)sim.getValor()-1;
        }else{
            return new Errores("Semantico", "Incremento con tipo no valido", this.linea, this.columna);
        }
        
        sim.setValor(nuevo);
        return null;
    }
}
