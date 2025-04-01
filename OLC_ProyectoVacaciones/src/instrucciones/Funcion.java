/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.HashMap;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author relda
 */
public class Funcion extends Instruccion {

    public String identificador;
    public LinkedList<HashMap> parametros;
    public LinkedList<Instruccion> instrucciones;

    //tipo:
    //id:
    //int a, double b
    public Funcion(String identificador, LinkedList<HashMap> parametros, LinkedList<Instruccion> instrucciones, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.identificador = identificador;
        this.parametros = parametros;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {

        for (var ins : this.instrucciones) {

            if (ins instanceof Return ret ) {
                var valor = ins.interpretar(arbol, tabla);
                
                if (!(((Return) ins).tipo.getTipo().equals(this.tipo.getTipo()))) {
                    return new Errores("Semantico", "El tipo de retorno no es de tipo " + this.tipo.getTipo(),
                            this.linea, this.columna);
                }

                return valor;
            }
            
            var resultado = ins.interpretar(arbol, tabla);
            
            if (resultado instanceof Return ret) {
                var valor = ret.interpretar(arbol, tabla);

                if (!(ret.tipo.getTipo().equals(this.tipo.getTipo()))) {
                    return new Errores("Semantico", "El tipo de retorno no es de tipo " + this.tipo.getTipo() + " Es de tipo" + ((Return) resultado).tipo.getTipo(),
                            this.linea, this.columna);
                }

                return valor;
            }

            if (resultado instanceof Errores) {
                return resultado;
            }
        }
        return null;
    }

}
