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
public class Llamada extends Instruccion {

    private String identificador;
    public LinkedList<Instruccion> parametros;
    public static int contador;
    
    public Llamada(String identificador, LinkedList<Instruccion> parametros, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.identificador = identificador;
        this.parametros = parametros;
        Llamada.contador = 0;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var busqueda = arbol.getFuncion(this.identificador);
        if (busqueda == null) {
            return new Errores("Semantico", "La funcion/Metodo " + this.identificador + " no existe", this.linea, this.columna);
        }

        if (busqueda instanceof Funcion funcion) {
            var newTabla = new tablaSimbolos(arbol.getTablaGlobal());
            newTabla.setNombre("LLAMADA FUN/MET " + this.identificador+ Llamada.contador);
            Llamada.contador++;

            if (funcion.parametros.size() != this.parametros.size()) {
                return new Errores("Semantico", "Error en la cantidad de parametros, se esperan " + funcion.parametros.size(),
                        this.linea, this.columna);
            }
            
            for (int i = 0; i < this.parametros.size(); i++) {
                var identificador2 = (String)funcion.parametros.get(i).get("id");
                var tipo2 = (Tipo) funcion.parametros.get(i).get("tipo");
                var valor = this.parametros.get(i);
                //int a
                var declaracionParametro = new DeclaracionVariable(identificador2, tipo2,  true, this.linea, this.columna);
                var resInterpretacion = declaracionParametro.interpretar(arbol, newTabla);
                
                
                // a=0
                if (resInterpretacion instanceof Errores) {
                    return resInterpretacion;
                }
                //
                
                var valorInterpretado = valor.interpretar(arbol, tabla);
                //System.out.println(valorInterpretado.toString());
                if (valorInterpretado instanceof Errores) {
                    return valorInterpretado;
                }
                
                var variable = newTabla.getVariable(identificador2);
                if(variable == null){
                     return new Errores("Semantico", "El simbolo " + identificador2 + " no existe en "+newTabla.getNombre()
                             , this.linea, this.columna);

                }
                
                if (variable.getTipo().getTipo() != valor.tipo.getTipo()) {
                    return new Errores("Semantico", "Error en tipo de parametro",
                            this.linea, this.columna);
                }
                
                variable.setValor(valorInterpretado);
            }
            /*System.out.println(newTabla.getNombre());
            System.out.println(tabla.getNombre());*/
            var resultFuncion = funcion.interpretar(arbol, newTabla);
            this.tipo.setTipo(funcion.tipo.getTipo());
            
            
            if(resultFuncion != null){
                return resultFuncion;
            }else if(resultFuncion == null){
                return null;
            }
                
            
            if(resultFuncion instanceof Errores){
                return resultFuncion;
            }
                
            
            
            
            
            
    }
        return null;
}

}
