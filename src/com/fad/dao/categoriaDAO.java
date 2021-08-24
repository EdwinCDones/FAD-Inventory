package com.fad.dao;

import com.fad.controller.CategoriaJpaController;
import com.fad.controller.TipordeninvJpaController;
import com.fad.entities.Categoria;
import com.fad.entities.Tipordeninv;
import com.fad.entities.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author GabuAndLemo
 */
public class categoriaDAO {

    private CategoriaJpaController cjc = new CategoriaJpaController();
    private Categoria categoria = new Categoria();

    /**
     * Funciones basicas CRUD*
     */
    public void insertar(String nombreC, String descripcionToiC, String siglaC) {
        try {
            categoria.setIdCategoria(Integer.BYTES);
            categoria.setNombreCat(nombreC);
            categoria.setDescripcionCat(descripcionToiC);
            categoria.setSiglaCat(siglaC);
            cjc.create(categoria);

            System.out.println("Se ha guardado con exito!!!");

            //tipordeninv = new Tipordeninv();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!: ");
            e.printStackTrace();
        }
    }

    public void modificar(int ids, String nombreC, String descripcionToiC, String siglaC) {
        try {
            categoria.setIdCategoria(ids);
            categoria.setNombreCat(nombreC);
            categoria.setDescripcionCat(descripcionToiC);
            categoria.setSiglaCat(siglaC);
            cjc.edit(categoria);

            System.out.println("Se ha modificado con exito!!!");

            //tipordeninv = new Tipordeninv();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!" + e);
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        try {
            cjc.destroy(id);
            System.out.println("Se ha eliminado correctamente");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!");
            e.printStackTrace();
        }
    }

    public void listarCategorias(JTable tablaP, String nombreCat) {
        DefaultTableModel model;
        String[] titulosC = {"Id", "Nombre", "Descripción", "Sigla"};
        model = new DefaultTableModel(null, titulosC);
        List<Categoria> categorias = buscarCategoria(nombreCat);

        String[] datosU = new String[4];

        for (Categoria u : categorias) {
            datosU[0] = u.getIdCategoria().toString();
            datosU[1] = u.getNombreCat();
            datosU[2] = u.getDescripcionCat();
            datosU[3] = u.getSiglaCat();

            model.addRow(datosU);
        }
        tablaP.setModel(model);
    }

    private List<Categoria> buscarCategoria(String nombreCat) {

        EntityManager em = cjc.getEntityManager(); //
        Query sql = em.createQuery("SELECT c FROM Categoria c WHERE c.nombreCat LIKE :nombreCat ORDER BY c.nombreCat");
        sql.setParameter("nombreCat", nombreCat + "%");
        List<Categoria> lista = sql.getResultList();

        return lista;

    }
}
