package com.fad.dao;

import com.fad.controller.ExistenciaJpaController;
import com.fad.entities.Categoria;
import com.fad.entities.Existencia;
import com.fad.entities.Producto;
import com.fad.entities.Usuario;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import sun.rmi.transport.proxy.CGIHandler;

/**
 *
 * @author GabuAndLemo
 */
public class existenciaDAO {

    private ExistenciaJpaController ejc = new ExistenciaJpaController();
    private Existencia existencia = new Existencia();
    private Producto producto = new Producto();
    private Categoria categoria = new Categoria();
    private static List<Existencia> existencias = new ArrayList<>();

    /**
     * Funciones basicas CRUD*
     */
    public boolean insertar(int idPro, int idCat, int existenciaIni, int existenciaAct, double valorTotal) {
        try {
            existencias = buscarExistenciaByPro(idPro, idCat);

            if (existencias.size() == 0) {
                producto = buscarProducto(idPro);
                categoria = buscarCategoria(idCat);

                existencia.setIdProducto(producto);
                existencia.setIdCategoria(categoria);
                existencia.setIdExistencia(obtenerId(categoria));
                existencia.setExistenciaActualE(BigInteger.valueOf(existenciaAct));
                existencia.setExistenciaIniE(BigInteger.valueOf(existenciaIni));
                existencia.setValorTotalE(roundDecimal(valorTotal));

                ejc.create(existencia);

                System.out.println("Se ha guardado con exito!!!");

                return true;
            } else {
                return false;
            }

            //tipordeninv = new Tipordeninv();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!: ");
            e.printStackTrace();
            return false;
        }
    }

    public void modificar(String id, int existenciaIni, int existenciaAct, double valorTotal) {
        try {

            existencia = buscarExistencia(id);

            existencia.setExistenciaActualE(BigInteger.valueOf(existenciaAct));
            existencia.setExistenciaIniE(BigInteger.valueOf(existenciaIni));
            existencia.setValorTotalE(valorTotal);

            ejc.edit(existencia);

            System.out.println("Se ha modificado con exito!!!");

            //tipordeninv = new Tipordeninv();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!" + e);
            e.printStackTrace();
        }
    }

    public void eliminar(String id) {
        try {
            ejc.destroy(id);
            System.out.println("Se ha eliminado correctamente");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!");
            e.printStackTrace();
        }
    }

    public String obtenerId(Categoria c) {

        String codigoAux = maxIdExistencia(c.getSiglaCat());
        String estructura;

        if (codigoAux == null) {
            if (c.getIdCategoria() < 10) {
                codigoAux = ("FAD." + c.getSiglaCat() + ".0" + c.getIdCategoria() + ".000");
            } else {
                codigoAux = ("FAD." + c.getSiglaCat() + "." + c.getIdCategoria() + ".000");
            }
        } else {
            if (c.getIdCategoria() < 10) {
                estructura = ("FAD." + c.getSiglaCat() + ".0" + c.getIdCategoria() + ".");
            } else {
                estructura = ("FAD." + c.getSiglaCat() + "." + c.getIdCategoria() + ".");
            }

            System.out.println("Este es el id que trae: " + codigoAux);

            String separador = Pattern.quote(".");
            String[] partsId = codigoAux.split(separador);
            System.out.println("Este es el id que trae: " + partsId);
            String codigoCortado = partsId[3];

            System.out.println("Este es el id convertido en numero: " + codigoCortado);

            Integer codigo = Integer.parseInt(codigoCortado);
            codigo = codigo + 1;

            System.out.println("Este es el id oficial: " + codigo);

            String codigoEx = codigo.toString();

            switch (codigoEx.length()) {
                case 1:
                    codigoAux = (estructura + "00" + codigoEx);
                    break;
                case 2:
                    codigoAux = (estructura + "0" + codigoEx);
                    break;
                case 3:
                    codigoAux = (estructura + codigoEx);
                    break;

                default:
                    break;
            }

            System.out.println("Este es el id: " + codigoAux);

        }

        return codigoAux;
    }
    
