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
import com.fad.entities.Tipordeninv;
import com.fad.entities.Usuario;
import com.fad.entities.Movimientoinventario;
import com.fad.entities.Ordeninventario;
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
public class OrdeninventarioJpaController implements Serializable {

    public OrdeninventarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("FADPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public OrdeninventarioJpaController() {
    }
    
    public void create(Ordeninventario ordeninventario) throws PreexistingEntityException, Exception {
        if (ordeninventario.getMovimientoinventarioCollection() == null) {
            ordeninventario.setMovimientoinventarioCollection(new ArrayList<Movimientoinventario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipordeninv idTipordeninv = ordeninventario.getIdTipordeninv();
            if (idTipordeninv != null) {
                idTipordeninv = em.getReference(idTipordeninv.getClass(), idTipordeninv.getIdTipordeninv());
                ordeninventario.setIdTipordeninv(idTipordeninv);
            }
            Usuario idUsuario = ordeninventario.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                ordeninventario.setIdUsuario(idUsuario);
            }
            Collection<Movimientoinventario> attachedMovimientoinventarioCollection = new ArrayList<Movimientoinventario>();
            for (Movimientoinventario movimientoinventarioCollectionMovimientoinventarioToAttach : ordeninventario.getMovimientoinventarioCollection()) {
                movimientoinventarioCollectionMovimientoinventarioToAttach = em.getReference(movimientoinventarioCollectionMovimientoinventarioToAttach.getClass(), movimientoinventarioCollectionMovimientoinventarioToAttach.getIdMovimiento());
                attachedMovimientoinventarioCollection.add(movimientoinventarioCollectionMovimientoinventarioToAttach);
            }
            ordeninventario.setMovimientoinventarioCollection(attachedMovimientoinventarioCollection);
            em.persist(ordeninventario);
            if (idTipordeninv != null) {
                idTipordeninv.getOrdeninventarioCollection().add(ordeninventario);
                idTipordeninv = em.merge(idTipordeninv);
            }
            if (idUsuario != null) {
                idUsuario.getOrdeninventarioCollection().add(ordeninventario);
                idUsuario = em.merge(idUsuario);
            }
            for (Movimientoinventario movimientoinventarioCollectionMovimientoinventario : ordeninventario.getMovimientoinventarioCollection()) {
                Ordeninventario oldIdOrdeninventarioOfMovimientoinventarioCollectionMovimientoinventario = movimientoinventarioCollectionMovimientoinventario.getIdOrdeninventario();
                movimientoinventarioCollectionMovimientoinventario.setIdOrdeninventario(ordeninventario);
                movimientoinventarioCollectionMovimientoinventario = em.merge(movimientoinventarioCollectionMovimientoinventario);
                if (oldIdOrdeninventarioOfMovimientoinventarioCollectionMovimientoinventario != null) {
                    oldIdOrdeninventarioOfMovimientoinventarioCollectionMovimientoinventario.getMovimientoinventarioCollection().remove(movimientoinventarioCollectionMovimientoinventario);
                    oldIdOrdeninventarioOfMovimientoinventarioCollectionMovimientoinventario = em.merge(oldIdOrdeninventarioOfMovimientoinventarioCollectionMovimientoinventario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrdeninventario(ordeninventario.getIdOrdeninventario()) != null) {
                throw new PreexistingEntityException("Ordeninventario " + ordeninventario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ordeninventario ordeninventario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ordeninventario persistentOrdeninventario = em.find(Ordeninventario.class, ordeninventario.getIdOrdeninventario());
            Tipordeninv idTipordeninvOld = persistentOrdeninventario.getIdTipordeninv();
            Tipordeninv idTipordeninvNew = ordeninventario.getIdTipordeninv();
            Usuario idUsuarioOld = persistentOrdeninventario.getIdUsuario();
            Usuario idUsuarioNew = ordeninventario.getIdUsuario();
            Collection<Movimientoinventario> movimientoinventarioCollectionOld = persistentOrdeninventario.getMovimientoinventarioCollection();
            Collection<Movimientoinventario> movimientoinventarioCollectionNew = ordeninventario.getMovimientoinventarioCollection();
            if (idTipordeninvNew != null) {
                idTipordeninvNew = em.getReference(idTipordeninvNew.getClass(), idTipordeninvNew.getIdTipordeninv());
                ordeninventario.setIdTipordeninv(idTipordeninvNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                ordeninventario.setIdUsuario(idUsuarioNew);
            }
            Collection<Movimientoinventario> attachedMovimientoinventarioCollectionNew = new ArrayList<Movimientoinventario>();
            for (Movimientoinventario movimientoinventarioCollectionNewMovimientoinventarioToAttach : movimientoinventarioCollectionNew) {
                movimientoinventarioCollectionNewMovimientoinventarioToAttach = em.getReference(movimientoinventarioCollectionNewMovimientoinventarioToAttach.getClass(), movimientoinventarioCollectionNewMovimientoinventarioToAttach.getIdMovimiento());
                attachedMovimientoinventarioCollectionNew.add(movimientoinventarioCollectionNewMovimientoinventarioToAttach);
            }
            movimientoinventarioCollectionNew = attachedMovimientoinventarioCollectionNew;
            ordeninventario.setMovimientoinventarioCollection(movimientoinventarioCollectionNew);
            ordeninventario = em.merge(ordeninventario);
            if (idTipordeninvOld != null && !idTipordeninvOld.equals(idTipordeninvNew)) {
                idTipordeninvOld.getOrdeninventarioCollection().remove(ordeninventario);
                idTipordeninvOld = em.merge(idTipordeninvOld);
            }
            if (idTipordeninvNew != null && !idTipordeninvNew.equals(idTipordeninvOld)) {
                idTipordeninvNew.getOrdeninventarioCollection().add(ordeninventario);
                idTipordeninvNew = em.merge(idTipordeninvNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getOrdeninventarioCollection().remove(ordeninventario);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getOrdeninventarioCollection().add(ordeninventario);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (Movimientoinventario movimientoinventarioCollectionOldMovimientoinventario : movimientoinventarioCollectionOld) {
                if (!movimientoinventarioCollectionNew.contains(movimientoinventarioCollectionOldMovimientoinventario)) {
                    movimientoinventarioCollectionOldMovimientoinventario.setIdOrdeninventario(null);
                    movimientoinventarioCollectionOldMovimientoinventario = em.merge(movimientoinventarioCollectionOldMovimientoinventario);
                }
            }
            for (Movimientoinventario movimientoinventarioCollectionNewMovimientoinventario : movimientoinventarioCollectionNew) {
                if (!movimientoinventarioCollectionOld.contains(movimientoinventarioCollectionNewMovimientoinventario)) {
                    Ordeninventario oldIdOrdeninventarioOfMovimientoinventarioCollectionNewMovimientoinventario = movimientoinventarioCollectionNewMovimientoinventario.getIdOrdeninventario();
                    movimientoinventarioCollectionNewMovimientoinventario.setIdOrdeninventario(ordeninventario);
                    movimientoinventarioCollectionNewMovimientoinventario = em.merge(movimientoinventarioCollectionNewMovimientoinventario);
                    if (oldIdOrdeninventarioOfMovimientoinventarioCollectionNewMovimientoinventario != null && !oldIdOrdeninventarioOfMovimientoinventarioCollectionNewMovimientoinventario.equals(ordeninventario)) {
                        oldIdOrdeninventarioOfMovimientoinventarioCollectionNewMovimientoinventario.getMovimientoinventarioCollection().remove(movimientoinventarioCollectionNewMovimientoinventario);
                        oldIdOrdeninventarioOfMovimientoinventarioCollectionNewMovimientoinventario = em.merge(oldIdOrdeninventarioOfMovimientoinventarioCollectionNewMovimientoinventario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ordeninventario.getIdOrdeninventario();
                if (findOrdeninventario(id) == null) {
                    throw new NonexistentEntityException("The ordeninventario with id " + id + " no longer exists.");
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
            Ordeninventario ordeninventario;
            try {
                ordeninventario = em.getReference(Ordeninventario.class, id);
                ordeninventario.getIdOrdeninventario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordeninventario with id " + id + " no longer exists.", enfe);
            }
            Tipordeninv idTipordeninv = ordeninventario.getIdTipordeninv();
            if (idTipordeninv != null) {
                idTipordeninv.getOrdeninventarioCollection().remove(ordeninventario);
                idTipordeninv = em.merge(idTipordeninv);
            }
            Usuario idUsuario = ordeninventario.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getOrdeninventarioCollection().remove(ordeninventario);
                idUsuario = em.merge(idUsuario);
            }
            Collection<Movimientoinventario> movimientoinventarioCollection = ordeninventario.getMovimientoinventarioCollection();
            for (Movimientoinventario movimientoinventarioCollectionMovimientoinventario : movimientoinventarioCollection) {
                movimientoinventarioCollectionMovimientoinventario.setIdOrdeninventario(null);
                movimientoinventarioCollectionMovimientoinventario = em.merge(movimientoinventarioCollectionMovimientoinventario);
            }
            em.remove(ordeninventario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ordeninventario> findOrdeninventarioEntities() {
        return findOrdeninventarioEntities(true, -1, -1);
    }

    public List<Ordeninventario> findOrdeninventarioEntities(int maxResults, int firstResult) {
        return findOrdeninventarioEntities(false, maxResults, firstResult);
    }

    private List<Ordeninventario> findOrdeninventarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ordeninventario.class));
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

    public Ordeninventario findOrdeninventario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ordeninventario.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdeninventarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ordeninventario> rt = cq.from(Ordeninventario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
