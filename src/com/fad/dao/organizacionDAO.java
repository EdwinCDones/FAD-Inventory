package com.fad.dao;

import com.fad.controller.OrganizacionJpaController;
import com.fad.entities.Organizacion;


/**
 *
 * @author GabuAndLemo
 */
public class organizacionDAO {

    private OrganizacionJpaController ojc = new OrganizacionJpaController();
    private Organizacion organizacion = new Organizacion();

    /**
     * Funciones basicas CRUD*
     */
    public void insertar(
            String nombreO,
            String descripcionO,
            String telefono1,
            String telefono2,
            String celularO,
            String emailO,
            String rucO,
            String direccionO
    ) {
        try {
            organizacion.setIdOrganizacion(Integer.BYTES);
            organizacion.setNombreOrg(nombreO);
            organizacion.setDescripcionOrg(descripcionO);
            organizacion.setTelefono1Org(telefono1);
            organizacion.setTelefono2Org(telefono2);
            organizacion.setCelularOrg(celularO);
            organizacion.setEmailOrg(emailO);
            organizacion.setRucOrg(rucO);
            organizacion.setDireccionOrg(direccionO);
            ojc.create(organizacion);

            System.out.println("Se ha guardado con exito!!!");

            //tipordeninv = new Tipordeninv();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!: ");
            e.printStackTrace();
        }
    }

    public void modificar(
            int id,
            String nombreO,
            String descripcionO,
            String telefono1,
            String telefono2,
            String celularO,
            String emailO,
            String rucO,
            String direccionO
            ) {
        try {
            organizacion.setIdOrganizacion(id);
            organizacion.setNombreOrg(nombreO);
            organizacion.setDescripcionOrg(descripcionO);
            organizacion.setTelefono1Org(telefono1);
            organizacion.setTelefono2Org(telefono2);
            organizacion.setCelularOrg(celularO);
            organizacion.setEmailOrg(emailO);
            organizacion.setRucOrg(rucO);
            organizacion.setDireccionOrg(direccionO);
            ojc.edit(organizacion);

            System.out.println("Se ha modificado con exito!!!");

            //tipordeninv = new Tipordeninv();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!" + e);
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        try {
            ojc.destroy(id);
            System.out.println("Se ha eliminado correctamente");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!");
            e.printStackTrace();
        }
    }
}
