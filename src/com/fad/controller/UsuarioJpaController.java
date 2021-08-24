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
import com.fad.entities.Usuario;
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
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FADPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public UsuarioJpaController() {
    }

    public void create(Usuario usuario) {
        if (usuario.getOrdeninventarioCollection() == null) {
            usuario.setOrdeninventarioCollection(new ArrayList<Ordeninventario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Ordeninventario> attachedOrdeninventarioCollection = new ArrayList<Ordeninventario>();
            for (Ordeninventario ordeninventarioCollectionOrdeninventarioToAttach : usuario.getOrdeninventarioCollection()) {
                ordeninventarioCollectionOrdeninventarioToAttach = em.getReference(ordeninventarioCollectionOrdeninventarioToAttach.getClass(), ordeninventarioCollectionOrdeninventarioToAttach.getIdOrdeninventario());
                attachedOrdeninventarioCollection.add(ordeninventarioCollectionOrdeninventarioToAttach);
            }
            usuario.setOrdeninventarioCollection(attachedOrdeninventarioCollection);
            em.persist(usuario);
            for (Ordeninventario ordeninventarioCollectionOrdeninventario : usuario.getOrdeninventarioCollection()) {
                Usuario oldIdUsuarioOfOrdeninventarioCollectionOrdeninventario = ordeninventarioCollectionOrdeninventario.getIdUsuario();
                ordeninventarioCollectionOrdeninventario.setIdUsuario(usuario);
                ordeninventarioCollectionOrdeninventario = em.merge(ordeninventarioCollectionOrdeninventario);
                if (oldIdUsuarioOfOrdeninventarioCollectionOrdeninventario != null) {
                    oldIdUsuarioOfOrdeninventarioCollectionOrdeninventario.getOrdeninventarioCollection().remove(ordeninventarioCollectionOrdeninventario);
                    oldIdUsuarioOfOrdeninventarioCollectionOrdeninventario = em.merge(oldIdUsuarioOfOrdeninventarioCollectionOrdeninventario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            Collection<Ordeninventario> ordeninventarioCollectionOld = persistentUsuario.getOrdeninventarioCollection();
            Collection<Ordeninventario> ordeninventarioCollectionNew = usuario.getOrdeninventarioCollection();
            Collection<Ordeninventario> attachedOrdeninventarioCollectionNew = new ArrayList<Ordeninventario>();
            /*for (Ordeninventario ordeninventarioCollectionNewOrdeninventarioToAttach : ordeninventarioCollectionNew) {
                ordeninventarioCollectionNewOrdeninventarioToAttach = em.getReference(ordeninventarioCollectionNewOrdeninventarioToAttach.getClass(), ordeninventarioCollectionNewOrdeninventarioToAttach.getIdOrdeninventario());
                attachedOrdeninventarioCollectionNew.add(ordeninventarioCollectionNewOrdeninventarioToAttach);
            }*/
            ordeninventarioCollectionNew = attachedOrdeninventarioCollectionNew;
            usuario.setOrdeninventarioCollection(ordeninventarioCollectionNew);
            usuario = em.merge(usuario);
            for (Ordeninventario ordeninventarioCollectionOldOrdeninventario : ordeninventarioCollectionOld) {
                if (!ordeninventarioCollectionNew.contains(ordeninventarioCollectionOldOrdeninventario)) {
                    ordeninventarioCollectionOldOrdeninventario.setIdUsuario(null);
                    ordeninventarioCollectionOldOrdeninventario = em.merge(ordeninventarioCollectionOldOrdeninventario);
                }
            }
            for (Ordeninventario ordeninventarioCollectionNewOrdeninventario : ordeninventarioCollectionNew) {
                if (!ordeninventarioCollectionOld.contains(ordeninventarioCollectionNewOrdeninventario)) {
                    Usuario oldIdUsuarioOfOrdeninventarioCollectionNewOrdeninventario = ordeninventarioCollectionNewOrdeninventario.getIdUsuario();
                    ordeninventarioCollectionNewOrdeninventario.setIdUsuario(usuario);
                    ordeninventarioCollectionNewOrdeninventario = em.merge(ordeninventarioCollectionNewOrdeninventario);
                    if (oldIdUsuarioOfOrdeninventarioCollectionNewOrdeninventario != null && !oldIdUsuarioOfOrdeninventarioCollectionNewOrdeninventario.equals(usuario)) {
                        oldIdUsuarioOfOrdeninventarioCollectionNewOrdeninventario.getOrdeninventarioCollection().remove(ordeninventarioCollectionNewOrdeninventario);
                        oldIdUsuarioOfOrdeninventarioCollectionNewOrdeninventario = em.merge(oldIdUsuarioOfOrdeninventarioCollectionNewOrdeninventario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIdUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            Collection<Ordeninventario> ordeninventarioCollection = usuario.getOrdeninventarioCollection();
            for (Ordeninventario ordeninventarioCollectionOrdeninventario : ordeninventarioCollection) {
                ordeninventarioCollectionOrdeninventario.setIdUsuario(null);
                ordeninventarioCollectionOrdeninventario = em.merge(ordeninventarioCollectionOrdeninventario);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
