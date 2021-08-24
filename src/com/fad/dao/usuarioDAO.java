package com.fad.dao;

import com.fad.controller.CategoriaJpaController;
import com.fad.controller.UsuarioJpaController;
import com.fad.entities.Categoria;
import com.fad.entities.Producto;
import com.fad.entities.Usuario;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author GabuAndLemo
 */
public class usuarioDAO {

    private Usuario usuario = new Usuario();
    private UsuarioJpaController ujc = new UsuarioJpaController();
    private CategoriaJpaController cjc = new CategoriaJpaController();
    private static Usuario usuarioSession = new Usuario();

    /**
     * Funciones basicas CRUD*
     */
    public void insertar(String nombreU, String passwordU, int rol) {
        try {
            usuario.setIdUsuario(Integer.MIN_VALUE);
            usuario.setNombreUser(nombreU);
            usuario.setPasswordUser(passwordU);
            usuario.setRolUser(rol);
            ujc.create(usuario);
            System.out.println("Se ha guardado con exito!!!");

            //tipordeninv = new Tipordeninv();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!: ");
            e.printStackTrace();
        }
    }

    public void modificar(int id, String nombreU, String passwordU, int rol) {
        try {
            usuario.setIdUsuario(id);
            usuario.setNombreUser(nombreU);
            usuario.setPasswordUser(passwordU);
            usuario.setRolUser(rol);
            ujc.edit(usuario);
            System.out.println("Se ha modificado con exito!!!");

            //tipordeninv = new Tipordeninv();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!" + e);
            e.printStackTrace();
        }
    }

    public void listarUsuarios(JTable tablaP, String nombreUser) {
        DefaultTableModel model;
        String[] titulosU = {"Id", "Nombre", "Password", "Rol"};
        model = new DefaultTableModel(null, titulosU);
        List<Usuario> usuarios = buscarUsuario(nombreUser);

        String[] datosU = new String[4];

        for (Usuario u : usuarios) {
            datosU[0] = u.getIdUsuario().toString();
            datosU[1] = u.getNombreUser();
            datosU[2] = u.getPasswordUser();
            datosU[3] = categoria(u.getRolUser());

            model.addRow(datosU);
        }
        tablaP.setModel(model);
    }

    public void eliminar(int id) {
        try {
            ujc.destroy(id);
            System.out.println("Se ha eliminado correctamente");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error!!!");
            e.printStackTrace();
        }
    }

    public String categoria(int id) {
        String nombre = "";

        Categoria c = new Categoria();

        c = findCategoriaById(id);

        if (c == null) {
            nombre = "N/A";
        } else {
            nombre = c.getNombreCat();
        }

        return nombre;
    }
    
    /**
     * Consultas
     *
     * @param user
     * @return
     */
    private List<Usuario> buscarUsuario(String user) {

        Usuario u;
        EntityManager em = ujc.getEntityManager(); //
        Query sql = em.createQuery("SELECT u FROM Usuario u WHERE u.nombreUser LIKE :user ORDER BY u.nombreUser");
        sql.setParameter("user", user + "%");
        List<Usuario> lista = sql.getResultList();

        return lista;

    }

    public boolean login(String nombreUser, String passwordUser) {

        EntityManager em = ujc.getEntityManager();

        boolean valor;
        try {

            Query sql = em.createQuery("SELECT u.nombreUser, u.passwordUser FROM Usuario u WHERE u.nombreUser = :nombreUser AND u.passwordUser = :passwordUser");
            sql.setParameter("nombreUser", nombreUser);
            sql.setParameter("passwordUser", passwordUser);
            //sql.setParameter("rol", rolUser);

            List resultado = sql.getResultList();

            if (!resultado.isEmpty()) {
                valor = true;
                setUsuarioSession(findUsuarioById(nombreUser));

            } else {
                valor = false;

            }

        } catch (Exception e) {
            valor = false;

        }
        return valor;
    }

    public void getRolCmb(JComboBox<Categoria> cmbCategoria) {
        EntityManager em = ujc.getEntityManager(); //
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

    public Categoria findCategoriaById(int id) {

        EntityManager em = ujc.getEntityManager(); //
        Query sql = em.createQuery("SELECT c FROM Categoria c WHERE c.idCategoria = :idCategoria");
        sql.setParameter("idCategoria", id);
        sql.setMaxResults(1);
        try {
            Categoria categoria = (Categoria) sql.getSingleResult();
            return categoria;
        } catch (NoResultException nre) {
            return null;
        } catch (NonUniqueResultException nure) {
            return null;
        }

    }
    
    public Usuario findUsuarioById(String user) {

        EntityManager em = ujc.getEntityManager(); //
        Query sql = em.createQuery("SELECT u FROM Usuario u WHERE u.nombreUser = :nombreUser");
        sql.setParameter("nombreUser", user);
        sql.setMaxResults(1);
        try {
            Usuario usuario = (Usuario) sql.getSingleResult();
            return usuario;
        } catch (NoResultException nre) {
            return null;
        } catch (NonUniqueResultException nure) {
            return null;
        }

    }

    public static Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public static void setUsuarioSession(Usuario usuarioSession) {
        usuarioDAO.usuarioSession = usuarioSession;
    }
    
}
