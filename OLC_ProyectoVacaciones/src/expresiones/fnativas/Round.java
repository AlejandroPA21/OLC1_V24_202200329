/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones.fnativas;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author relda
 */
public class Round extends Instruccion {

    private Instruccion expresion;

    public Round(Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var expInterpretada = this.expresion.interpretar(arbol, tabla);

        if (expInterpretada instanceof Errores) {
            return expInterpretada;
        }

        if (!(expInterpretada instanceof Integer || expInterpretada instanceof Double)) {
            return new Errores("Semantico", "No se puede aplicar round a un dato de tipo "
                    + expInterpretada.getClass().toString(),
                    this.linea, this.columna);
        }

        return this.roundValue(expInterpretada);
    }

    public int roundValue(Object value) {
        switch (value) {
            case Double aDouble -> {
                return (int) Math.round(aDouble);
            }
            case Integer integer -> {
                return integer;
            }
            default -> {
            }
        }
        return 0;
    }

}
