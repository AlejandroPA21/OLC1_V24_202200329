/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simbolo;

/**
 *
 * @author relda
 */
public class Simbolo {
    private Tipo tipo;
    private String id;
    private Object valor;
    private boolean mutabilidad;
    private TipoSimbolo tipoSimbolo;

    public Simbolo(Tipo tipo, String id, boolean mutabilidad, TipoSimbolo tipoSimbolo ) {
        this.tipo = tipo;
        this.id = id;
        this.mutabilidad = mutabilidad;
        this.tipoSimbolo = tipoSimbolo;
        
    }

    public Simbolo(Tipo tipo, String id, Object valor,  boolean mutabilidad, TipoSimbolo tipoSimbolo) {
        this.tipo = tipo;
        this.id = id;
        this.valor = valor;
        this.mutabilidad = mutabilidad;
        this.tipoSimbolo = tipoSimbolo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public boolean isMutabilidad() {
        return mutabilidad;
    }

    public void setMutabilidad(boolean mutabilidad) {
        this.mutabilidad = mutabilidad;
    }

    public TipoSimbolo getTipoSimbolo() {
        return tipoSimbolo;
    }

    public void setTipoSimbolo(TipoSimbolo tipoSimbolo) {
        this.tipoSimbolo = tipoSimbolo;
    }
    
    
    
}
