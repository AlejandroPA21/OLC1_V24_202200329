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
public class While extends Instruccion {

    private Instruccion condicion;
    private LinkedList<Instruccion> bloqueWhile;

    public While(Instruccion condicion, LinkedList<Instruccion> bloqueWhile, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.bloqueWhile = bloqueWhile;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var cond = this.condicion.interpretar(arbol, tabla);
        if (cond instanceof Errores) {
            return cond;
        }
        if (this.condicion.tipo.getTipo() != tipoDato.BOOL) {
            return new Errores("Semantico", "Expresion de while no es de tipo bool", this.linea, this.columna);
        }

        while ((boolean) cond) {
            var newTabla2 = new tablaSimbolos(tabla);
            newTabla2.setNombre(tabla.getNombre() + ", While");
            for (Instruccion i : this.bloqueWhile) {

                if (i instanceof Return newReturn) {
                    newReturn.tablaExpresion = newTabla2;
                    return newReturn;
                }

                if (i instanceof Continue) {
                    break;
                }

                if (i instanceof Break) {
                    return null;
                }
                var ins = i.interpretar(arbol, newTabla2);

                if (ins instanceof Return newReturn) {
                    newReturn.tablaExpresion = newTabla2;
                    return newReturn;
                }

                if (ins instanceof Continue) {
                    break;
                }

                if (ins instanceof Break) {
                    return null;
                }
                if (ins instanceof Errores) {
                    return ins;
                }

            }

            cond = this.condicion.interpretar(arbol, tabla);
            if (cond instanceof Errores) {
                return cond;
            }
        }
        return null;
    }

}
