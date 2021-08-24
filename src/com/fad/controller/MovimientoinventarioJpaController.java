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
import com.fad.entities.Existencia;
import com.fad.entities.Movimientoinventario;
import com.fad.entities.Ordeninventario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author GabuAndLemo
 */
public class MovimientoinventarioJpaController implements Serializable {

    public MovimientoinventarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FADPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public MovimientoinventarioJpaController() {
    }
    
    public void create(Movimientoinventario movimientoinventario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Existencia idExistencia = movimientoinventario.getIdExistencia();
            if (idExistencia != null) {
                idExistencia = em.getReference(idExistencia.getClass(), idExistencia.getIdExistencia());
                movimientoinventario.setIdExistencia(idExistencia);
            }
            Ordeninventario idOrdeninventario = movimientoinventario.getIdOrdeninventario();
            if (idOrdeninventario != null) {
                idOrdeninventario = em.getReference(idOrdeninventario.getClass(), idOrdeninventario.getIdOrdeninventario());
                movimientoinventario.setIdOrdeninventario(idOrdeninventario);
            }
            em.persist(movimientoinventario);
            if (idExistencia != null) {
                idExistencia.getMovimientoinventarioCollection().add(movimientoinventario);
                idExistencia = em.merge(idExistencia);
            }
            if (idOrdeninventario != null) {
                idOrdeninventario.getMovimientoinventarioCollection().add(movimientoinventario);
                idOrdeninventario = em.merge(idOrdeninventario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMovimientoinventario(movimientoinventario.getIdMovimiento()) != null) {
                throw new PreexistingEntityException("Movimientoinventario " + movimientoinventario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Movimientoinventario movimientoinventario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Movimientoinventario persistentMovimientoinventario = em.find(Movimientoinventario.class, movimientoinventario.getIdMovimiento());
            Existencia idExistenciaOld = persistentMovimientoinventario.getIdExistencia();
            Existencia idExistenciaNew = movimientoinventario.getIdExistencia();
            Ordeninventario idOrdeninventarioOld = persistentMovimientoinventario.getIdOrdeninventario();
            Ordeninventario idOrdeninventarioNew = movimientoinventario.getIdOrdeninventario();
            if (idExistenciaNew != null) {
                idExistenciaNew = em.getReference(idExistenciaNew.getClass(), idExistenciaNew.getIdExistencia());
                movimientoinventario.setIdExistencia(idExistenciaNew);
            }
            if (idOrdeninventarioNew != null) {
                idOrdeninventarioNew = em.getReference(idOrdeninventarioNew.getClass(), idOrdeninventarioNew.getIdOrdeninventario());
                movimientoinventario.setIdOrdeninventario(idOrdeninventarioNew);
            }
            movimientoinventario = em.merge(movimientoinventario);
            if (idExistenciaOld != null && !idExistenciaOld.equals(idExistenciaNew)) {
                idExistenciaOld.getMovimientoinventarioCollection().remove(movimientoinventario);
                idExistenciaOld = em.merge(idExistenciaOld);
            }
            if (idExistenciaNew != null && !idExistenciaNew.equals(idExistenciaOld)) {
                idExistenciaNew.getMovimientoinventarioCollection().add(movimientoinventario);
                idExistenciaNew = em.merge(idExistenciaNew);
            }
            if (idOrdeninventarioOld != null && !idOrdeninventarioOld.equals(idOrdeninventarioNew)) {
                idOrdeninventarioOld.getMovimientoinventarioCollection().remove(movimientoinventario);
                idOrdeninventarioOld = em.merge(idOrdeninventarioOld);
            }
            if (idOrdeninventarioNew != null && !idOrdeninventarioNew.equals(idOrdeninventarioOld)) {
                idOrdeninventarioNew.getMovimientoinventarioCollection().add(movimientoinventario);
                idOrdeninventarioNew = em.merge(idOrdeninventarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = movimientoinventario.getIdMovimiento();
                if (findMovimientoinventario(id) == null) {
                    throw new NonexistentEntityException("The movimientoinventario with id " + id + " no longer exists.");
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
            Movimientoinventario movimientoinventario;
            try {
                movimientoinventario = em.getReference(Movimientoinventario.class, id);
                movimientoinventario.getIdMovimiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The movimientoinventario with id " + id + " no longer exists.", enfe);
            }
            Existencia idExistencia = movimientoinventario.getIdExistencia();
            if (idExistencia != null) {
                idExistencia.getMovimientoinventarioCollection().remove(movimientoinventario);
                idExistencia = em.merge(idExistencia);
            }
            Ordeninventario idOrdeninventario = movimientoinventario.getIdOrdeninventario();
            if (idOrdeninventario != null) {
                idOrdeninventario.getMovimientoinventarioCollection().remove(movimientoinventario);
                idOrdeninventario = em.merge(idOrdeninventario);
            }
            em.remove(movimientoinventario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Movimientoinventario> findMovimientoinventarioEntities() {
        return findMovimientoinventarioEntities(true, -1, -1);
    }

    public List<Movimientoinventario> findMovimientoinventarioEntities(int maxResults, int firstResult) {
        return findMovimientoinventarioEntities(false, maxResults, firstResult);
    }

    private List<Movimientoinventario> findMovimientoinventarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Movimientoinventario.class));
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

    public Movimientoinventario findMovimientoinventario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Movimientoinventario.class, id);
        } finally {
            em.close();
        }
    }

    public int getMovimientoinventarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Movimientoinventario> rt = cq.from(Movimientoinventario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
