/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones.fnativas;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.TipoSimbolo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author relda
 */
public class Find extends Instruccion {

    private String identificador;
    private Instruccion expresion;

    public Find(String identificador, Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.BOOL), linea, columna);
        this.identificador = identificador;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Simbolo findIn = tabla.getVariable(this.identificador);
        var exp = this.expresion.interpretar(arbol, tabla);

        if (findIn == null) {
            return new Errores("Semantico", "No existe un simbolo llamado " + this.identificador + ", en " + tabla.getNombre(),
                    this.linea, this.columna);
        }

        if (!(findIn.getTipoSimbolo().equals(TipoSimbolo.ARREGLO) || findIn.getTipoSimbolo().equals(TipoSimbolo.DINAMICA))) {
            return new Errores("Semantico", "La funcion find solo pueda usarse con Vectores o listas Dinamicas",
                    this.linea, this.columna);
        }
        if (findIn.getValor() instanceof LinkedList vec) {
            System.out.println("entro");
            for (Object a : vec) {

                if (a instanceof LinkedList newVec) {
                    for (Object b : newVec) {
                        if (b.toString().toLowerCase().equals(exp.toString().toLowerCase())) {
                            return true;
                        }
                    }
                }
                /* System.out.println(a.toString());
                System.out.println(exp.toString());
                System.out.println(a.toString().toLowerCase()+"  "+exp.toString().toLowerCase());*/

                if (a.toString().toLowerCase().equals(exp.toString().toLowerCase())) {
                    return true;
                }

            }
        }
        return false;
    }

}
