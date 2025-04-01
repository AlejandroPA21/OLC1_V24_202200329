/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Analizadores;

/**
 *
 * @author relda
 */
public class Generador {
    public static void main(String[] args) {
        try{
            String ruta = "src/Analizadores/";
            /*
                ruta -> ruta del los archivos
                -d -> ruta donde se genera la salida
                ruta salida
            */ 

           String Flex[] = {ruta + "lex2.jflex", "-d", ruta};
           jflex.Main.generate(Flex);
          String Cup[] = { "-destdir", ruta, "-parser", "parser", ruta + "sin.cup" };
          java_cup.Main.main(Cup);
        }catch(Exception e){
            System.out.println(e);
        }
    
    }
}
