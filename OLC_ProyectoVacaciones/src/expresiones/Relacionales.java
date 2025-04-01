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
public class Relacionales extends Instruccion {
    private Instruccion expresion1;
    private Instruccion expresion2;
    private OperadoresRelacionales relacion;
    private Instruccion expresionU;

    public Relacionales(Instruccion expresion1, Instruccion expresion2, OperadoresRelacionales relacion, int linea, int columna) {
        super(new Tipo(tipoDato.BOOL), linea, columna);
        this.expresion1 = expresion1;
        this.expresion2 = expresion2;
        this.relacion = relacion;
    }

    
    

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object expresionIzq = null, expresionDer=null;
        
        expresionIzq = this.expresion1.interpretar(arbol, tabla);
        if(expresionIzq instanceof Errores){
            return expresionIzq;
        }
            
        expresionDer = this.expresion2  .interpretar(arbol, tabla);
        if(expresionDer instanceof Errores){
            return expresionDer;
        }
             
        
        
        return switch(this.relacion){
            case IGUALACION ->
                this.igualacion(expresionIzq, expresionDer);
            case DIFERENCIACION ->
                this.diferenciacion(expresionIzq, expresionDer);
            case MAYORQUE ->
                this.mayorque(expresionIzq, expresionDer);
            case MAYORIGUAL ->
                this.mayorigual(expresionIzq, expresionDer);
            case MENORQUE ->
                this.menorque(expresionIzq, expresionDer);
            case MENORIGUAL ->
                this.menorigual(expresionIzq, expresionDer);
            default ->
                new Errores("Semantico", "Operacion relacional no disponible", this.linea, this.columna);
        };
    }
    
    
    public Object igualacion(Object exp1, Object exp2){
        var tipo1 = this.expresion1.tipo.getTipo();
        var tipo2 = this.expresion2.tipo.getTipo();
        switch (tipo1){
            case ENTERO ->{
                switch(tipo2){
                    case ENTERO->{
                        return (int)exp1 == (int)exp2;
                    }
                    case DECIMAL ->{
                        return (int)exp1 == (double)exp2;
                    }
                    case CARACTER ->{
                        char car = (Character)exp2;
                        return (int)exp1 == (int)car;
                    }
                    default ->{
                        return new Errores("Semantico", "Igualacion no valida con entero", this.linea, this.columna);
                    }
                }
                
            }
            
            case DECIMAL ->{
                switch(tipo2){
                    case ENTERO->{
                        this.tipo.setTipo(tipoDato.BOOL);
                        return (double)exp1 == (int)exp2;
                    }
                    case DECIMAL ->{
                        return (double)exp1 == (double)exp2;
                    }
                    case CARACTER ->{
                        char car = (Character)exp2;
                        return (double)exp1 == (int)car;
                    }
                    default ->{
                        return new Errores("Semantico", "Igualacion no valida con decimal", this.linea, this.columna);
                    }
                }  
            }
            
            case BOOL ->{
                System.out.println(exp1);
                System.out.println(exp2);
                switch(tipo2){
                    
                    case BOOL ->{
                        return (boolean)exp1 ==(boolean)exp2;
                    }
                    default ->{
                        return new Errores("Semantico", "Igualacion no valida con BOOL", this.linea, this.columna);

                    }
                }
                
                
            }
            
            case CARACTER ->{
                char car1 = (Character)exp1;
                switch(tipo2){
                    case ENTERO->{
                        return (int)car1 == (int)exp2;
                    }
                    case DECIMAL->{
                        return (int)car1 == (double)exp2;
                    }
                    case CARACTER->{
                        char car2 = (Character)exp2;
                        return car2 == car1;
                    }
                    default->{
                        return new Errores("Semantico", "Igualacion no valida con Char", this.linea, this.columna);
                    
                    }
                        
                }
            }
            
            case CADENA->{
                switch(tipo2){
                    case CADENA ->{
                        return exp1.toString().equals(exp2.toString());
                    }
                    default->{
                        return new Errores("Semantico", "Igualacion no valida con String", this.linea, this.columna);
                    }
                }
            }
            
            default ->{
                return new Errores("Semantico", "dato Relacional no valido", this.linea, this.columna);
            }
        }
    }
    
    public Object diferenciacion(Object exp1, Object exp2){
        var tipo1 = this.expresion1.tipo.getTipo();
        var tipo2 = this.expresion2.tipo.getTipo();
        switch (tipo1){
            case ENTERO ->{
                switch(tipo2){
                    case ENTERO->{
                        return (int)exp1 != (int)exp2;
                    }
                    case DECIMAL ->{
                        return (int)exp1 != (double)exp2;
                    }
                    case CARACTER ->{
                        char car = (Character)exp2;
                        return (int)exp1 != (int)car;
                    }
                    default ->{
                        return new Errores("Semantico", "Diferenciacion no valida con entero", this.linea, this.columna);
                    }
                }
                
            }
            
            case DECIMAL ->{
                switch(tipo2){
                    case ENTERO->{
                        return (double)exp1 != (int)exp2;
                    }
                    case DECIMAL ->{
                        return (double)exp1 != (double)exp2;
                    }
                    case CARACTER ->{
                        char car = (Character)exp2;
                        return (double)exp1 != (int)car;
                    }
                    default ->{
                        return new Errores("Semantico", "Diferenciacion no valida con decimal", this.linea, this.columna);
                    }
                }  
            }
            
            case BOOL ->{
                switch(tipo2){
                    case BOOL ->{
                        return (boolean)exp1 !=(boolean)exp2;
                    }
                    default ->{
                        return new Errores("Semantico", "Diferenciacion no valida con BOOL", this.linea, this.columna);

                    }
                }
            }
            
            case CARACTER ->{
                char car1 = (Character)exp1;
                switch(tipo2){
                    case ENTERO->{
                        return (int)car1 != (int)exp2;
                    }
                    case DECIMAL->{
                        return (int)car1 != (double)exp2;
                    }
                    case CARACTER->{
                        char car2 = (Character)exp2;
                        return car2 !=car1;
                    }
                    default->{
                        return new Errores("Semantico", "Diferenciacion no valida con Char", this.linea, this.columna);
                    
                    }
                        
                }
            }
            
            case CADENA->{
                switch(tipo2){
                    case CADENA ->{
                        return !exp1.toString().equals(exp2.toString());
                    }
                    default->{
                        return new Errores("Semantico", "Diferenciacion no valida con String", this.linea, this.columna);
                    }
                }
            }
            
            default ->{
                return new Errores("Semantico", "dato Relacional no valido", this.linea, this.columna);
            }
        }
    }
    
    public Object mayorque(Object exp1, Object exp2){
        var tipo1 = this.expresion1.tipo.getTipo();
        var tipo2 = this.expresion2.tipo.getTipo();
        switch (tipo1){
            case ENTERO ->{
                switch(tipo2){
                    case ENTERO->{
                        return (int)exp1 > (int)exp2;
                    }
                    case DECIMAL ->{
                        return (int)exp1 > (double)exp2;
                    }
                    case CARACTER ->{
                        char car = (Character)exp2;
                        return (int)exp1 > (int)car;
                    }
                    default ->{
                        return new Errores("Semantico", "Mayor que no valida con entero", this.linea, this.columna);
                    }
                }
                
            }
            
            case DECIMAL ->{
                switch(tipo2){
                    case ENTERO->{
                        return (double)exp1 > (int)exp2;
                    }
                    case DECIMAL ->{
                        return (double)exp1 > (double)exp2;
                    }
                    case CARACTER ->{
                        char car = (Character)exp2;
                        return (double)exp1 > (int)car;
                    }
                    default ->{
                        return new Errores("Semantico", "Mayor que no valido con decimal", this.linea, this.columna);
                    }
                }  
            }
            
            case CARACTER ->{
                char car1 = (Character)exp1;
                switch(tipo2){
                    case ENTERO->{
                        return (int)car1 > (int)exp2;
                    }
                    case DECIMAL->{
                        return (int)car1 > (double)exp2;
                    }
                    case CARACTER->{
                        char car2 = (Character)exp2;
                        return car2 > car1;
                    }
                    default->{
                        return new Errores("Semantico", "Mayor que no valido con Char", this.linea, this.columna);
                    
                    }
                        
                }
            }
            
            case CADENA->{
                switch(tipo2){
                    case CADENA ->{
                        return exp1.toString().length() > exp2.toString().length();
                    }
                    default->{
                        return new Errores("Semantico", "Mayor que no valido con String", this.linea, this.columna);
                    }
                }
            }
            
            case BOOL ->{
                switch(tipo2){
                    case BOOL ->{
                        boolean a = (boolean)exp1;
                        boolean b = (boolean)exp2;
                        return a && !b ;
                    }
                    default ->{
                        return new Errores("Semantico", "Mayorque no valida con BOOL", this.linea, this.columna);
                    }
                }   
            }
            
            default ->{
                return new Errores("Semantico", "dato Relacional no valido", this.linea, this.columna);
            }
        }
    }
    
    
    public Object mayorigual(Object exp1, Object exp2){
        var tipo1 = this.expresion1.tipo.getTipo();
        var tipo2 = this.expresion2.tipo.getTipo();
        switch (tipo1){
            case ENTERO ->{
                switch(tipo2){
                    case ENTERO->{
                        return (int)exp1 >= (int)exp2;
                    }
                    case DECIMAL ->{
                        return (int)exp1 >= (double)exp2;
                    }
                    case CARACTER ->{
                        char car = (Character)exp2;
                        return (int)exp1 >= (int)car;
                    }
                    default ->{
                        return new Errores("Semantico", "Mayor igual no valida con entero", this.linea, this.columna);
                    }
                }
                
            }
            
            case DECIMAL ->{
                switch(tipo2){
                    case ENTERO->{
                        return (double)exp1 >= (int)exp2;
                    }
                    case DECIMAL ->{
                        return (double)exp1 >= (double)exp2;
                    }
                    case CARACTER ->{
                        char car = (Character)exp2;
                        return (double)exp1 >= (int)car;
                    }
                    default ->{
                        return new Errores("Semantico", "Mayor igual no valido con decimal", this.linea, this.columna);
                    }
                }  
            }
            
            case CARACTER ->{
                char car1 = (Character)exp1;
                switch(tipo2){
                    case ENTERO->{
                        return (int)car1 >= (int)exp2;
                    }
                    case DECIMAL->{
                        return (int)car1 >= (double)exp2;
                    }
                    case CARACTER->{
                        char car2 = (Character)exp2;
                        return car2 >= car1;
                    }
                    default->{
                        return new Errores("Semantico", "Mayor igual no valido con Char", this.linea, this.columna);
                    
                    }
                        
                }
            }
            
            case CADENA->{
                switch(tipo2){
                    case CADENA ->{
                        return exp1.toString().length() >= exp2.toString().length();
                    }
                    default->{
                        return new Errores("Semantico", "Mayor igual no valido con String", this.linea, this.columna);
                    }
                }
            }
            
            case BOOL ->{
                switch(tipo2){
                    case BOOL ->{
                        boolean a = (boolean)exp1;
                        boolean b = (boolean)exp2;
                        return a && !b || a==b;
                    }
                    default ->{
                        return new Errores("Semantico", "Igualacion no valida con BOOL", this.linea, this.columna);
                    }
                }   
            }
            
            default ->{
                return new Errores("Semantico", "dato Relacional no valido", this.linea, this.columna);
            }
        }
    }
    
    
    public Object menorigual(Object exp1, Object exp2){
        var tipo1 = this.expresion1.tipo.getTipo();
        var tipo2 = this.expresion2.tipo.getTipo();
        switch (tipo1){
            case ENTERO ->{
                switch(tipo2){
                    case ENTERO->{
                        this.tipo.setTipo(tipoDato.BOOL);
                        return (int)exp1 <= (int)exp2;
                    }
                    case DECIMAL ->{
                        return (int)exp1 <= (double)exp2;
                    }
                    case CARACTER ->{
                        char car = (Character)exp2;
                        return (int)exp1 <= (int)car;
                    }
                    default ->{
                        return new Errores("Semantico", "Menor igual no valida con entero", this.linea, this.columna);
                    }
                }
                
            }
            
            case DECIMAL ->{
                switch(tipo2){
                    case ENTERO->{
                        this.tipo.setTipo(tipoDato.BOOL);
                        return (double)exp1 <= (int)exp2;
                    }
                    case DECIMAL ->{
                        return (double)exp1 <= (double)exp2;
                    }
                    case CARACTER ->{
                        char car = (Character)exp2;
                        return (double)exp1 <= (int)car;
                    }
                    default ->{
                        return new Errores("Semantico", "Menor igual no valido con decimal", this.linea, this.columna);
                    }
                }  
            }
            
            case CARACTER ->{
                char car1 = (Character)exp1;
                switch(tipo2){
                    case ENTERO->{
                        return (int)car1 <= (int)exp2;
                    }
                    case DECIMAL->{
                        return (int)car1 <= (double)exp2;
                    }
                    case CARACTER->{
                        char car2 = (Character)exp2;
                        return car2 <= car1;
                    }
                    default->{
                        return new Errores("Semantico", "Menor igual no valido con Char", this.linea, this.columna);
                    
                    }
                        
                }
            }
            
            case CADENA->{
                switch(tipo2){
                    case CADENA ->{
                        return exp1.toString().length() <= exp2.toString().length();
                    }
                    default->{
                        return new Errores("Semantico", "Menor igual no valido con String", this.linea, this.columna);
                    }
                }
            }
            
            case BOOL ->{
                switch(tipo2){
                    case BOOL ->{
                        boolean a = (boolean)exp1;
                        boolean b = (boolean)exp2;
                        return !a && b || a==b;
                    }
                    default ->{
                        return new Errores("Semantico", "Igualacion no valida con BOOL", this.linea, this.columna);
                    }
                }   
            }
            
            default ->{
                return new Errores("Semantico", "dato Relacional no valido", this.linea, this.columna);
            }
        }
    }
    
    public Object menorque(Object exp1, Object exp2){
        var tipo1 = this.expresion1.tipo.getTipo();
        var tipo2 = this.expresion2.tipo.getTipo();
        switch (tipo1){
            case ENTERO ->{
                switch(tipo2){
                    case ENTERO->{
                        return (int)exp1 < (int)exp2;
                    }
                    case DECIMAL ->{
                        return (int)exp1 < (double)exp2;
                    }
                    case CARACTER ->{
                        char car = (Character)exp2;
                        return (int)exp1 < (int)car;
                    }
                    default ->{
                        return new Errores("Semantico", "Menor que no valida con entero", this.linea, this.columna);
                    }
                }
                
            }
            
            case DECIMAL ->{
                switch(tipo2){
                    case ENTERO->{
                        return (double)exp1 < (int)exp2;
                    }
                    case DECIMAL ->{
                        return (double)exp1 < (double)exp2;
                    }
                    case CARACTER ->{
                        char car = (Character)exp2;
                        return (double)exp1 < (int)car;
                    }
                    default ->{
                        return new Errores("Semantico", "Mayor que no valido con decimal", this.linea, this.columna);
                    }
                }  
            }
            
            case CARACTER ->{
                char car1 = (Character)exp1;
                switch(tipo2){
                    case ENTERO->{
                        return (int)car1 < (int)exp2;
                    }
                    case DECIMAL->{
                        return (int)car1 < (double)exp2;
                    }
                    case CARACTER->{
                        char car2 = (Character)exp2;
                        return car2 < car1;
                    }
                    default->{
                        return new Errores("Semantico", "< que no valido con Char", this.linea, this.columna);
                    
                    }
                        
                }
            }
            
            case CADENA->{
                switch(tipo2){
                    case CADENA ->{
                        return exp1.toString().length() < exp2.toString().length();
                    }
                    default->{
                        return new Errores("Semantico", "Menor que no valido con String", this.linea, this.columna);
                    }
                }
            }
            
            case BOOL ->{
                switch(tipo2){
                    case BOOL ->{
                        boolean a = (boolean)exp1;
                        boolean b = (boolean)exp2;
                        return !a && b ;
                    }
                    default ->{
                        return new Errores("Semantico", "Igualacion no valida con BOOL", this.linea, this.columna);
                    }
                }   
            }
            
            default ->{
                return new Errores("Semantico", "dato Relacional no valido", this.linea, this.columna);
            }
        }
    }
    
    
}
