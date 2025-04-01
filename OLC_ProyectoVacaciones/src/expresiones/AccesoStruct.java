/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.HashMap;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author relda
 */
public class AccesoStruct extends Instruccion {

    private String idStruct;
    private String idStruct2;
    private String campo;

    public AccesoStruct(String idStruct, String campo, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.idStruct = idStruct;
        this.campo = campo;
    }

    //idStruct.idStruct2.idCampo = expresion;
    public AccesoStruct(String idStruct, String idStruct2, String campo, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.idStruct = idStruct;
        this.idStruct2 = idStruct2;
        this.campo = campo;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var structSimbolo = tabla.getVariable(this.idStruct);

        if (structSimbolo == null) {
            return new Errores("Semantico", "No existe el Struct con el nombre  " + this.idStruct, this.linea, this.columna);
        }
        
        var struct = (HashMap) structSimbolo.getValor();
        
        if (this.idStruct2 == null) {
            
            Object valor = struct.get(this.campo);

            if (valor == null) {
                return new Errores("Semantico", "El campo " + this.campo + " no existe en " + this.idStruct, this.linea, this.columna);
            }

            tipoDato tip = this.getTipoDato(valor);
            this.tipo.setTipo(tip);
            return valor;
        }else{
            Object valor = struct.get(this.idStruct2);
            if (valor == null) {
                return new Errores("Semantico", "El campo " + this.idStruct2 + " no existe en " + this.idStruct, this.linea, this.columna);
            }
            
            if(valor instanceof HashMap){
                var valorSub = ((HashMap)valor).get(this.campo);
                
                if(valorSub == null){
                    return new Errores("Semantico", "EL valor asociado a"+this.campo+" nO existe en "+this.idStruct2, this.linea, this.columna);
                }
                
                tipoDato tip = this.getTipoDato(valorSub);
                this.tipo.setTipo(tip);
                return valorSub;
            }
        }
        return null;
    }

    public tipoDato getTipoDato(Object a) {
        if (a instanceof String) {
            return tipoDato.CADENA;
        } else if (a instanceof Integer) {
            return tipoDato.ENTERO;
        } else if (a instanceof Double) {
            return tipoDato.DECIMAL;
        } else if (a instanceof Boolean) {
            return tipoDato.BOOL;
        } else if (a instanceof Character) {
            return tipoDato.CARACTER;
        } else {
            return tipoDato.VOID;
        }
    }
}
