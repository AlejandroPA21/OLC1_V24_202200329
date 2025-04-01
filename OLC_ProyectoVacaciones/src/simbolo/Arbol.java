/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simbolo;

import abstracto.Instruccion;
import excepciones.Errores;
import instrucciones.Funcion;
import instrucciones.structs.Struct;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author relda
 */
public class Arbol {
    private LinkedList<Instruccion> instrucciones;
    private String consola;
    private tablaSimbolos tablaGlobal;
    private LinkedList<Errores> erroresLst;
    static  LinkedList<SimboloReporte> reporteSimbolos; 
    private LinkedList<Instruccion>funciones;
    private HashMap<String, HashMap>mapaStructs;

    public Arbol(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
        this.consola = "";
        this.tablaGlobal = new tablaSimbolos();
        this.erroresLst = new LinkedList<Errores>();
        Arbol.reporteSimbolos = new LinkedList<SimboloReporte>();
        this.funciones = new LinkedList<Instruccion>();
        this.mapaStructs = new HashMap<String, HashMap>();
    }

    public LinkedList<Instruccion> getInstrucciones() {
        return instrucciones;
    }
    
    public void Print(String valor){
        this.consola += valor + "\n";
    }

    public void setInstrucciones(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getConsola() {
        return consola;
    }

    public void setConsola(String consola) {
        this.consola = consola;
    }

    public tablaSimbolos getTablaGlobal() {
        return tablaGlobal;
    }

    public void setTablaGlobal(tablaSimbolos tablaGLobal) {
        this.tablaGlobal = tablaGLobal;
    }

    public LinkedList<Errores> getErrores() {
        return erroresLst;
    }

    public void setErrores(LinkedList<Errores> errores) {
        this.erroresLst = errores;
    }
    
    
    
   

    public static LinkedList<SimboloReporte> getReporteSimbolos() {
        return reporteSimbolos;
    }

    public LinkedList<Errores> getErroresLst() {
        return erroresLst;
    }

    public void setErroresLst(LinkedList<Errores> erroresLst) {
        this.erroresLst = erroresLst;
    }

    public LinkedList<Instruccion> getFunciones() {
        return funciones;
    }

    public void setFunciones(LinkedList<Instruccion> funciones) {
        this.funciones = funciones;
    }
    
    
    public void addFunciones(Instruccion ins){
        this.funciones.add(ins);
    }

    public HashMap<String, HashMap> getMapaStructs() {
        return mapaStructs;
    }

    public void setMapaStructs(HashMap<String, HashMap> mapaStructs) {
        this.mapaStructs = mapaStructs;
    }
    
    
    
    public void insertSimbolo(SimboloReporte sim){
        for(SimboloReporte s: Arbol.reporteSimbolos ){ 
            if(s.getSimReporte().getId().toLowerCase().equals(sim.getSimReporte().getId().toLowerCase())
            && s.getEntorno().equals(sim.getEntorno()) ){
                
                var newValor= sim.getSimReporte().getValor();
                s.getSimReporte().setValor(newValor);
                return;
            }
        }
        Arbol.reporteSimbolos.add(sim);
    }
    
    public boolean insertStruct(String id, HashMap<String, Tipo>newHash){
        var existe = this.mapaStructs.get(id);
        if(existe!= null){
            return false;
        }
        this.mapaStructs.put(id, newHash);
        return true;
    }
    

    public HashMap<String, Tipo> getStruct(String id){
        var existe = this.mapaStructs.get(id);
        if(existe== null){
            return null;
        }
        return existe;
    }
    
    
    public Instruccion getFuncion(String id) {
        for (var i : this.funciones) {
            if (i instanceof Funcion metodo) {
                if (metodo.identificador.equalsIgnoreCase(id)) {
                    return i;
                }
            }
        }
        return null;
    }
    
    
    
    
   
    
}
