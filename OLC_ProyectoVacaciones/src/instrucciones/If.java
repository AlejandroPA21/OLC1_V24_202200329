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
public class If extends Instruccion {

    private Instruccion condicion;
    private LinkedList<Instruccion> bloqueIf;
    private LinkedList<Instruccion> bloqueElse;
    private Instruccion elseIf;

    public If(Instruccion condicion, LinkedList<Instruccion> bloqueIf, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.bloqueIf = bloqueIf;
    }

    public If(Instruccion condicion, LinkedList<Instruccion> bloqueIf, LinkedList<Instruccion> bloqueElse, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.bloqueIf = bloqueIf;
        this.bloqueElse = bloqueElse;
    }

    public If(Instruccion condicion, LinkedList<Instruccion> bloqueIf, Instruccion elseIf, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.bloqueIf = bloqueIf;
        this.elseIf = elseIf;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {

        var cond = this.condicion.interpretar(arbol, tabla);

        //verificando si la condicion es un error
        if (cond instanceof Errores) {
            return cond;
        }

        //validando tipos
        if (this.condicion.tipo.getTipo() != tipoDato.BOOL) {
            return new Errores("Semantico", "Condicion if no es de tipo bool es " + this.condicion.tipo.getTipo(),
                    this.linea, this.columna);
        }

        tablaSimbolos tablaNueva = new tablaSimbolos(tabla);
        tablaNueva.setNombre(tabla.getNombre() + ", IF");

        if (this.bloqueElse == null) {
            if ((boolean) cond) {
                for (Instruccion inst : this.bloqueIf) {
                    //System.out.println(inst.getClass());
                    if (inst instanceof Return newReturn) {
                        newReturn.tablaExpresion = tablaNueva;
                        return newReturn;
                    }

                    if (inst instanceof Break) {
                        return inst;
                    }

                    if (inst instanceof Continue) {
                        return inst;
                    }

                    var resultado = inst.interpretar(arbol, tablaNueva);
                    
                    if (resultado instanceof Return newReturn) {
                        newReturn.tablaExpresion = tablaNueva;
                        return newReturn;
                    }

                    if (resultado instanceof Continue) {
                        return resultado;
                    }

                    if (resultado instanceof Break) {
                        return resultado;
                    }
                    if (resultado instanceof Errores) {
                        return resultado;
                    }
                }
                //arbol.entornos.add(tablaNueva);

                return null;
            }

            if (this.elseIf != null) {
                tablaSimbolos tablaEif = new tablaSimbolos(tabla);
                tablaEif.setNombre(tabla.getNombre() + ", Else IF");
                //arbol.entornos.add(tablaEif);
                var res = this.elseIf.interpretar(arbol, tablaEif);
                
                if (res instanceof Return newReturn) {
                        newReturn.tablaExpresion = tablaEif;
                        return newReturn;
                    }
                
                
                if (res instanceof Continue) {
                    return res;
                }

                if (res instanceof Break) {
                    return res;
                }
                if (res instanceof Errores) {
                    return res;
                }
                return null;

            }
        } else {
            if ((boolean) cond) {
                for (Instruccion inst : this.bloqueIf) {
                    
                    if (inst instanceof Return newReturn) {
                        newReturn.tablaExpresion = tablaNueva;
                        return newReturn;
                    }
                    
                    if (inst instanceof Break) {
                        return inst;
                    }

                    if (inst instanceof Continue) {
                        return inst;
                    }
                    var resultado = inst.interpretar(arbol, tablaNueva);
                    
                    if (resultado instanceof Return newReturn) {
                        newReturn.tablaExpresion = tablaNueva;
                        return newReturn;
                    }

                    if (resultado instanceof Continue) {
                        return resultado;
                    }

                    if (resultado instanceof Errores) {
                        return resultado;
                    }
                    if (resultado instanceof Break) {
                        return resultado;
                    }

                }
                //arbol.entornos.add(tablaNueva);

                return null;

            } else if (this.elseIf != null) {
                tablaSimbolos tablaEif = new tablaSimbolos(tabla);
                tablaEif.setNombre(tabla.getNombre() + ", Else IF");
                //arbol.entornos.add(tablaEif);

                var res = this.elseIf.interpretar(arbol, tablaEif);
                
                if (res instanceof Return newReturn) {
                        newReturn.tablaExpresion = tablaEif;
                        return newReturn;
                    }
                
                if (res instanceof Continue) {
                    return res;
                }

                if (res instanceof Break) {
                    return res;
                }
                if (res instanceof Errores) {
                    return res;
                }
                return null;
            } else {
                tablaSimbolos tablaE = new tablaSimbolos(tabla);
                tablaE.setNombre(tabla.getNombre() + " Else");
                //arbol.entornos.add(tablaE);

                for (Instruccion inst : this.bloqueElse) {
                    
                    if (inst instanceof Return newReturn) {
                        newReturn.tablaExpresion = tablaE;
                        return newReturn;
                    }
                    
                    if (inst instanceof Continue) {
                        return inst;
                    }

                    if (inst instanceof Break) {
                        return inst;
                    }
                    var resultado = inst.interpretar(arbol, tablaE);
                    
                    if (resultado instanceof Return newReturn) {
                        newReturn.tablaExpresion = tablaE;
                        return newReturn;
                    }
                    if (resultado instanceof Continue) {
                        return resultado;
                    }

                    if (resultado instanceof Break) {
                        return resultado;
                    }
                    if (resultado instanceof Errores) {
                        return resultado;
                    }
                }

                return null;
            }
        }
        return null;
    }

}
