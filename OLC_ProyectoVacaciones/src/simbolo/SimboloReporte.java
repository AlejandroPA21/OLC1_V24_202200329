/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simbolo;

/**
 *
 * @author relda
 */
public class SimboloReporte {
    private Simbolo simReporte;
    private String Entorno;
    private int linea;
    private int columna;

    public SimboloReporte(Simbolo simReporte, String Entorno, int linea, int columna) {
        this.simReporte = simReporte;
        this.Entorno = Entorno;
        this.linea = linea;
        this.columna = columna;
    }
    
    

    public Simbolo getSimReporte() {
        return simReporte;
    }

    public void setSimReporte(Simbolo simReporte) {
        this.simReporte = simReporte;
    }

    public String getEntorno() {
        return Entorno;
    }

    public void setEntorno(String Entorno) {
        this.Entorno = Entorno;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    
    
    
}
