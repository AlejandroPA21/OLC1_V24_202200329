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
public class Start extends Instruccion {

    private String identificador;
    private LinkedList<Instruccion> parametros;

    public Start(String identificador, LinkedList<Instruccion> parametros,int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.identificador = identificador;
        this.parametros = parametros;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {

        var busquedaFun = arbol.getFuncion(this.identificador);
        if (busquedaFun == null) {
            return new Errores("Semantico", "La funcion/Metodo " + this.identificador + " no existe", this.linea, this.columna);
        }
        if (busquedaFun instanceof Funcion funcion) {
            var newTabla = new tablaSimbolos(arbol.getTablaGlobal());
            newTabla.setNombre("START_WITH");
            

            if (funcion.parametros.size() != this.parametros.size()) {
                return new Errores("Semantico", "Error en la cantidad de parametros, se esperan " + funcion.parametros.size(),
                        this.linea, this.columna);
            }

            for (int i = 0; i < this.parametros.size(); i++) {
                var identificador2 = (String) funcion.parametros.get(i).get("id");
                var tipo2 = (Tipo) funcion.parametros.get(i).get("tipo");
                var valor = this.parametros.get(i);

                var declaracionParametro = new DeclaracionVariable(identificador2, tipo2, valor, true, this.linea, this.columna);
                var resInterpretacion = declaracionParametro.interpretar(arbol, newTabla);
                if (resInterpretacion instanceof Errores) {
                    return resInterpretacion;
                }

            }

            var resultFuncion = funcion.interpretar(arbol, newTabla);
            if (resultFuncion instanceof Errores) {
                return resultFuncion;
            }
        }

        return null;
    }

}
