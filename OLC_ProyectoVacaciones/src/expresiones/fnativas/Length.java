/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones.fnativas;

import abstracto.Instruccion;
import excepciones.Errores;
import expresiones.AccesoVariable;
import expresiones.Nativo;
import expresiones.vectores.AccesoVector;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author relda
 */
public class Length extends Instruccion {

    private Instruccion expresion;

    public Length(Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        if ((this.expresion instanceof AccesoVariable || this.expresion instanceof Nativo ||this.expresion instanceof  AccesoVector)) {
            var insInterpretada = this.expresion.interpretar(arbol, tabla);
            
            if(insInterpretada instanceof Errores ){
                return insInterpretada;
            }
            
            if (!(insInterpretada instanceof LinkedList || insInterpretada instanceof String)) {
                return new Errores("Semantico", "Tipo de expresion no "+ insInterpretada.toString()+ 
                                    " compatible con funcion length", this.linea, this.columna);
            }
            
            if(insInterpretada instanceof LinkedList newVector){
                return newVector.size();
            } 
            
            if(insInterpretada instanceof String newVector){
                return newVector.length();
            } 
            
        } else {
            return new Errores("Semantico", "Tipo de expresion no valida en length", this.linea, this.columna);

        }
        return 0;
    }
}
