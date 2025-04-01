package instrucciones.structs;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.HashMap;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.TipoSimbolo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author relda
 */

/*
    e1.edad = 28;
    <IDENTIFICADOR>. <IDENTIFICADOR> = EXPRESION;
 */
public class Asignacion extends Instruccion {

    private String idStruct, idStruct2, idCampo;
    private Instruccion expresion;

    public Asignacion(String idStruct, String idCampo, Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.idStruct = idStruct;
        this.idCampo = idCampo;
        this.expresion = expresion;
    }

    //idStruct.idStruct2.idCampo = expresion;
    public Asignacion(String idStruct, String idStruct2, String idCampo, Instruccion expresion, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.idStruct = idStruct;
        this.idStruct2 = idStruct2;
        this.idCampo = idCampo;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var expInterpretada = this.expresion.interpretar(arbol, tabla);
        if (expInterpretada instanceof Errores) {
            return expInterpretada;
        }

        Simbolo structSimbolo = tabla.getVariable(idStruct);
        if (structSimbolo == null) {
            return new Errores("Semantico", this.idStruct + " No es un struct existente", this.linea, this.columna);
        }
        
        if(!(structSimbolo.isMutabilidad())){
            return new Errores("Semantico", "El valor de "+this.idStruct+" no puede modificarse", this.linea, this.columna);
        }

        if (!(structSimbolo.getTipoSimbolo().equals(TipoSimbolo.STRUCT))) {
            return new Errores("Semantico", "El simbolo" + this.idStruct + " No es un struct ", this.linea, this.columna);
        }

        var instanciaStruct = (HashMap) structSimbolo.getValor();

        if (this.idStruct2 == null) {

            var existe = instanciaStruct.get(this.idCampo);

            if (existe == null) {
                return new Errores("Semantico", "El campo" + this.idCampo + " No existe en el struct " + this.idStruct, this.linea, this.columna);
            }

            if (!(this.getTipoDato(existe).equals(this.getTipoDato(expInterpretada)))) {
                return new Errores("Semantico", "El campo" + this.idCampo + " no es del tipo " + this.getTipoDato(expInterpretada),
                        this.linea, this.columna);
            }
            instanciaStruct.put(this.idCampo, expInterpretada);
            structSimbolo.setValor(instanciaStruct);
            return null;
        } else {
            var existe = instanciaStruct.get(this.idStruct2);
            
            if (existe == null) {
                return new Errores("Semantico", "El campo" + this.idCampo + " No existe en el struct " + this.idStruct, this.linea, this.columna);
            }
            
            if (existe instanceof HashMap) {
                var subStruct = (HashMap) existe;
                var valorNuevo = subStruct.get(this.idCampo);

                if (valorNuevo == null) {
                    return new Errores("Semantico", "El campo" + this.idCampo + " No existe en el struct " + this.idStruct, this.linea, this.columna);
                }

                if (!(this.getTipoDato(valorNuevo).equals(this.getTipoDato(expInterpretada)))) {
                    return new Errores("Semantico", "El campo" + this.idCampo + " no es del tipo " + this.getTipoDato(expInterpretada),
                            this.linea, this.columna);
                }

                subStruct.put(this.idCampo, expInterpretada);
                instanciaStruct.put(idStruct2, subStruct);
                structSimbolo.setValor(instanciaStruct);
                return null;
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
