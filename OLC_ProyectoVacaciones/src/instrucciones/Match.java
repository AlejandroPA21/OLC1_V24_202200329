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
public class Match extends Instruccion {

    private Instruccion condicionMatch;
    private LinkedList<Caso> casosMathc;
    private Caso bloqueDefault;

    public Match(Instruccion condicionMatch, LinkedList<Caso> casosMathc, Caso bloqueDefault, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicionMatch = condicionMatch;
        this.casosMathc = casosMathc;
        this.bloqueDefault = bloqueDefault;
    }

    public Match(Instruccion condicionMatch, LinkedList<Caso> casosMathc, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicionMatch = condicionMatch;
        this.casosMathc = casosMathc;
    }

    public Match(Instruccion condicionMatch, Caso bloqueDefault, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicionMatch = condicionMatch;
        this.bloqueDefault = bloqueDefault;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var condM = this.condicionMatch.interpretar(arbol, tabla);

        if (condM instanceof Errores) {
            return condM;
        }

        tablaSimbolos tablaNueva = new tablaSimbolos(tabla);
        tablaNueva.setNombre(tabla.getNombre() + " Match");
        if (this.casosMathc != null && this.bloqueDefault != null) {
            for (Caso c : this.casosMathc) {

                var result = c.condicion.interpretar(arbol, tablaNueva);

                if (this.condicionMatch.tipo.getTipo() != c.condicion.tipo.getTipo()) {
                    return new Errores("Semantico", "Expresiones de Match son de tipo Incompatible", this.linea, this.columna);
                }

                if (condM == result) {
                    var cas = c.interpretar(arbol, tablaNueva);

                    if (cas instanceof Return newReturn) {
                        newReturn.tablaExpresion = tablaNueva;
                        return newReturn;
                    }

                    //arbol.entornos.add(tablaNueva);
                    if (cas instanceof Continue) {
                        break;
                    }

                    return null;
                }
            }

            var resultD = this.bloqueDefault.interpretar(arbol, tablaNueva);

            if (resultD instanceof Return newReturn) {
                newReturn.tablaExpresion = tablaNueva;
                return newReturn;
            }

            if (resultD instanceof Errores) {
                return resultD;
            }
            //arbol.entornos.add(tablaNueva);

        }
        if (this.casosMathc != null && this.bloqueDefault == null) {
            for (Caso c : this.casosMathc) {
                var result = c.condicion.interpretar(arbol, tablaNueva);

                if (this.condicionMatch.tipo.getTipo() != c.condicion.tipo.getTipo()) {
                    return new Errores("Semantico", "Expresiones de Match son de tipo Incompatible", this.linea, this.columna);
                }

                if (condM == result) {
                    c.interpretar(arbol, tablaNueva);
                    return null;
                }
            }
        }

        if (this.casosMathc == null && this.bloqueDefault != null) {
            var resultD = this.bloqueDefault.interpretar(arbol, tablaNueva);

            if (resultD instanceof Return newReturn) {
                newReturn.tablaExpresion = tablaNueva;
                return newReturn;
            }

            if (resultD instanceof Errores) {
                return resultD;
            }
        }

        return null;

    }

}
