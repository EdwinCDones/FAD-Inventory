/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fad.reportes;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author scajasc
 */
public class generarReportes {
    public void reporteExistencia(String nombre_cat){
        try {
            
            if(nombre_cat.equals("Todo")){
               nombre_cat = "%" ;
            }
            
            Conexion c =  new Conexion();
            
            JasperReport reporte =  (JasperReport) JRLoader.loadObject("existencias.jasper");
            Map parametro = new HashMap();
            
            parametro.put("nombre_cat", nombre_cat);
            
            JasperPrint j = JasperFillManager.fillReport(reporte, parametro, c.con);
            
            JasperViewer jv =   new JasperViewer(j,false);
            
            if(nombre_cat.equals("%")){
               nombre_cat = "Todas las Existencias" ;
            }
            
            jv.setTitle("Reporte Existencias - " + nombre_cat);
            jv.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar el reporte: " + e);
        }
    }
}
