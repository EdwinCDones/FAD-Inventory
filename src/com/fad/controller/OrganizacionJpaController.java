/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fad.controller;

import com.fad.controller.exceptions.NonexistentEntityException;
import com.fad.entities.Organizacion;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author GabuAndLemo
 */
public class OrganizacionJpaController implements Serializable {

    public OrganizacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FADPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public OrganizacionJpaController() {
    }
    
    public void create(Organizacion organizacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(organizacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Organizacion organizacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            organizacion = em.merge(organizacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = organizacion.getIdOrganizacion();
                if (findOrganizacion(id) == null) {
                    throw new NonexistentEntityException("The organizacion with id " + id + " no longer exists.");
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
            Organizacion organizacion;
            try {
                organizacion = em.getReference(Organizacion.class, id);
                organizacion.getIdOrganizacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The organizacion with id " + id + " no longer exists.", enfe);
            }
            em.remove(organizacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Organizacion> findOrganizacionEntities() {
        return findOrganizacionEntities(true, -1, -1);
    }

    public List<Organizacion> findOrganizacionEntities(int maxResults, int firstResult) {
        return findOrganizacionEntities(false, maxResults, firstResult);
    }

    private List<Organizacion> findOrganizacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Organizacion.class));
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

    public Organizacion findOrganizacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Organizacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrganizacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Organizacion> rt = cq.from(Organizacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