    public Double roundDecimal(Double val) {
        return new BigDecimal(val.toString()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Consultas*
     */
    public void listarExistencias(JTable tablaE, String producto) {
        DefaultTableModel model;
        String[] titulosU = {"Id", "Producto", "Descripcion", "Categoría", "Existencia Inicial", "Existencia Actual", "V/U", "V/T"};
        model = new DefaultTableModel(null, titulosU);
        List<Existencia> existencias = buscarExistencias(producto);

        String[] datosE = new String[8];

        for (Existencia e : existencias) {
            datosE[0] = e.getIdExistencia();
            datosE[1] = e.getIdProducto().getNombrePro();
            datosE[2] = e.getIdProducto().getDescripcionPro();
            datosE[3] = e.getIdCategoria().getNombreCat();
            datosE[4] = e.getExistenciaIniE().toString();
            datosE[5] = e.getExistenciaActualE().toString();
            datosE[6] = e.getIdProducto().getValorUnitPro().toString();
            datosE[7] = e.getValorTotalE().toString();

            model.addRow(datosE);
        }
        tablaE.setModel(model);
    }
    
    public void listarExistenciasReporte(JTable tablaE, Categoria c) {
        DefaultTableModel model;
        String[] titulosU = {"Id", "Producto", "Descripcion", "Categoría", "Existencia Inicial", "Existencia Actual", "V/U", "V/T"};
        model = new DefaultTableModel(null, titulosU);
        List<Existencia> existencias = buscarExistenciasReport(c.getNombreCat());

        String[] datosE = new String[8];

        for (Existencia e : existencias) {
            datosE[0] = e.getIdExistencia();
            datosE[1] = e.getIdProducto().getNombrePro();
            datosE[2] = e.getIdProducto().getDescripcionPro();
            datosE[3] = e.getIdCategoria().getNombreCat();
            datosE[4] = e.getExistenciaIniE().toString();
            datosE[5] = e.getExistenciaActualE().toString();
            datosE[6] = e.getIdProducto().getValorUnitPro().toString();
            datosE[7] = e.getValorTotalE().toString();

            model.addRow(datosE);
        }
        tablaE.setModel(model);
    }

    private List<Existencia> buscarExistencias(String producto) {

        EntityManager em = ejc.getEntityManager(); //
        Query sql = em.createQuery("SELECT e FROM Existencia e WHERE e.idProducto.nombrePro LIKE :pro ORDER BY e.idProducto.nombrePro");
        sql.setParameter("pro", producto + "%");
        List<Existencia> lista = sql.getResultList();

        return lista;

    }
    
    private List<Existencia> buscarExistenciasReport(String nombreCat) {
        
        if(nombreCat.equals("Todo")){
            nombreCat = "";
        }

        EntityManager em = ejc.getEntityManager(); //
        Query sql = em.createQuery("SELECT e FROM Existencia e WHERE e.idCategoria.nombreCat LIKE :nombreCat ORDER BY e.idCategoria.idCategoria, e.idExistencia");
        sql.setParameter("nombreCat", nombreCat + "%");
        List<Existencia> lista = sql.getResultList();

        return lista;

    }

    private List<Existencia> buscarExistenciaByPro(int idProducto, int idCategoria) {
        EntityManager em = ejc.getEntityManager(); //
        Query sql = em.createQuery("SELECT e FROM Existencia e WHERE e.idProducto.idProducto = :idProducto AND e.idCategoria.idCategoria = :idCategoria");
        sql.setParameter("idProducto", idProducto);
        sql.setParameter("idCategoria", idCategoria);
        List<Existencia> lista = sql.getResultList();

        return lista;
    }

    public Producto buscarProducto(int id) {

        EntityManager em = ejc.getEntityManager(); //
        Query sql = em.createQuery("SELECT p FROM Producto p WHERE p.idProducto = :idPro");
        sql.setParameter("idPro", id);
        Producto p = (Producto) sql.getSingleResult();

        return p;

    }

    private Categoria buscarCategoria(int id) {

        EntityManager em = ejc.getEntityManager(); //
        Query sql = em.createQuery("SELECT c FROM Categoria c WHERE c.idCategoria = :idCategoria");
        sql.setParameter("idCategoria", id);
        Categoria c = (Categoria) sql.getSingleResult();

        return c;

    }

    private Existencia buscarExistencia(String id) {

        EntityManager em = ejc.getEntityManager(); //
        Query sql = em.createQuery("SELECT e FROM Existencia e WHERE e.idExistencia = :idExistencia");
        sql.setParameter("idExistencia", id);
        Existencia e = (Existencia) sql.getSingleResult();

        return e;

    }

    public void getRolCmb(JComboBox<Categoria> cmbCategoria) {
        EntityManager em = ejc.getEntityManager(); //
        Iterator it = em.createQuery("SELECT c FROM Categoria c").getResultList().iterator();
        Categoria categoria;
        try {
            while (it.hasNext()) {
                categoria = (Categoria) it.next();
                cmbCategoria.addItem(categoria);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("No pudo cargar el combo");
        }

    }
    
    public void getRolCmbReport(JComboBox<Categoria> cmbCategoria) {
        EntityManager em = ejc.getEntityManager(); //
        Iterator it = em.createQuery("SELECT c FROM Categoria c").getResultList().iterator();
        Categoria categoria;
        
        Categoria c = new Categoria();
        c.setIdCategoria(Integer.valueOf(0));
        c.setNombreCat("Todo");
        c.setDescripcionCat("Trae todo");
        cmbCategoria.addItem(c);
        
        try {
            while (it.hasNext()) {
                categoria = (Categoria) it.next();
                cmbCategoria.addItem(categoria);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("No pudo cargar el combo");
        }

    }

    public String maxIdExistencia(String id) {

        EntityManager em = ejc.getEntityManager();

        StringBuilder queryString = new StringBuilder(
                "SELECT max(e.idExistencia) FROM Existencia e where e.idExistencia like '%" + id + "%'"
        );

        Query query = em.createQuery(queryString.toString());

        try {
            String idExistencia = (String) query.getSingleResult();
            return idExistencia;
        } catch (NoResultException nre) {
            return null;
        } catch (NonUniqueResultException nure) {
            return null;
        }
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public static List<Existencia> getExistencias() {
        return existencias;
    }

    public static void setExistencias(List<Existencia> existencias) {
        existenciaDAO.existencias = existencias;
    }

}
