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
//var vector1 : string [] = [“Hola”, “Mundo”];
//mutabilidad, Identificador, tipo de Datos, Dimensiones, LinkedList de expresiones o linkedlist
//const vector2 : int [][] = [ [1, 2], [3, 4] ];

public class DeclaracionVector extends Instruccion{
    private String identificador;
    private int dimensiones;
    private LinkedList<Object> arreglo;
    private boolean mutabilidad;
    private String identificadorNuevo;
    private Instruccion subVector;

    public DeclaracionVector(String identificador, int dimensiones, LinkedList<Object> arreglo, boolean mutabilidad, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.identificador = identificador;
        this.dimensiones = dimensiones;
        this.arreglo = arreglo;
        this.mutabilidad = mutabilidad;
        
    }

    //En caso este igualado al identificadow de otro vector
        public DeclaracionVector(String identificador, int dimensiones, String identificadorNuevo, boolean mutabilidad,  Tipo tipo, int linea, int columna) {
            super(tipo, linea, columna);
        this.identificador = identificador;
        this.dimensiones = dimensiones;
        this.mutabilidad = mutabilidad;
        this.identificadorNuevo = identificadorNuevo;
    }

    public DeclaracionVector(String identificador, int dimensiones,  Instruccion subVector,boolean mutabilidad, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.identificador = identificador;
        this.dimensiones = dimensiones;
        this.mutabilidad = mutabilidad;
        this.subVector = subVector;
    }
    
    
    
    

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        //Nueva linkedlist
        LinkedList<Object>listaNueva = new LinkedList<>();
        //En caso se requiera insertar un arreglo a un arreglo
        LinkedList<Object>listaAuxiliar = new LinkedList<>();
        
        
        if(this.arreglo != null){
        //Si el arreglo viene de esta forma {[0,1,2,3], [0,3,4]}
        if(this.dimensiones == 2){
            for(Object a: this.arreglo){
                
                //SI es instancia de LinkedList hay que volver a iterar la misma.
                if(a instanceof LinkedList linkedList){
                    listaAuxiliar = new LinkedList<>();
                    for(Object b: linkedList){
                        
                        //casteamos de Object a instruccion/expresion
                        var ins = (Instruccion)b;
                        var expInterpretada = ins.interpretar(arbol, tabla);
                        
                        //Validar Si es error
                        if(expInterpretada instanceof Errores){
                            return expInterpretada;
                        }
                        
                        //Validamos tipos
                        if(!(ins.tipo.getTipo().equals(this.tipo.getTipo()))){
                            return new Errores("Semantico", "Los tipos de dato no coinciden con la declaracion"
                                    , this.linea, this.columna);
                        }
                        //agregamos a lista auxiliar
                        listaAuxiliar.add(expInterpretada);
                    }
                    if(listaAuxiliar != null){
                        listaNueva.add(listaAuxiliar);
                    }
                    
                //Esto es en caso que venga algo como {[0,1,2],2}
                }else if(a instanceof Instruccion ins){
                    var expInterpretada = ins.interpretar(arbol, tabla);
                    
                    //Validar Si es error
                    if(expInterpretada instanceof Errores){
                            return expInterpretada;
                    }
                        
                    //Validamos tipos
                    if(!(ins.tipo.getTipo().equals(this.tipo.getTipo()))){
                        return new Errores("Semantico", "Los tipos de dato no coinciden con la declaracion"
                                , this.linea, this.columna);
                    }
                    listaNueva.add(expInterpretada);
                    
                }
            }
            
            
        }else{
            for(Object a: this.arreglo){
                if(a instanceof Instruccion ins){
                    var expInterpretada = ins.interpretar(arbol, tabla);
                    
                    //Validar Si es error
                    if(expInterpretada instanceof Errores){
                            return expInterpretada;
                    }
                        
                    //Validamos tipos
                    if(!(ins.tipo.getTipo().equals(this.tipo.getTipo()))){
                        return new Errores("Semantico", "Los tipos de dato no coinciden con la declaracion"
                                , this.linea, this.columna);
                    }
                    listaNueva.add(expInterpretada);
                }
            
            }
        }
        
        if(listaNueva != null){
            Simbolo sim = new Simbolo(this.tipo, this.identificador, listaNueva, this.mutabilidad, TipoSimbolo.ARREGLO); 
            var puted = tabla.setVariable(sim);
            if(!puted){
               return new Errores("Semantico", "Ya existe un simbolo  con el id "+this.identificador, this.linea, this.columna);
            }
            SimboloReporte rep = new SimboloReporte(sim, tabla.getNombre(), this.linea, this.columna);
            arbol.insertSimbolo(rep);
            return null;
        }
        
        //En caso este igualado al identificadow de otro vector
        }else if(this.identificadorNuevo!= null){
            var arrNuevo = tabla.getVariable(this.identificadorNuevo);
            
            //Si el valor del simbolo que retorna getVariable no es un Arreglo
            if(!(arrNuevo.getValor() instanceof LinkedList) ||
              !(arrNuevo.getTipoSimbolo().equals(TipoSimbolo.ARREGLO)) ){
                
              return new Errores("Semantico", "Identificador no pertenece a un Arreglo", this.linea, this.columna);  
                
            } else {
                int dimensionesNuevas=1;
                LinkedList asigArreglo = (LinkedList)arrNuevo.getValor();
                
                //Validando que el arreglo que se le asigne con id a la declaracion se de las mismas dimensiones
                for(var a : asigArreglo){
                    if(a instanceof LinkedList){
                       dimensionesNuevas = 2;
                       break;
                    }
                }
                
                if(dimensionesNuevas != this.dimensiones){
                    return new Errores("Semantico", "Dimensiones del Arreglo "+this.identificadorNuevo+
                            " No concuerdan con la asignacion", this.linea, this.columna);
                }
                
                Simbolo sim = new Simbolo(this.tipo, this.identificador, asigArreglo, this.mutabilidad, TipoSimbolo.ARREGLO); 
                var puted = tabla.setVariable(sim);
                if(!puted){
                    return new Errores("Semantico", "Ya existe un simbolo  con el id "+this.identificador, this.linea, this.columna);
                }
                SimboloReporte rep = new SimboloReporte(sim, tabla.getNombre(), this.linea, this.columna);
                arbol.insertSimbolo(rep);
                
            }
            
        }
        if(this.subVector != null){
            var nuevoVector = this.subVector.interpretar(arbol, tabla);
            
            if(nuevoVector instanceof Errores){
                return nuevoVector;
            }
            
            if(!subVector.tipo.getTipo().equals(this.tipo.getTipo())){
                return new Errores("Semantico", "El acceso de el vector no es del tipo "+this.tipo.getTipo(),
                        this.linea, this.columna);
            }
            
            if(!(nuevoVector instanceof LinkedList)){
                return new Errores("Semantico", "La exprecion que esta asignando a "+this.identificador+" No es un vector"
                ,this.linea, this.columna);
            }
            
            Simbolo sim = new Simbolo(this.tipo, this.identificador, nuevoVector, this.mutabilidad, TipoSimbolo.ARREGLO);
            var puted = tabla.setVariable(sim);
            if(!puted){
               return new Errores("Semantico", "Ya existe un simbolo  con el id "+this.identificador, this.linea, this.columna);
            }
            SimboloReporte rep = new SimboloReporte(sim, tabla.getNombre(), this.linea, this.columna);
            arbol.insertSimbolo(rep);
        }
        return null;
        
    }
    
    
    
    
}
