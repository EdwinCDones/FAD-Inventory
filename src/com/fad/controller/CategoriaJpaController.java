/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fad.controller;

import com.fad.controller.exceptions.NonexistentEntityException;
import com.fad.entities.Categoria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.fad.entities.Existencia;
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
public class CategoriaJpaController implements Serializable {

    public CategoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FADPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CategoriaJpaController() {
    }
    
    

    public void create(Categoria categoria) {
        if (categoria.getExistenciaCollection() == null) {
            categoria.setExistenciaCollection(new ArrayList<Existencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Existencia> attachedExistenciaCollection = new ArrayList<Existencia>();
            for (Existencia existenciaCollectionExistenciaToAttach : categoria.getExistenciaCollection()) {
                existenciaCollectionExistenciaToAttach = em.getReference(existenciaCollectionExistenciaToAttach.getClass(), existenciaCollectionExistenciaToAttach.getIdExistencia());
                attachedExistenciaCollection.add(existenciaCollectionExistenciaToAttach);
            }
            categoria.setExistenciaCollection(attachedExistenciaCollection);
            em.persist(categoria);
            for (Existencia existenciaCollectionExistencia : categoria.getExistenciaCollection()) {
                Categoria oldIdCategoriaOfExistenciaCollectionExistencia = existenciaCollectionExistencia.getIdCategoria();
                existenciaCollectionExistencia.setIdCategoria(categoria);
                existenciaCollectionExistencia = em.merge(existenciaCollectionExistencia);
                if (oldIdCategoriaOfExistenciaCollectionExistencia != null) {
                    oldIdCategoriaOfExistenciaCollectionExistencia.getExistenciaCollection().remove(existenciaCollectionExistencia);
                    oldIdCategoriaOfExistenciaCollectionExistencia = em.merge(oldIdCategoriaOfExistenciaCollectionExistencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoria categoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria persistentCategoria = em.find(Categoria.class, categoria.getIdCategoria());
            Collection<Existencia> existenciaCollectionOld = persistentCategoria.getExistenciaCollection();
            Collection<Existencia> existenciaCollectionNew = categoria.getExistenciaCollection();
            Collection<Existencia> attachedExistenciaCollectionNew = new ArrayList<Existencia>();
            /*for (Existencia existenciaCollectionNewExistenciaToAttach : existenciaCollectionNew) {
                existenciaCollectionNewExistenciaToAttach = em.getReference(existenciaCollectionNewExistenciaToAttach.getClass(), existenciaCollectionNewExistenciaToAttach.getIdExistencia());
                attachedExistenciaCollectionNew.add(existenciaCollectionNewExistenciaToAttach);
            }*/
            existenciaCollectionNew = attachedExistenciaCollectionNew;
            categoria.setExistenciaCollection(existenciaCollectionNew);
            categoria = em.merge(categoria);
            for (Existencia existenciaCollectionOldExistencia : existenciaCollectionOld) {
                if (!existenciaCollectionNew.contains(existenciaCollectionOldExistencia)) {
                    existenciaCollectionOldExistencia.setIdCategoria(null);
                    existenciaCollectionOldExistencia = em.merge(existenciaCollectionOldExistencia);
                }
            }
            for (Existencia existenciaCollectionNewExistencia : existenciaCollectionNew) {
                if (!existenciaCollectionOld.contains(existenciaCollectionNewExistencia)) {
                    Categoria oldIdCategoriaOfExistenciaCollectionNewExistencia = existenciaCollectionNewExistencia.getIdCategoria();
                    existenciaCollectionNewExistencia.setIdCategoria(categoria);
                    existenciaCollectionNewExistencia = em.merge(existenciaCollectionNewExistencia);
                    if (oldIdCategoriaOfExistenciaCollectionNewExistencia != null && !oldIdCategoriaOfExistenciaCollectionNewExistencia.equals(categoria)) {
                        oldIdCategoriaOfExistenciaCollectionNewExistencia.getExistenciaCollection().remove(existenciaCollectionNewExistencia);
                        oldIdCategoriaOfExistenciaCollectionNewExistencia = em.merge(oldIdCategoriaOfExistenciaCollectionNewExistencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoria.getIdCategoria();
                if (findCategoria(id) == null) {
                    throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoria;
            try {
                categoria = em.getReference(Categoria.class, id);
                categoria.getIdCategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.", enfe);
            }
            Collection<Existencia> existenciaCollection = categoria.getExistenciaCollection();
            for (Existencia existenciaCollectionExistencia : existenciaCollection) {
                existenciaCollectionExistencia.setIdCategoria(null);
                existenciaCollectionExistencia = em.merge(existenciaCollectionExistencia);
            }
            em.remove(categoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoria> findCategoriaEntities() {
        return findCategoriaEntities(true, -1, -1);
    }

    public List<Categoria> findCategoriaEntities(int maxResults, int firstResult) {
        return findCategoriaEntities(false, maxResults, firstResult);
    }

    private List<Categoria> findCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoria.class));
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

    public Categoria findCategoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoria> rt = cq.from(Categoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
