/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones.vectores;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.TipoSimbolo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author relda
 */
public class AccesoVector extends Instruccion {

    private String identificador;
    private Instruccion indice1;
    private Instruccion indice2;

    public AccesoVector(String identificador, Instruccion indice1, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.identificador = identificador;
        this.indice1 = indice1;
    }

    public AccesoVector(String identificador, Instruccion indice1, Instruccion indice2, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.identificador = identificador;
        this.indice1 = indice1;
        this.indice2 = indice2;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var vector = tabla.getVariable(this.identificador);
        if(!(vector.getValor() instanceof LinkedList)){
            return new Errores("SEMANTICA", "El identificador " + this.identificador + " no representa a un VECTOR"
                    + "O lista Dinamiva en " + tabla.getNombre(),
                    this.linea, this.columna);
        }
        
        if (vector == null) {
            return new Errores("SEMANTICA", "Vector " + this.identificador + " no existente en el contecto " + tabla.getNombre(),
                    this.linea, this.columna);
        }

        
        if (this.indice2 != null) {
            var ind1 = this.indice1.interpretar(arbol, tabla);
            if (ind1 instanceof Errores) {
                return ind1;
            }

            var ind2 = this.indice2.interpretar(arbol, tabla);
            if (ind1 instanceof Errores) {
                return ind2;
            }

            if (!this.indice1.tipo.getTipo().equals(tipoDato.ENTERO)
                    || !this.indice2.tipo.getTipo().equals(tipoDato.ENTERO)) {
                return new Errores("Semantico", "Los indices con los cuales intenta acceder a el vector no son enterods",
                        this.linea, this.columna);
            }

            if (!this.indice1.tipo.getTipo().equals(tipoDato.ENTERO)
                    || !this.indice2.tipo.getTipo().equals(tipoDato.ENTERO)) {
                return new Errores("Semantico", "Los indices con los cuales intenta acceder a el vector no son enterods",
                        this.linea, this.columna);
            }

            LinkedList newL = (LinkedList) vector.getValor();

            LinkedList newL2 = (LinkedList) newL.get((int) ind1);
            var valor = newL2.get((int) ind2);

            this.tipo.setTipo(this.getTipoDato(valor));
            return valor;

        } else{
            var ind1 = this.indice1.interpretar(arbol, tabla);
            if (ind1 instanceof Errores) {
                return ind1;
            }

            if (!this.indice1.tipo.getTipo().equals(tipoDato.ENTERO)) {
                return new Errores("Semantico", "Los indices con los cuales intenta acceder a el vector no son enterods",
                        this.linea, this.columna);
            }

            if (!this.indice1.tipo.getTipo().equals(tipoDato.ENTERO)) {
                return new Errores("Semantico", "Los indices con los cuales intenta acceder a el vector no son enteros",
                        this.linea, this.columna);
            }

            LinkedList newL = (LinkedList) vector.getValor();
            var valor = newL.get((int) ind1);
            
            if(valor instanceof LinkedList){
               LinkedList sublista = (LinkedList)valor;
               var tipoSublista = this.getTipoDato(sublista.get(0));
               this.tipo.setTipo(tipoSublista);
               return valor;
            }
            
            this.tipo.setTipo(this.getTipoDato(valor));
            return valor;
        }
        
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
