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
import simbolo.TipoSimbolo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author relda
 */
//identificador[0]= "15";  || identificador[0][1]=25;
public class AsignacionVector extends Instruccion {

    private String identificador;
    private Instruccion indice1;
    private Instruccion indice2;
    private Instruccion expresionNueva;

    //en caso de asignacion con dos indices
    public AsignacionVector(String identificador, Instruccion indice1, Instruccion indice2, Instruccion expresionNueva, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.identificador = identificador;
        this.indice1 = indice1;
        this.indice2 = indice2;
        this.expresionNueva = expresionNueva;
    }

    //Asifnacion con un solo indice
    public AsignacionVector(String identificador, Instruccion indice1, Instruccion expresionNueva, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.identificador = identificador;
        this.indice1 = indice1;
        this.expresionNueva = expresionNueva;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var simVector = tabla.getVariable(identificador);
        var expInterpretada = this.expresionNueva.interpretar(arbol, tabla);

        if (!(simVector.isMutabilidad())) {
            return new Errores("Semantico", "El simbolo " + this.identificador + " no puede modificarse", this.linea, this.columna);
        }

        if (expInterpretada instanceof Errores) {
            return expInterpretada;
        }

        //error si no es vector, lista dinamica o el valor de el simbolo no es lista enlazada
        if (!(simVector.getValor() instanceof LinkedList)) {
            return new Errores("Semantico", "La variable " + this.identificador + " No es un vector o lista dinamica", this.linea, this.columna);
        }

        //error en caso de asignar un entero a un vector de doubles, por ejemplo
        if (!(this.expresionNueva.tipo.getTipo().equals(simVector.getTipo().getTipo()))) {
            return new Errores("Semantico", "Esta tratando de asignar al vector una expresion de un tipo diferente de "
                    + simVector.getTipo().getTipo(), this.linea, this.columna);
        }

        if (this.indice2 != null) {

            var ind1 = this.indice1.interpretar(arbol, tabla);

            if (ind1 instanceof Errores) {
                return ind1;
            }
            if (!(this.indice1.tipo.getTipo().equals(tipoDato.ENTERO))) {
                return new Errores("Semantico", "El indice para acceder a " + this.identificador + " No es un entero", this.linea, this.columna);
            }

            var ind2 = this.indice2.interpretar(arbol, tabla);

            if (ind2 instanceof Errores) {
                return ind1;
            }
            if (!(this.indice2.tipo.getTipo().equals(tipoDato.ENTERO))) {
                return new Errores("Semantico", "El indice para acceder a " + this.identificador + " No es un entero", this.linea, this.columna);
            }

            var nuevaLista = (LinkedList) simVector.getValor();
            try {
                if (nuevaLista.get((int) ind1) instanceof LinkedList) {
                    var sublista = (LinkedList) nuevaLista.get((int) ind1);
                    sublista.set((int) ind2, expInterpretada);
                    nuevaLista.set((int) ind1, sublista);
                    simVector.setValor(nuevaLista);
                    return null;
                }

            } catch (Exception e) {
                return new Errores("Semantico", e.getMessage(), this.linea, this.columna);
            }
        }

        var ind1 = this.indice1.interpretar(arbol, tabla);

        if (ind1 instanceof Errores) {
            return ind1;
        }
        if (!(this.indice1.tipo.getTipo().equals(tipoDato.ENTERO))) {
            return new Errores("Semantico", "El indice para acceder a " + this.identificador + " No es un entero", this.linea, this.columna);
        }
        var nuevaLista = (LinkedList) simVector.getValor();
        nuevaLista.set((int) ind1, expInterpretada);
        
        
        simVector.setValor(nuevaLista);
        return null;
    }

}
