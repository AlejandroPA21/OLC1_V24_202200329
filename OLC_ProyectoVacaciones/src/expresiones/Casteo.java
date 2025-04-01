/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

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
public class Casteo extends Instruccion{
    private Tipo tipoCasteo;
    private Instruccion expresion;

    public Casteo(Tipo tipoCasteo, Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.tipoCasteo = tipoCasteo;
        this.expresion = expresion;
    }

    
    

    

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        var expInterpretada = this.expresion.interpretar(arbol, tabla);
        var tipoExp = this.expresion.tipo.getTipo();
        if(expInterpretada instanceof Errores){
            return expInterpretada;
        }
        
        switch (this.tipoCasteo.getTipo()){
            
            case tipoDato.ENTERO->{
                switch(tipoExp){
                    case tipoDato.DECIMAL ->{ 
                        this.tipo.setTipo(tipoDato.ENTERO);
                        double expDouble = (double)expInterpretada;
                        return (int)expDouble;
                    }case tipoDato.CARACTER ->{
                        this.tipo.setTipo(tipoDato.ENTERO);
                        char a = (Character) expInterpretada;
                        return (int)a;
                    }default-> {
                        return new Errores("Semantico", "Error al tratar de castear a Entero", this.linea, this.columna);
                    }
                }
            }
            
            case tipoDato.DECIMAL->{
                switch(tipoExp){
                    case tipoDato.ENTERO ->{ 
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        int a = (int)expInterpretada;
                        return (double)a;
                    }case tipoDato.CARACTER ->{
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        char a = (Character) expInterpretada;
                        int b = (int)a;
                        return (double)b;
                    }default-> {
                        return new Errores("Semantico", "Error al tratar de castear a double"+tipoExp.toString(), this.linea, this.columna);
                    }
                }
            }
            
            case tipoDato.CARACTER->{
                if(tipoExp == tipoDato.ENTERO){
                    this.tipo.setTipo(tipoDato.CARACTER);
                    int numero = (int)expInterpretada;
                    
                    return (char)numero;
                }else{
                    return new Errores("Semantico", "Error al tratar de castear a char", this.linea, this.columna);
                }
            } 
            default->{
                return new Errores("Semantico", "No se permiten casteos con este tipo de dato", this.linea, this.columna);
            }
               
        }
    }
    
}
