/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones.listas;

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
//identificador.append(exp);
public class Append extends Instruccion {
    private String identificador;
    private Instruccion expresion;

    public Append(String identificador, Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.identificador = identificador;
        this.expresion = expresion;
    }
    
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var expIn = this.expresion.interpretar(arbol, tabla);
        var lista = tabla.getVariable(this.identificador);
        
        if(expIn instanceof Errores){
            return expIn;
        }
        
        if(!(lista.getValor() instanceof LinkedList)){
             return new Errores("Semantico", "El identificador"+this.identificador+" No representa una lista dinamica",
                     this.linea, this.columna);
        }
        
        if(!(expresion.tipo.getTipo().equals(lista.getTipo().getTipo()))){
            return new Errores("Semantico", "La expresion que intenta asignar a la lista no es del tipo"
                    + " "+lista.getTipo().getTipo(), this.linea, this.columna);
        }
        
        LinkedList nuevaLista = (LinkedList)lista.getValor();
        nuevaLista.add(expIn);
        lista.setValor(nuevaLista);
        return null;
        
    }
    
}
