/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.SimboloReporte;
import simbolo.Tipo;
import simbolo.TipoSimbolo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author relda
 */
public class DeclaracionVariable extends Instruccion {
    private String id;
    private Instruccion expresion;
    private boolean mutabilidad;

    public DeclaracionVariable(String id, Tipo tipo, Instruccion expresion,  boolean mutabilidad,int linea, int columna) {
        super(tipo, linea, columna);
        this.id = id;
        this.expresion = expresion;
        this.mutabilidad = mutabilidad;
    }

    public DeclaracionVariable(String id, Tipo tipo, boolean mutabilidad, int linea, int columna) {
        super(tipo, linea, columna);
        this.id = id;
        this.mutabilidad = mutabilidad;
    }
    
        
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        TipoSimbolo tip;
        Object valor;
        if(this.expresion !=null){
            var expInterpretada =  this.expresion.interpretar(arbol, tabla);
            if(expInterpretada instanceof Errores){
                return expInterpretada;
            }
            //validando tipos
            if(this.tipo.getTipo() != this.expresion.tipo.getTipo()){
                return new Errores("Semantico", "Tipo declarado no coincide con el de la expresion "+this.expresion.tipo,
                        this.linea, this.columna);
            }
            
            //Validar si es tipo de simbolo variable o constante
            if(this.mutabilidad){
                tip = TipoSimbolo.VARIABLE;
            }else{
                tip = TipoSimbolo.CONSTANTE;
            }
            
            Simbolo sim = new Simbolo(this.tipo, this.id, expInterpretada, this.mutabilidad, tip);
            boolean puted = tabla.setVariable(sim);
            SimboloReporte rep = new SimboloReporte(sim, tabla.getNombre(), this.linea, this.columna);
            arbol.insertSimbolo(rep);
            if(!puted){
                return new Errores("Semantico", "La variable con el id "+this.id+" ya existe", this.linea, this.columna);
            }
            return null;
        }else{
            switch(this.tipo.getTipo()){
                case tipoDato.ENTERO ->{
                    valor =0;
                }case tipoDato.DECIMAL ->{
                    valor=0.0;
                }case tipoDato.CADENA->{
                    valor = "";
                }case tipoDato.BOOL->{
                    valor = true;
                }case tipoDato.CARACTER->{
                    valor = '0';
                }default->{
                    return new Errores("Semantico", "Tipo de declaracion no valido", this.linea, this.columna);
                }
            }
            
            //Validar si es tipo de simbolo variable o constante
            if(this.mutabilidad){
                tip = TipoSimbolo.VARIABLE;
            }else{
                tip = TipoSimbolo.CONSTANTE;
            }
            
            
            Simbolo sim = new Simbolo(this.tipo, this.id, valor, this.mutabilidad, tip);
            boolean puted = tabla.setVariable(sim);
            if(!puted){
                return new Errores("Semantico", "La variable con el id"+this.id+" ya existe", this.linea, this.columna);
            }
            SimboloReporte rep = new SimboloReporte(sim, tabla.getNombre(), this.linea, this.columna);
            arbol.insertSimbolo(rep);
            return null;
        }
    }
    
}
