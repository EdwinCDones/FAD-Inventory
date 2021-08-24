package com.fad.dao;

import com.fad.controller.ExistenciaJpaController;
import com.fad.controller.ProductoJpaController;
import com.fad.controller.TipordeninvJpaController;
import com.fad.entities.Existencia;
import com.fad.entities.Producto;
import com.fad.entities.Tipordeninv;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author GabuAndLemo
 */
public class productoDAO {

    private ProductoJpaController pjc = new ProductoJpaController();
    private ExistenciaJpaController ejc = new ExistenciaJpaController();
    private Producto producto = new Producto();
    private static List<Existencia> existencias = new ArrayList<Existencia>();

    /**
     * Funciones basicas CRUD*
     */
    public void insertar(String nombreP, String descripcionP, double valorP) {
        try {
            producto.setIdProducto(Integer.BYTES);
            producto.setNombrePro(nombreP);
            producto.setDescripcionPro(descripcionP);
            producto.setValorUnitPro(roundDecimal(valorP));
            pjc.create(producto);

            System.out.println("Se ha guardado con exito!!!");

            //tipordeninv = new Tipordeninv();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!: ");
            e.printStackTrace();
        }
    }

    public void modificar(int id, String nombreP, String descripcionP, double valorP) {
        try {
            System.out.println("Este es el id " + id);
            producto.setIdProducto(id);
            producto.setNombrePro(nombreP);
            producto.setDescripcionPro(descripcionP);
            producto.setValorUnitPro(valorP);
            existencias = buscarExistenciaByPro(id);

            pjc.edit(producto);

            modificarExistencias(id, valorP);

            System.out.println("Se ha modificado con exito!!!");

            //tipordeninv = new Tipordeninv();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!" + e);
            e.printStackTrace();
        }
    }

    public void modificarExistencias(int id, double valorP) {

        System.out.println("Esta son las existencias relacionadas : " + existencias.size());
        System.out.println("Producto " + producto.getNombrePro());

        for (Existencia e : existencias) {
            e.setValorTotalE(roundDecimal(valorP * e.getExistenciaActualE().intValue()));
            e.setIdProducto(producto);
            try {
                ejc.edit(e);
            } catch (Exception ex) {
                Logger.getLogger(productoDAO.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        }
    }

    public Boolean eliminar(int id) {
        try {
            existencias = buscarExistenciaByPro(id);
            if (existencias.size() == 0) {
                pjc.destroy(id);
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!");
            e.printStackTrace();
            return false;
        }
    }

    public void listarProductos(JTable tablaP, String nombreP) {
        DefaultTableModel model;
        String[] titulosP = {"Id", "Nombre", "Descripción", "V.U."};
        model = new DefaultTableModel(null, titulosP);
        List<Producto> productos = buscarProducto(nombreP);

        String[] datosP = new String[4];

        for (Producto p : productos) {
            datosP[0] = p.getIdProducto().toString();
            datosP[1] = p.getNombrePro();
            datosP[2] = p.getDescripcionPro();
            datosP[3] = p.getValorUnitPro().toString();
            model.addRow(datosP);
        }
        tablaP.setModel(model);
    }

    public Double roundDecimal(Double val) {
        return new BigDecimal(val.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     *
     * Consultas SQL
     */
    private List<Producto> buscarProducto(String nombreP) {

        Producto pro;
        EntityManager em = pjc.getEntityManager(); //
        Query sql = em.createQuery("SELECT p FROM Producto p WHERE p.nombrePro LIKE :nombreP");
        sql.setParameter("nombreP", nombreP + "%");
        List<Producto> lista = sql.getResultList();

        return lista;

    }

    private List<Existencia> buscarExistenciaByPro(int idProducto) {
        EntityManager em = pjc.getEntityManager(); //
        Query sql = em.createQuery("SELECT e FROM Existencia e WHERE e.idProducto.idProducto = :idProducto");
        sql.setParameter("idProducto", idProducto);
        List<Existencia> lista = sql.getResultList();

        return lista;
    }

    /**
     * Get and Set
     *
     * @return
     */
    public static List<Existencia> getExistencias() {
        return existencias;
    }

    public static void setExistencias(List<Existencia> existencias) {
        productoDAO.existencias = existencias;
    }

}
