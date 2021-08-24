package com.fad.dao;

import com.fad.controller.TipordeninvJpaController;
import com.fad.entities.Tipordeninv;

/**
 *
 * @author GabuAndLemo
 */
public class tipordeninvDAO {
    
    private TipordeninvJpaController tjc = new TipordeninvJpaController();
    private Tipordeninv tipordeninv = new Tipordeninv();
    
    /**Funciones basicas CRUD**/
    public void instertar(String nombreToi, String descripcionToi) {
        try {
            tipordeninv.setIdTipordeninv(Integer.BYTES);
            tipordeninv.setNombreToi(nombreToi);
            tipordeninv.setDescripcionToi(descripcionToi);
            tjc.create(tipordeninv);
            
            System.out.println("Se ha guardado con exito!!!");
            
            //tipordeninv = new Tipordeninv();
            
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!: ");
            e.printStackTrace();
        }
    }
    
    public void modificar(int ids, String nombreToi, String descripcionToi) {
        try {
            tipordeninv.setIdTipordeninv(Integer.valueOf(ids));
            tipordeninv.setNombreToi(nombreToi);
            tipordeninv.setDescripcionToi(descripcionToi);
            tjc.edit(tipordeninv);
            
            System.out.println("Se ha modificado con exito!!!");
            
            //tipordeninv = new Tipordeninv();
            
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!" + e);
            e.printStackTrace();
        }
    }
    
    public void eliminar(int id) {
        try {
            tjc.destroy(id);
            System.out.println("Se ha eliminado correctamente");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!");
            e.printStackTrace();
        }
    }
}
