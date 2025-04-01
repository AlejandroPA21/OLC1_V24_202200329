/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones.structs;

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

/*
    struct {
        <LISTA_STRUCT>
    } <ID> ;
    struct{
        nombre:string;
        edad:int;
    }estudiante;

    STRUCT{
        HashMap<String, Tipo>
    }IDENTIFICADOR;   

 */

 /*
             
             struct{
                string:nombre;
                int : edad
             }estudiante;
            
             struct{
                string:identificador
                estudiante:NuevoEstudiante
             }escritorio
            
            
            */
public class Struct extends Instruccion {
    private HashMap<String, Object> camposStruct;
    private String identificador;

    public Struct(HashMap<String, Object> camposStruct, String identificador,  int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.camposStruct = camposStruct;
        this.identificador = identificador;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        HashMap<String, Tipo> nuevosCampos = new HashMap<>();
        
        for (String key : camposStruct.keySet()){
            
            if(camposStruct.get(key) instanceof Tipo){
                Tipo tipo1 = (Tipo)camposStruct.get(key);
                nuevosCampos.put(key, tipo1);
            }
            if(camposStruct.get(key) instanceof String){
                var nuevoId =(String) camposStruct.get(key);
                var subStruct = arbol.getStruct(nuevoId);
                if(subStruct ==null){
                    return new Errores("Semantico", "El campo "+key+" No representa un struct", this.linea, this.columna);
                }
                nuevosCampos.put(key, new Tipo(tipoDato.INS_STRUCT));
            }
            
        }
        
        var result = arbol.insertStruct(identificador, nuevosCampos);
        if(!result){
            return new Errores("Semantico","El Struct con el nombre "+this.identificador+" ya existe", this.linea, this.columna);
        }
        
        return null;
    }
    
    
    
}
