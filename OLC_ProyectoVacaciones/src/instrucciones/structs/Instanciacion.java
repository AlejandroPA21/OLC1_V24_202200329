/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones.structs;

import abstracto.Instruccion;
import excepciones.Errores;
import expresiones.AccesoVariable;
import java.util.HashMap;
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
/*
    var e1:estudiante = { nombre: “prueba”, edad: 10 };
    <MUTABILIDAD> <ID> : <NOMBRE_STRUCT> = {<VALORES_STRUCT> };


            struct{
                string:identificador
                estudiante:NuevoEstudiante
             }escritorio;
            var es:estudiante = { nombre: “prueba”, edad: 10 }; 
            (1)var es:estudiante = { nombre: “prueba”, edad: 10 };
            (2)var e1:escritorio = { identidicador: “prueba”, NuevoEstudiante: var es:estudiante = { nombre: “prueba”, edad: 10 }; };
            (3)var e1:escritorio = { identidicador: “prueba”, NuevoEstudiante: es };
            
            ->
 */
public class Instanciacion extends Instruccion {

    private boolean mutabilidad;
    public String identificador;
    private String idStruct;
    private HashMap<String, Object> valoresStruct;

    public Instanciacion(boolean mutabilidad, String identificador, String idStruct, HashMap<String, Object> valoresStruct, int linea, int columna) {
        super(new Tipo(tipoDato.INS_STRUCT), linea, columna);
        this.mutabilidad = mutabilidad;
        this.identificador = identificador;
        this.idStruct = idStruct;
        this.valoresStruct = valoresStruct;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        //Se busva estructura del sttuct que se esta instanciando en el hash(general en el arbol) con el idStruct
        var structDec = arbol.getStruct(this.idStruct);

        if (structDec == null) {
            return new Errores("Semantico", "No existe Struct con el nombre " + this.idStruct, this.linea, this.columna);
        }

        HashMap<String, Object> newHash = new HashMap<>();//se crea un nuevo hashMap, para asignarlo al simbolo correspondiente

        /*
          Basicamente recorremos el Hash guardado en el arbol con el idStruct
          el cual tiene la estructura <String, Tipo>, y corroboramos tipos con 
          lo que trae consigo el valoresStruct <String, Object>, se va haciendo comparaciones
          Sincronas con ambos hashMaps, pues en teoria tienen la mismma llave.
        
        
        
          Practicamente recorremos con sus llaves a structDec.
         */
        for (String key : structDec.keySet()) {
            var valor = valoresStruct.get(key);//obtenesmos un valor del hash, asociado a Key<Key, Object>
            if (valor == null) {
                return new Errores("Semantico", "Falta el campo" + key + " en su instanciacion", this.linea, this.columna);
            }

            if (valor instanceof Instanciacion) {// (2)
                Instanciacion newInstanciacion = (Instanciacion) valor;

                //se interpreta la instansacion, para que la nueva variable de instansacion se guarde en la tabla
                var instanciacionIn = newInstanciacion.interpretar(arbol, tabla);
                if (instanciacionIn instanceof Errores) {
                    return instanciacionIn;
                }
                //En este momento la nueva instancia esta guardada como simbolo, la obtenemos de la tabla
                String idNuevaInstanciacion = newInstanciacion.identificador;
                var structExistente = tabla.getVariable(idNuevaInstanciacion);
                if (structExistente == null) {
                    return new Errores("Semantico", "El simbolo" + idNuevaInstanciacion + "no existe", this.linea, this.columna);
                }

                HashMap subHashMap = (HashMap) structExistente.getValor();
                newHash.put(key, subHashMap);

            }

            if (valor instanceof AccesoVariable) {  //(3)
                //Como en teoria la instancia ya esta guardada en la tabla, entonces, accedemos a ella y a su hashMap(Struct).
                var res = ((AccesoVariable) valor).interpretar(arbol, tabla);
                if (res instanceof Errores) {
                    return res;
                }
                if (!(res instanceof HashMap)) {
                    return new Errores("Semantico", "El simbolo no representa un Struct", this.linea, this.columna);
                }
                HashMap subHashMap = (HashMap) res;
                newHash.put(key, subHashMap);

            }

            //(1)
            if (valor instanceof Instruccion && !(valor instanceof Instanciacion) && !(valor instanceof AccesoVariable)) {
                Instruccion ins = (Instruccion) valor;
                var resultado = ins.interpretar(arbol, tabla);

                if (resultado instanceof Errores) {
                    return resultado;
                }

                newHash.put(key, resultado);
            }

            //(4)
            if (valor instanceof HashMap) {
                HashMap hashCampo = (HashMap) valor;
                HashMap<String, Object> subStruct = new HashMap<>();
                for (var hola : hashCampo.keySet()) {
                    var newCampo = hashCampo.get(hola);
                    if (newCampo instanceof Instruccion) {
                        var newCampoInte = ((Instruccion) newCampo).interpretar(arbol, tabla);
                        subStruct.put((String) hola, newCampoInte);
                    }
                }
                if (!(subStruct.isEmpty())) {
                    newHash.put(key, subStruct);
                }
            }
        }

        if (!(newHash.isEmpty())) {
            Simbolo sim = new Simbolo(this.tipo, this.identificador, newHash, this.mutabilidad, TipoSimbolo.STRUCT);
            boolean puted = tabla.setVariable(sim);
            if (!puted) {
                return new Errores("Semantico", "La variable con el id" + this.identificador + " ya existe", this.linea, this.columna);
            }
            SimboloReporte rep = new SimboloReporte(sim, tabla.getNombre(), this.linea, this.columna);
            arbol.insertSimbolo(rep);

        }
        return null;
    }

}
