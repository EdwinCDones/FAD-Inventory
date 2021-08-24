/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fad.controller;

import com.fad.controller.exceptions.NonexistentEntityException;
import com.fad.controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.fad.entities.Categoria;
import com.fad.entities.Existencia;
import com.fad.entities.Producto;
import com.fad.entities.Movimientoinventario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author GabuAndLemo
 */
public class ExistenciaJpaController implements Serializable {

    public ExistenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FADPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ExistenciaJpaController() {
    }
    
    public void create(Existencia existencia) throws PreexistingEntityException, Exception {
        if (existencia.getMovimientoinventarioCollection() == null) {
            existencia.setMovimientoinventarioCollection(new ArrayList<Movimientoinventario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria idCategoria = existencia.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getIdCategoria());
                existencia.setIdCategoria(idCategoria);
            }
            Producto idProducto = existencia.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                existencia.setIdProducto(idProducto);
            }
            Collection<Movimientoinventario> attachedMovimientoinventarioCollection = new ArrayList<Movimientoinventario>();
            for (Movimientoinventario movimientoinventarioCollectionMovimientoinventarioToAttach : existencia.getMovimientoinventarioCollection()) {
                movimientoinventarioCollectionMovimientoinventarioToAttach = em.getReference(movimientoinventarioCollectionMovimientoinventarioToAttach.getClass(), movimientoinventarioCollectionMovimientoinventarioToAttach.getIdMovimiento());
                attachedMovimientoinventarioCollection.add(movimientoinventarioCollectionMovimientoinventarioToAttach);
            }
            existencia.setMovimientoinventarioCollection(attachedMovimientoinventarioCollection);
            em.persist(existencia);
            if (idCategoria != null) {
                idCategoria.getExistenciaCollection().add(existencia);
                idCategoria = em.merge(idCategoria);
            }
            if (idProducto != null) {
                idProducto.getExistenciaCollection().add(existencia);
                idProducto = em.merge(idProducto);
            }
            for (Movimientoinventario movimientoinventarioCollectionMovimientoinventario : existencia.getMovimientoinventarioCollection()) {
                Existencia oldIdExistenciaOfMovimientoinventarioCollectionMovimientoinventario = movimientoinventarioCollectionMovimientoinventario.getIdExistencia();
                movimientoinventarioCollectionMovimientoinventario.setIdExistencia(existencia);
                movimientoinventarioCollectionMovimientoinventario = em.merge(movimientoinventarioCollectionMovimientoinventario);
                if (oldIdExistenciaOfMovimientoinventarioCollectionMovimientoinventario != null) {
                    oldIdExistenciaOfMovimientoinventarioCollectionMovimientoinventario.getMovimientoinventarioCollection().remove(movimientoinventarioCollectionMovimientoinventario);
                    oldIdExistenciaOfMovimientoinventarioCollectionMovimientoinventario = em.merge(oldIdExistenciaOfMovimientoinventarioCollectionMovimientoinventario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findExistencia(existencia.getIdExistencia()) != null) {
                throw new PreexistingEntityException("Existencia " + existencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Existencia existencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Existencia persistentExistencia = em.find(Existencia.class, existencia.getIdExistencia());
            Categoria idCategoriaOld = persistentExistencia.getIdCategoria();
            Categoria idCategoriaNew = existencia.getIdCategoria();
            Producto idProductoOld = persistentExistencia.getIdProducto();
            Producto idProductoNew = existencia.getIdProducto();
            Collection<Movimientoinventario> movimientoinventarioCollectionOld = persistentExistencia.getMovimientoinventarioCollection();
            Collection<Movimientoinventario> movimientoinventarioCollectionNew = existencia.getMovimientoinventarioCollection();
            if (idCategoriaNew != null) {
                idCategoriaNew = em.getReference(idCategoriaNew.getClass(), idCategoriaNew.getIdCategoria());
                existencia.setIdCategoria(idCategoriaNew);
            }
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                existencia.setIdProducto(idProductoNew);
            }
            Collection<Movimientoinventario> attachedMovimientoinventarioCollectionNew = new ArrayList<Movimientoinventario>();
            for (Movimientoinventario movimientoinventarioCollectionNewMovimientoinventarioToAttach : movimientoinventarioCollectionNew) {
                movimientoinventarioCollectionNewMovimientoinventarioToAttach = em.getReference(movimientoinventarioCollectionNewMovimientoinventarioToAttach.getClass(), movimientoinventarioCollectionNewMovimientoinventarioToAttach.getIdMovimiento());
                attachedMovimientoinventarioCollectionNew.add(movimientoinventarioCollectionNewMovimientoinventarioToAttach);
            }
            movimientoinventarioCollectionNew = attachedMovimientoinventarioCollectionNew;
            existencia.setMovimientoinventarioCollection(movimientoinventarioCollectionNew);
            existencia = em.merge(existencia);
            if (idCategoriaOld != null && !idCategoriaOld.equals(idCategoriaNew)) {
                idCategoriaOld.getExistenciaCollection().remove(existencia);
                idCategoriaOld = em.merge(idCategoriaOld);
            }
            if (idCategoriaNew != null && !idCategoriaNew.equals(idCategoriaOld)) {
                idCategoriaNew.getExistenciaCollection().add(existencia);
                idCategoriaNew = em.merge(idCategoriaNew);
            }
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getExistenciaCollection().remove(existencia);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getExistenciaCollection().add(existencia);
                idProductoNew = em.merge(idProductoNew);
            }
            for (Movimientoinventario movimientoinventarioCollectionOldMovimientoinventario : movimientoinventarioCollectionOld) {
                if (!movimientoinventarioCollectionNew.contains(movimientoinventarioCollectionOldMovimientoinventario)) {
                    movimientoinventarioCollectionOldMovimientoinventario.setIdExistencia(null);
                    movimientoinventarioCollectionOldMovimientoinventario = em.merge(movimientoinventarioCollectionOldMovimientoinventario);
                }
            }
            for (Movimientoinventario movimientoinventarioCollectionNewMovimientoinventario : movimientoinventarioCollectionNew) {
                if (!movimientoinventarioCollectionOld.contains(movimientoinventarioCollectionNewMovimientoinventario)) {
                    Existencia oldIdExistenciaOfMovimientoinventarioCollectionNewMovimientoinventario = movimientoinventarioCollectionNewMovimientoinventario.getIdExistencia();
                    movimientoinventarioCollectionNewMovimientoinventario.setIdExistencia(existencia);
                    movimientoinventarioCollectionNewMovimientoinventario = em.merge(movimientoinventarioCollectionNewMovimientoinventario);
                    if (oldIdExistenciaOfMovimientoinventarioCollectionNewMovimientoinventario != null && !oldIdExistenciaOfMovimientoinventarioCollectionNewMovimientoinventario.equals(existencia)) {
                        oldIdExistenciaOfMovimientoinventarioCollectionNewMovimientoinventario.getMovimientoinventarioCollection().remove(movimientoinventarioCollectionNewMovimientoinventario);
                        oldIdExistenciaOfMovimientoinventarioCollectionNewMovimientoinventario = em.merge(oldIdExistenciaOfMovimientoinventarioCollectionNewMovimientoinventario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = existencia.getIdExistencia();
                if (findExistencia(id) == null) {
                    throw new NonexistentEntityException("The existencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Existencia existencia;
            try {
                existencia = em.getReference(Existencia.class, id);
                existencia.getIdExistencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The existencia with id " + id + " no longer exists.", enfe);
            }
            Categoria idCategoria = existencia.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getExistenciaCollection().remove(existencia);
                idCategoria = em.merge(idCategoria);
            }
            Producto idProducto = existencia.getIdProducto();
            if (idProducto != null) {
                idProducto.getExistenciaCollection().remove(existencia);
                idProducto = em.merge(idProducto);
            }
            Collection<Movimientoinventario> movimientoinventarioCollection = existencia.getMovimientoinventarioCollection();
            for (Movimientoinventario movimientoinventarioCollectionMovimientoinventario : movimientoinventarioCollection) {
                movimientoinventarioCollectionMovimientoinventario.setIdExistencia(null);
                movimientoinventarioCollectionMovimientoinventario = em.merge(movimientoinventarioCollectionMovimientoinventario);
            }
            em.remove(existencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Existencia> findExistenciaEntities() {
        return findExistenciaEntities(true, -1, -1);
    }

    public List<Existencia> findExistenciaEntities(int maxResults, int firstResult) {
        return findExistenciaEntities(false, maxResults, firstResult);
    }

    private List<Existencia> findExistenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Existencia.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Existencia findExistencia(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Existencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getExistenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Existencia> rt = cq.from(Existencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
