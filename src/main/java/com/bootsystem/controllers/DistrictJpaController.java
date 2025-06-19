/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bootsystem.controllers;

import com.bootsystem.controllers.exceptions.IllegalOrphanException;
import com.bootsystem.controllers.exceptions.NonexistentEntityException;
import com.bootsystem.controllers.exceptions.RollbackFailureException;
import com.bootsystem.entities.District;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.bootsystem.entities.Unite;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author jhonatan
 */
public class DistrictJpaController implements Serializable {

    public DistrictJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(District district) throws RollbackFailureException, Exception {
        if (district.getUniteCollection() == null) {
            district.setUniteCollection(new ArrayList<Unite>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Unite> attachedUniteCollection = new ArrayList<Unite>();
            for (Unite uniteCollectionUniteToAttach : district.getUniteCollection()) {
                uniteCollectionUniteToAttach = em.getReference(uniteCollectionUniteToAttach.getClass(), uniteCollectionUniteToAttach.getId());
                attachedUniteCollection.add(uniteCollectionUniteToAttach);
            }
            district.setUniteCollection(attachedUniteCollection);
            em.persist(district);
            for (Unite uniteCollectionUnite : district.getUniteCollection()) {
                District oldFkDistrictOfUniteCollectionUnite = uniteCollectionUnite.getFkDistrict();
                uniteCollectionUnite.setFkDistrict(district);
                uniteCollectionUnite = em.merge(uniteCollectionUnite);
                if (oldFkDistrictOfUniteCollectionUnite != null) {
                    oldFkDistrictOfUniteCollectionUnite.getUniteCollection().remove(uniteCollectionUnite);
                    oldFkDistrictOfUniteCollectionUnite = em.merge(oldFkDistrictOfUniteCollectionUnite);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(District district) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            District persistentDistrict = em.find(District.class, district.getId());
            Collection<Unite> uniteCollectionOld = persistentDistrict.getUniteCollection();
            Collection<Unite> uniteCollectionNew = district.getUniteCollection();
            List<String> illegalOrphanMessages = null;
            for (Unite uniteCollectionOldUnite : uniteCollectionOld) {
                if (!uniteCollectionNew.contains(uniteCollectionOldUnite)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Unite " + uniteCollectionOldUnite + " since its fkDistrict field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Unite> attachedUniteCollectionNew = new ArrayList<Unite>();
            for (Unite uniteCollectionNewUniteToAttach : uniteCollectionNew) {
                uniteCollectionNewUniteToAttach = em.getReference(uniteCollectionNewUniteToAttach.getClass(), uniteCollectionNewUniteToAttach.getId());
                attachedUniteCollectionNew.add(uniteCollectionNewUniteToAttach);
            }
            uniteCollectionNew = attachedUniteCollectionNew;
            district.setUniteCollection(uniteCollectionNew);
            district = em.merge(district);
            for (Unite uniteCollectionNewUnite : uniteCollectionNew) {
                if (!uniteCollectionOld.contains(uniteCollectionNewUnite)) {
                    District oldFkDistrictOfUniteCollectionNewUnite = uniteCollectionNewUnite.getFkDistrict();
                    uniteCollectionNewUnite.setFkDistrict(district);
                    uniteCollectionNewUnite = em.merge(uniteCollectionNewUnite);
                    if (oldFkDistrictOfUniteCollectionNewUnite != null && !oldFkDistrictOfUniteCollectionNewUnite.equals(district)) {
                        oldFkDistrictOfUniteCollectionNewUnite.getUniteCollection().remove(uniteCollectionNewUnite);
                        oldFkDistrictOfUniteCollectionNewUnite = em.merge(oldFkDistrictOfUniteCollectionNewUnite);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = district.getId();
                if (findDistrict(id) == null) {
                    throw new NonexistentEntityException("The district with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            District district;
            try {
                district = em.getReference(District.class, id);
                district.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The district with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Unite> uniteCollectionOrphanCheck = district.getUniteCollection();
            for (Unite uniteCollectionOrphanCheckUnite : uniteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This District (" + district + ") cannot be destroyed since the Unite " + uniteCollectionOrphanCheckUnite + " in its uniteCollection field has a non-nullable fkDistrict field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(district);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<District> findDistrictEntities() {
        return findDistrictEntities(true, -1, -1);
    }

    public List<District> findDistrictEntities(int maxResults, int firstResult) {
        return findDistrictEntities(false, maxResults, firstResult);
    }

    private List<District> findDistrictEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(District.class));
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

    public District findDistrict(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(District.class, id);
        } finally {
            em.close();
        }
    }

    public int getDistrictCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<District> rt = cq.from(District.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
