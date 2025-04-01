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
public class Logicas extends Instruccion {
    private Instruccion expresion1;
    private Instruccion expresion2;
    private Instruccion expresionU;
    private OperadoresLogicos  operador;

    public Logicas(Instruccion expresion1, Instruccion expresion2, OperadoresLogicos operador, int linea, int columna) {
        super(new Tipo(tipoDato.BOOL), linea, columna);
        this.expresion1 = expresion1;
        this.expresion2 = expresion2;
        this.operador = operador;
    }

    public Logicas(Instruccion expresionU, OperadoresLogicos operador,  int linea, int columna) {
        super(new Tipo(tipoDato.BOOL), linea, columna);
        this.expresionU = expresionU;
        this.operador = operador;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object op1 = null, op2 = null, opU = null;
        
        
        if (this.expresionU != null){
            opU=this.expresionU.interpretar(arbol, tabla);
            if(opU instanceof Errores){
                return opU;
            }
        }else{
            op1=this.expresion1.interpretar(arbol, tabla);
            if(op1 instanceof Errores){
                return op1;
            }
            
            op2=this.expresion2.interpretar(arbol, tabla);
            if(op2 instanceof Errores){
                return op2;
            }
        }
        
        switch(this.operador){
            
            case OR->{
                return (boolean)op1 ||(boolean) op2;
            }case AND->{
                return (boolean)op1 && (boolean) op2;
            }case XOR->{
                boolean a = (boolean)op1;
                boolean b = (boolean)op2;
                return (!a && b || !b && a);
            }case NOT-> {
                return !(boolean)opU;
            }default ->{
                return new Errores("Semantico", "Operador logico no disponible", this.linea, this.columna);
            }
        }
    }
    
    
    
    
    
    
}
