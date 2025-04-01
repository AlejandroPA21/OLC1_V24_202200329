/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Vistas;

import Analizadores.parser;
import Analizadores.scanner;
import abstracto.Instruccion;
import excepciones.Errores;
import instrucciones.AsignacionVariable;
import instrucciones.AsignacionVector;
import instrucciones.DeclaracionLista;
import instrucciones.DeclaracionVariable;
import instrucciones.DeclaracionVector;
import instrucciones.Funcion;
import instrucciones.Start;
import instrucciones.listas.Append;
import instrucciones.structs.Asignacion;
import instrucciones.structs.Instanciacion;
import instrucciones.structs.Struct;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JOptionPane;
import simbolo.Arbol;
import simbolo.SimboloReporte;
import simbolo.tablaSimbolos;

/**
 *
 * @author relda
 */
public class PanelEdicion extends javax.swing.JInternalFrame {

    private File archivo;
    private Principal principal_clase;
    public LinkedList<Errores> erroresLexSin = new LinkedList<>();
    public LinkedList<SimboloReporte> SimbolosL = new LinkedList<>();

    public PanelEdicion() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        reportes = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        guardarCambios = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reportes.setBackground(new java.awt.Color(102, 102, 102));
        reportes.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        reportes.setForeground(new java.awt.Color(255, 255, 255));
        reportes.setText("Reportes");
        reportes.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        reportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportesActionPerformed(evt);
            }
        });
        jPanel1.add(reportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 110, 30));

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Ejecutar");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 110, 30));

        guardarCambios.setBackground(new java.awt.Color(51, 51, 51));
        guardarCambios.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        guardarCambios.setForeground(new java.awt.Color(255, 255, 255));
        guardarCambios.setText("Guardar Camb.");
        guardarCambios.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        guardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarCambiosActionPerformed(evt);
            }
        });
        jPanel1.add(guardarCambios, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 110, 30));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 620, 310));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public void setForm(Principal principal) {
        this.principal_clase = principal;

    }

    public void cargarContenidoArchivo(String rutaArchivo) {
        try {
            // Crea un objeto File a partir de la ruta del archivo
            File archivoNuevo = new File(rutaArchivo);
            StringBuilder contenido;
            try ( // Lee el contenido del archivo de texto y lo guarda en un StringBuilder
                    BufferedReader lector = new BufferedReader(new FileReader(archivoNuevo))) {
                contenido = new StringBuilder();
                String linea;
                while ((linea = lector.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }
            }
            // Asigna el contenido del archivo al JTextArea sobrescribiendo el contenido existente
            jTextArea1.setText(contenido.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void generarTablaErroresHTML() {
        try {
            // Crear un objeto FileWriter para escribir en un archivo HTML
            File file = new File("Errores.html");
            FileWriter writer = new FileWriter(file);

            // Escribir el encabezado del HTML
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n");
            writer.write("<head>\n");
            writer.write("<title>Errores lexicos y sintacticos</title>\n");
            writer.write("<style>\n");
            writer.write("table { border-collapse: collapse; width: 100%; }\n");
            writer.write("th, td { border: 1px solid black; padding: 8px; text-align: left; }\n");
            writer.write("th { background-color: #f2f2f2; }\n");
            writer.write("</style>\n");
            writer.write("</head>\n");
            writer.write("<body>\n");

            // Escribir la tabla HTML con los errores léxicos y sintácticos
            writer.write("<h1>Errores léxicos, sintácticos y semanticos</h1>\n");
            writer.write("<table>\n");
            writer.write("<tr><th>Tipo</th><th>Descripcion</th><th>Fila</th><th>Columna</th></tr>\n");
            for (Errores error : erroresLexSin) {
                writer.write("<tr>");
                writer.write("<td>" + error.getTipo() + "</td>");
                writer.write("<td>" + error.getDesc() + "</td>");
                writer.write("<td>" + error.getLinea() + "</td>");
                writer.write("<td>" + error.getColumna() + "</td>");

                writer.write("</tr>\n");
            }
            writer.write("</table>\n");

            // Escribir el final del HTML
            writer.write("</body>\n");
            writer.write("</html>\n");

            // Cerrar el FileWriter
            writer.close();

            // Vaciar la lista de errores
            this.erroresLexSin.clear();

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (file.exists()) {
                    desktop.open(file);
                }
            }
            System.out.println("HTML generado exitosamente.");

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo HTML: " + e.getMessage());
        }
    }

    public void generarTablaVariablesHTML() {
        try {
            File file = new File("TablaSimbolos.html");
            FileWriter writer = new FileWriter(file);

            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n");
            writer.write("<head>\n");
            writer.write("<title>TablaSimbolos</title>\n");
            writer.write("<style>\n");
            writer.write("body { font-family: Arial, sans-serif; margin: 20px; }\n");
            writer.write("h1 { color: #333; }\n");
            writer.write("table { width: 100%; border-collapse: collapse; margin-top: 20px; }\n");
            writer.write("table, th, td { border: 1px solid #ccc; }\n");
            writer.write("th, td { padding: 10px; text-align: left; }\n");
            writer.write("th { background-color: #f2f2f2; }\n");
            writer.write("tr:nth-child(even) { background-color: #f9f9f9; }\n");
            writer.write("tr:hover { background-color: #f1f1f1; }\n");
            writer.write("</style>\n");
            writer.write("</head>\n");
            writer.write("<body>\n");

            writer.write("<h1>Tabla de Símbolos</h1>\n");
            writer.write("<table>\n");
            writer.write("<tr><th>Número</th><th>Identificador</th><th>Tipo</th><th>Tipo Simbolo</th><th>Entorno</th><th>Valor</th><th>Línea</th><th>Columna</th></tr>\n");

            int numero = 1;
            for (SimboloReporte sim : Arbol.getReporteSimbolos()) {
                String identificador = sim.getSimReporte().getId();
                Object valor = sim.getSimReporte().getValor();
                String tipo = sim.getSimReporte().getTipo().getTipo().toString();
                String tipoSimbolo = sim.getSimReporte().getTipoSimbolo().toString();
                String entorno = sim.getEntorno();
                int linea = sim.getLinea();
                int columna = sim.getColumna();
                String valorString = valor.toString();

                writer.write("<tr>");
                writer.write("<td>" + numero + "</td>");
                writer.write("<td>" + identificador + "</td>");
                writer.write("<td>" + tipo + "</td>");
                 writer.write("<td>" + tipoSimbolo + "</td>");
                writer.write("<td>" + entorno + "</td>");
                writer.write("<td>" + valorString + "</td>");
                writer.write("<td>" + linea + "</td>");
                writer.write("<td>" + columna + "</td>");
                writer.write("</tr>\n");

                numero++;
            }
            writer.write("</table>\n");

            writer.write("</body>\n");
            writer.write("</html>\n");

            writer.close();

            System.out.println("HTML generado exitosamente.");

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (file.exists()) {
                    desktop.open(file);
                }
            }

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo HTML: " + e.getMessage());
        }
    }


    private void reportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportesActionPerformed
        // TODO add your handling code here:
        this.generarTablaVariablesHTML();
        this.generarTablaErroresHTML();
    }//GEN-LAST:event_reportesActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String contenido = jTextArea1.getText();
        if (!contenido.isEmpty()) {
            try {

                scanner s = new scanner(new BufferedReader(new StringReader(contenido)));
                parser p = new parser(s);
                var resultado = p.parse();
                var ast = new Arbol((LinkedList<Instruccion>) resultado.value);
                var tabla = new tablaSimbolos();
                tabla.setNombre("GLOBAL");
                ast.setTablaGlobal(tabla);
                ast.setConsola("");

                for (Errores e : s.listaErrores) {
                    this.erroresLexSin.add(e);
                }

                for (Errores e : p.listaErrores) {
                    this.erroresLexSin.add(e);
                }

                //funciones, metodos o structs
                for (var a : ast.getInstrucciones()) {
                    if (a == null) {
                        continue;
                    }
                    
                    if (a instanceof Struct) {
                        var res = a.interpretar(ast, tabla);
                        if (res instanceof Errores errores) {
                            this.erroresLexSin.add(errores);
                        }
                    }

                    if (a instanceof Funcion) {
                        ast.addFunciones(a);
                    }
                }

                ////declaraciones o asignaciones globales
                for (var a : ast.getInstrucciones()) {
                    if (a == null) {
                        continue;
                    }
                   
                    if (a instanceof DeclaracionVariable
                            || a instanceof DeclaracionVector
                            || a instanceof DeclaracionLista
                            || a instanceof AsignacionVariable
                            || a instanceof Append
                            || a instanceof AsignacionVector
                            || a instanceof Instanciacion
                            || a instanceof Asignacion) {
                        
                        var res = a.interpretar(ast, tabla);
                        if (res instanceof Errores errores) {
                            this.erroresLexSin.add(errores);
                        }
                    }

                }

                //execute -> start_with
                Start e = null;
                for (var a : ast.getInstrucciones()) {
                    
                    if (a == null) {
                        continue;
                    }
                    if (a instanceof Start execute) {
                        e = execute;
                        break;
                    }
                }

                var resultadoExecute = e.interpretar(ast, tabla);
                if (resultadoExecute instanceof Errores errores) {
                    this.erroresLexSin.add(errores);
                }

                this.SimbolosL = Arbol.getReporteSimbolos();
                String nuevoTexto = ast.getConsola().replace("\\n", "\n");
                this.principal_clase.agregarContenidoJTextArea(nuevoTexto);

            } catch (Exception ex) {
                System.out.println("Algo salio mal");
                System.out.println(ex);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void guardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarCambiosActionPerformed
        // TODO add your handling code here:
        String contenido = jTextArea1.getText();

        // Verificar si hay un archivo asociado
        if (archivo != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                // Escribir el contenido en el archivo
                writer.write(contenido);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay un archivo asociado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_guardarCambiosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton guardarCambios;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton reportes;
    // End of variables declaration//GEN-END:variables
}
