/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.SimboloReporte;
import simbolo.Tipo;
import simbolo.TipoSimbolo;
import simbolo.tablaSimbolos;

/**
 *
 * @author relda
 */
//List< <TIPO> > <ID> = new List();
public class DeclaracionLista extends Instruccion {

    private String identificador;
    private String nuevaLista;

    public DeclaracionLista(String Identificador, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.identificador = Identificador;
    }

    //En caso venga igualado al identificador de otra lista
    public DeclaracionLista(String Identificador, String nuevaLista, Tipo tipo, int linea, int columna) {

        super(tipo, linea, columna);
        this.identificador = Identificador;
        this.nuevaLista = nuevaLista;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        if (this.nuevaLista != null) {
            var lista = tabla.getVariable(this.nuevaLista);
            if (lista.getTipoSimbolo().equals(TipoSimbolo.DINAMICA)) {
                return new Errores("Semanticos", "Esta tratando de asignar a la lista " + this.identificador + " Un simbolo que no es una lista",
                         this.linea, this.columna);
            }

            if (!(lista.getTipo().getTipo().equals(this.tipo.getTipo()))) {
                return new Errores("Semantico", "La lista que esta tratando de asignar no es del tipo " + this.tipo.getTipo(), this.linea,
                         this.columna);
            }

            Simbolo sim = new Simbolo(this.tipo, this.identificador, lista, true, TipoSimbolo.DINAMICA);
            var puted = tabla.setVariable(sim);
            if (!puted) {
                return new Errores("Semantico", "Ya existe un simbolo  con el id " + this.identificador, this.linea, this.columna);
            }
            SimboloReporte rep = new SimboloReporte(sim, tabla.getNombre(), this.linea, this.columna);
            arbol.insertSimbolo(rep);
            return null;
        }

        LinkedList<Object> lista = new LinkedList<>();
        Simbolo sim = new Simbolo(this.tipo, this.identificador, lista, true, TipoSimbolo.DINAMICA);
        var puted = tabla.setVariable(sim);
        if (!puted) {
            return new Errores("Semantico", "Ya existe un simbolo  con el id " + this.identificador, this.linea, this.columna);
        }
        SimboloReporte rep = new SimboloReporte(sim, tabla.getNombre(), this.linea, this.columna);
        arbol.insertSimbolo(rep);
        return null;
    }

}
