/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fad.reportes;

/**
 *
 * @author scajasc
 */
public class Pruebas {
    
    public static Conexion hc;
    
    public static void main(String[] args){
        try {
            //hc =  new Conexion();
            generarReportes g = new generarReportes();
            g.reporteExistencia("%");
        } catch (Exception e) {
            
        }
    }
}
