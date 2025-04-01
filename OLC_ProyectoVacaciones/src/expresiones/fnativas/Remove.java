/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones.fnativas;

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
//identificador.remove(indice);
public class Remove extends Instruccion {
    private String identificador;
    private Instruccion indice;

    public Remove(String identificador, Instruccion indice, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.identificador = identificador;
        this.indice = indice;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var ind = this.indice.interpretar(arbol, tabla);
        var lista = tabla.getVariable(this.identificador);
        
        if(ind instanceof Errores){
            return ind;
        }
        
        if(!(lista.getValor() instanceof LinkedList) ){
             return new Errores("Semantico", "El identificador"+this.identificador+" No representa una lista dinamica",
                     this.linea, this.columna);
        }
        
        if(!(this.indice.tipo.getTipo().equals(tipoDato.ENTERO))){
            return new Errores("Semantico", "El indice "+ind.toString()+ " no es entero", this.linea, this.columna);
        }
        
        LinkedList nuevaLista = (LinkedList)lista.getValor();
        var eliminado = nuevaLista.remove((int)ind);
        lista.setValor(nuevaLista);
        
        this.tipo.setTipo(lista.getTipo().getTipo());
        return eliminado;
    }
    
}
