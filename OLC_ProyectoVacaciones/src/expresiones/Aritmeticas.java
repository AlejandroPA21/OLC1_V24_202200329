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
public class Aritmeticas extends Instruccion{
    private Instruccion operando1;
    private Instruccion operando2;
    private operadoresAritmeticos operacion;
    private Instruccion operandoUnico;

    public Aritmeticas( Instruccion operandoUnico, operadoresAritmeticos operacion, int linea, int columna) {
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.operacion = operacion;
        this.operandoUnico = operandoUnico;
    }

    public Aritmeticas(Instruccion operando1, Instruccion operando2, operadoresAritmeticos operacion, int linea, int columna) {
        super(new Tipo(tipoDato.ENTERO), linea, columna);
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operacion = operacion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object opIzq = null, opDer= null, unico=null;
        if(this.operandoUnico != null){
            unico = this.operandoUnico.interpretar(arbol, tabla);
            if(unico instanceof Errores){
                return unico;
            }
            
        }else{
            opIzq = this.operando1.interpretar(arbol, tabla);
            if(opIzq instanceof Errores){
                return opIzq;
            }
            
            opDer = this.operando2.interpretar(arbol, tabla);
            if(opDer instanceof Errores){
                return opDer;
            } 
        }
        return switch(this.operacion){
            case SUMA ->
                this.suma(opIzq, opDer);
            case RESTA ->
                this.resta(opIzq, opDer);
            case MULTIPLICACION ->
                this.multiplicacion(opIzq, opDer);
            case DIVISION ->
                this.division(opIzq, opDer);
            case MODULO ->
                this.modulo(opIzq, opDer);
            case POTENCIA ->
                this.potencia(opIzq, opDer);
            case NEGACION ->
                this.negacion(unico);
            default ->
                new Errores("SEMANTICO", "Operador aritmetico no Valido", this.linea, this.columna);
                
        };
                
    }
    
    
    public Object suma(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            
            case tipoDato.ENTERO -> {
                
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 + (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op1 + (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        char c = (Character) op2;
                        return (int) c + (int) op1;
                    }
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Suma erronea", this.linea, this.columna);
                    }
                }
            }
            
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 + (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 + (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        char c = (Character) op2;
                        return (double) op1 + (int)c;
                    }
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Suma erronea con double", this.linea, this.columna);
                    }
                }
            }
            
            case tipoDato.BOOL -> {
                switch (tipo2) {
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    case tipoDato.BOOL ->{
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Suma erronea con bool", this.linea, this.columna);
                    }
                }
            }
            
            
            case tipoDato.CADENA -> {
                this.tipo.setTipo(tipoDato.CADENA);
                return op1.toString() + op2.toString();
            }
            
            case tipoDato.CARACTER -> {
                //op3 variable auxiliar con op1 como char
                char op3 = (Character) op1;
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op3 + (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op3 + (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        char c = (Character) op2;
                        return op1.toString()+op2.toString();
                    }
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Suma erronea con char", this.linea, this.columna);
                    }
                }
            }
            
            default -> {
                return new Errores("SEMANTICO", "Suma, tipo dato erroneo", this.linea, this.columna);
            }
        }
    }
    
    
    
    public Object resta(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 - (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op1 - (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        char c = (Character) op2;
                        return (int) op1 - (int) c;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "resta erronea con enteros", this.linea, this.columna);
                    }
                }
            }
            
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 - (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 - (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        char b = (Character) op2;
                        return (double)op1 - (int)b;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Resta erronea con double", this.linea, this.columna);
                    }
                }
            }
            
            
            case tipoDato.CARACTER -> {
                //op3 variable auxiliar con op1 como char
                char op3 = (Character) op1;
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op3 - (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op3 - (double) op2;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Resta erronea con char", this.linea, this.columna);
                    }
                }
            }
            
            default -> {
                
                
                return new Errores("SEMANTICO", "Resta, tipo dato erroneo"+tipo1.toString()+
                        op2.toString()+op1.toString(), this.linea, this.columna);
            }
        }
    }
    
    
    
    public Object multiplicacion(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 * (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op1 * (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        char c = (Character) op2;
                        return (int) c * (int) op1;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Mult erronea con entero", this.linea, this.columna);
                    }
                }
            }
            
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 * (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 * (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        char b = (Character) op2;
                        return (double)op1 * (int)b;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Mult erronea con decimal", this.linea, this.columna);
                    }
                }
            }
            
            
            case tipoDato.CARACTER -> {
                //op3 variable auxiliar con op1 como char
                char op3 = (Character) op1;
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op3 * (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op3 * (double) op2;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "mult erronea con char ", this.linea, this.columna);
                    }
                }
            }
            
            default -> {
                return new Errores("SEMANTICO", "Multiplicacion, tipo dato erroneo", this.linea, this.columna);
            }
        }
    }
    
    
    public Object division(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        if((int)op2 ==0){
                            return new Errores("Semantico", "division con cero", this.linea, this.columna);
                        }
                        
                        return (double)((int) op1) / (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        if((double)op2 ==0){
                            return new Errores("Semantico", "division con cero", this.linea, this.columna);
                        }
                        return (int) op1 / (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        char c = (Character) op2;
                        if((int)c ==0){
                            return new Errores("Semantico", "division con cero", this.linea, this.columna);
                        }
                        return (double)((int) op1)/(int) c  ;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "div erronea con entero", this.linea, this.columna);
                    }
                }
            }
            
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        if((int)op2 ==0){
                            return new Errores("Semantico", "division con cero", this.linea, this.columna);
                        }
                        return (double) op1 / (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        if((double)op2 ==0){
                            return new Errores("Semantico", "division con cero", this.linea, this.columna);
                        }
                        return (double) op1 / (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        char b = (Character) op2;
                        if((int)b ==0){
                            return new Errores("Semantico", "division con cero", this.linea, this.columna);
                        }
                        return (double)op1 / (int)b;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "div erronea con decimal", this.linea, this.columna);
                    }
                }
            }
            
            
            case tipoDato.CARACTER -> {
                //op3 variable auxiliar con op1 como char
                char op3 = (Character) op1;
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        if((int)op2 ==0){
                            return new Errores("Semantico", "division con cero", this.linea, this.columna);
                        }
                        return (double)((int) op3) / (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        if((double)op2 ==0){
                            return new Errores("Semantico", "division con cero", this.linea, this.columna);
                        }
                        return (double)((int) op3) / (double) op2;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "div erronea con char ", this.linea, this.columna);
                    }
                }
            }
            
            default -> {
                return new Errores("SEMANTICO", "Division, tipo dato erroneo", this.linea, this.columna);
            }
        }
    }
    
    public Object potencia(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        double x =  Math.pow((int) op1 , (int) op2);
                        return (int)x;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((int) op1 , (double) op2);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "potencia erronea con entero", this.linea, this.columna);
                    }
                }
            }
            
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((double) op1 , (int) op2);
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((double) op1 , (double) op2);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "potencia erronea con decimal", this.linea, this.columna);
                    }
                }
            }
            
            default -> {
                return new Errores("SEMANTICO", "Potencia, tipo dato erroneo", this.linea, this.columna);
            }
        }
    }
    
    
    public Object modulo(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double)((int) op1 % (int) op2);
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op1 % (double) op2;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Modulo erroneo con entero", this.linea, this.columna);
                    }
                }
            }
            
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 % (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 % (double) op2;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Modulo erroneo con decimal", this.linea, this.columna);
                    }
                }
            }
            
            default -> {
                return new Errores("SEMANTICO", "Modulo, tipo dato erroneo", this.linea, this.columna);
            }
        }
    }
    
    public Object negacion(Object op1) {
        var opU = this.operandoUnico.tipo.getTipo();
        switch (opU) {
            case tipoDato.ENTERO -> {
                this.tipo.setTipo(tipoDato.ENTERO);
                return (int) op1 * -1;
            }
            case tipoDato.DECIMAL -> {
                this.tipo.setTipo(tipoDato.DECIMAL);
                return (double) op1 * -1;
            }
            default -> {
                return new Errores("SEMANTICO", "Negacion erronea", this.linea, this.columna);
            }
        }
    }
    
}
