/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fad.controller;

import com.fad.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.fad.entities.Ordeninventario;
import com.fad.entities.Tipordeninv;
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
public class TipordeninvJpaController implements Serializable {

    public TipordeninvJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FADPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TipordeninvJpaController() {
    }
    
    public void create(Tipordeninv tipordeninv) {
        if (tipordeninv.getOrdeninventarioCollection() == null) {
            tipordeninv.setOrdeninventarioCollection(new ArrayList<Ordeninventario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Ordeninventario> attachedOrdeninventarioCollection = new ArrayList<Ordeninventario>();
            for (Ordeninventario ordeninventarioCollectionOrdeninventarioToAttach : tipordeninv.getOrdeninventarioCollection()) {
                ordeninventarioCollectionOrdeninventarioToAttach = em.getReference(ordeninventarioCollectionOrdeninventarioToAttach.getClass(), ordeninventarioCollectionOrdeninventarioToAttach.getIdOrdeninventario());
                attachedOrdeninventarioCollection.add(ordeninventarioCollectionOrdeninventarioToAttach);
            }
            tipordeninv.setOrdeninventarioCollection(attachedOrdeninventarioCollection);
            em.persist(tipordeninv);
            for (Ordeninventario ordeninventarioCollectionOrdeninventario : tipordeninv.getOrdeninventarioCollection()) {
                Tipordeninv oldIdTipordeninvOfOrdeninventarioCollectionOrdeninventario = ordeninventarioCollectionOrdeninventario.getIdTipordeninv();
                ordeninventarioCollectionOrdeninventario.setIdTipordeninv(tipordeninv);
                ordeninventarioCollectionOrdeninventario = em.merge(ordeninventarioCollectionOrdeninventario);
                if (oldIdTipordeninvOfOrdeninventarioCollectionOrdeninventario != null) {
                    oldIdTipordeninvOfOrdeninventarioCollectionOrdeninventario.getOrdeninventarioCollection().remove(ordeninventarioCollectionOrdeninventario);
                    oldIdTipordeninvOfOrdeninventarioCollectionOrdeninventario = em.merge(oldIdTipordeninvOfOrdeninventarioCollectionOrdeninventario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipordeninv tipordeninv) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipordeninv persistentTipordeninv = em.find(Tipordeninv.class, tipordeninv.getIdTipordeninv());
            Collection<Ordeninventario> ordeninventarioCollectionOld = persistentTipordeninv.getOrdeninventarioCollection();
            Collection<Ordeninventario> ordeninventarioCollectionNew = tipordeninv.getOrdeninventarioCollection();
            Collection<Ordeninventario> attachedOrdeninventarioCollectionNew = new ArrayList<Ordeninventario>();
            /*for (Ordeninventario ordeninventarioCollectionNewOrdeninventarioToAttach : ordeninventarioCollectionNew) {
                ordeninventarioCollectionNewOrdeninventarioToAttach = em.getReference(ordeninventarioCollectionNewOrdeninventarioToAttach.getClass(), ordeninventarioCollectionNewOrdeninventarioToAttach.getIdOrdeninventario());
                attachedOrdeninventarioCollectionNew.add(ordeninventarioCollectionNewOrdeninventarioToAttach);
            }*/
            ordeninventarioCollectionNew = attachedOrdeninventarioCollectionNew;
            tipordeninv.setOrdeninventarioCollection(ordeninventarioCollectionNew);
            tipordeninv = em.merge(tipordeninv);
            for (Ordeninventario ordeninventarioCollectionOldOrdeninventario : ordeninventarioCollectionOld) {
                if (!ordeninventarioCollectionNew.contains(ordeninventarioCollectionOldOrdeninventario)) {
                    ordeninventarioCollectionOldOrdeninventario.setIdTipordeninv(null);
                    ordeninventarioCollectionOldOrdeninventario = em.merge(ordeninventarioCollectionOldOrdeninventario);
                }
            }
            for (Ordeninventario ordeninventarioCollectionNewOrdeninventario : ordeninventarioCollectionNew) {
                if (!ordeninventarioCollectionOld.contains(ordeninventarioCollectionNewOrdeninventario)) {
                    Tipordeninv oldIdTipordeninvOfOrdeninventarioCollectionNewOrdeninventario = ordeninventarioCollectionNewOrdeninventario.getIdTipordeninv();
                    ordeninventarioCollectionNewOrdeninventario.setIdTipordeninv(tipordeninv);
                    ordeninventarioCollectionNewOrdeninventario = em.merge(ordeninventarioCollectionNewOrdeninventario);
                    if (oldIdTipordeninvOfOrdeninventarioCollectionNewOrdeninventario != null && !oldIdTipordeninvOfOrdeninventarioCollectionNewOrdeninventario.equals(tipordeninv)) {
                        oldIdTipordeninvOfOrdeninventarioCollectionNewOrdeninventario.getOrdeninventarioCollection().remove(ordeninventarioCollectionNewOrdeninventario);
                        oldIdTipordeninvOfOrdeninventarioCollectionNewOrdeninventario = em.merge(oldIdTipordeninvOfOrdeninventarioCollectionNewOrdeninventario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipordeninv.getIdTipordeninv();
                if (findTipordeninv(id) == null) {
                    throw new NonexistentEntityException("The tipordeninv with id " + id + " no longer exists.");
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
            Tipordeninv tipordeninv;
            try {
                tipordeninv = em.getReference(Tipordeninv.class, id);
                tipordeninv.getIdTipordeninv();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipordeninv with id " + id + " no longer exists.", enfe);
            }
            Collection<Ordeninventario> ordeninventarioCollection = tipordeninv.getOrdeninventarioCollection();
            for (Ordeninventario ordeninventarioCollectionOrdeninventario : ordeninventarioCollection) {
                ordeninventarioCollectionOrdeninventario.setIdTipordeninv(null);
                ordeninventarioCollectionOrdeninventario = em.merge(ordeninventarioCollectionOrdeninventario);
            }
            em.remove(tipordeninv);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipordeninv> findTipordeninvEntities() {
        return findTipordeninvEntities(true, -1, -1);
    }

    public List<Tipordeninv> findTipordeninvEntities(int maxResults, int firstResult) {
        return findTipordeninvEntities(false, maxResults, firstResult);
    }

    private List<Tipordeninv> findTipordeninvEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipordeninv.class));
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

    public Tipordeninv findTipordeninv(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipordeninv.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipordeninvCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipordeninv> rt = cq.from(Tipordeninv.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
