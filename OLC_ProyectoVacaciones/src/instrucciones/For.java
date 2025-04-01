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
public class For extends Instruccion {
    private Instruccion asignacion;
    private Instruccion condicion;
    private Instruccion actualizazacion;
    private LinkedList<Instruccion> bloqueFor;

    public For(Instruccion asignacion, Instruccion condicion, Instruccion actualizazacion, LinkedList<Instruccion> bloqueFor,  int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.asignacion = asignacion;
        this.condicion = condicion;
        this.actualizazacion = actualizazacion;
        this.bloqueFor = bloqueFor;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        tablaSimbolos newTabla = new tablaSimbolos(tabla);
        newTabla.setNombre(tabla.getNombre()+" INstancia FOR");
        
        //arbol.entornos.add(newTabla);
        var asigFor = this.asignacion.interpretar(arbol, newTabla);
        if(asigFor instanceof Errores){
            return asigFor;
        }
        
        var condFor = this.condicion.interpretar(arbol, newTabla);
        if(condFor instanceof Errores){
            return condFor;
        }
        
        if(this.condicion.tipo.getTipo() != tipoDato.BOOL){
            return new Errores("Semantico", "La condicion del for no es de tipo relacional", this.linea, this.columna);
        }
        
        
        while((boolean)this.condicion.interpretar(arbol, newTabla)){
            var newTabla2 = new tablaSimbolos(newTabla);
            newTabla2.setNombre(tabla.getNombre()+", For");
            for(Instruccion i : this.bloqueFor){
                
                if (i instanceof Return newReturn) {
                    newReturn.tablaExpresion = newTabla2;
                    return newReturn;
                }
                
                
                //System.out.println(i.getClass());
                if (i instanceof Break) {
                   return null;
                }
                
                if (i instanceof Continue) {
                   break;
                }
                
                var ins = i.interpretar(arbol, newTabla2);
                
                if (ins instanceof Return newReturn) {
                        newReturn.tablaExpresion = newTabla2;
                        return newReturn;
                    }
                
                if (ins instanceof Continue) {
                    break;
                }
                
                if (ins instanceof Break) {
                    return null;
                }
                if(ins instanceof Errores){
                    return ins;
                }
                    
                
            }
            
            var act = this.actualizazacion.interpretar(arbol, newTabla);
            if(act instanceof Errores){
                    return act;
            }
            var c  = this.condicion.interpretar(arbol, newTabla);
            if(c instanceof Errores){
                return condFor;
            }
        }
        //arbol.entornos.add(newTabla2);
        return null;
    }
    
    
}
