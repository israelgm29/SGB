/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bootsystem.controllers;

import com.bootsystem.controllers.exceptions.IllegalOrphanException;
import com.bootsystem.controllers.exceptions.NonexistentEntityException;
import com.bootsystem.controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.bootsystem.entities.District;
import com.bootsystem.entities.Student;
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
public class UniteJpaController implements Serializable {

    public UniteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Unite unite) throws RollbackFailureException, Exception {
        if (unite.getStudentCollection() == null) {
            unite.setStudentCollection(new ArrayList<Student>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            District fkDistrict = unite.getFkDistrict();
            if (fkDistrict != null) {
                fkDistrict = em.getReference(fkDistrict.getClass(), fkDistrict.getId());
                unite.setFkDistrict(fkDistrict);
            }
            Collection<Student> attachedStudentCollection = new ArrayList<Student>();
            for (Student studentCollectionStudentToAttach : unite.getStudentCollection()) {
                studentCollectionStudentToAttach = em.getReference(studentCollectionStudentToAttach.getClass(), studentCollectionStudentToAttach.getId());
                attachedStudentCollection.add(studentCollectionStudentToAttach);
            }
            unite.setStudentCollection(attachedStudentCollection);
            em.persist(unite);
            if (fkDistrict != null) {
                fkDistrict.getUniteCollection().add(unite);
                fkDistrict = em.merge(fkDistrict);
            }
            for (Student studentCollectionStudent : unite.getStudentCollection()) {
                Unite oldFkUnitOfStudentCollectionStudent = studentCollectionStudent.getFkUnit();
                studentCollectionStudent.setFkUnit(unite);
                studentCollectionStudent = em.merge(studentCollectionStudent);
                if (oldFkUnitOfStudentCollectionStudent != null) {
                    oldFkUnitOfStudentCollectionStudent.getStudentCollection().remove(studentCollectionStudent);
                    oldFkUnitOfStudentCollectionStudent = em.merge(oldFkUnitOfStudentCollectionStudent);
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

    public void edit(Unite unite) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Unite persistentUnite = em.find(Unite.class, unite.getId());
            District fkDistrictOld = persistentUnite.getFkDistrict();
            District fkDistrictNew = unite.getFkDistrict();
            Collection<Student> studentCollectionOld = persistentUnite.getStudentCollection();
            Collection<Student> studentCollectionNew = unite.getStudentCollection();
            List<String> illegalOrphanMessages = null;
            for (Student studentCollectionOldStudent : studentCollectionOld) {
                if (!studentCollectionNew.contains(studentCollectionOldStudent)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Student " + studentCollectionOldStudent + " since its fkUnit field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkDistrictNew != null) {
                fkDistrictNew = em.getReference(fkDistrictNew.getClass(), fkDistrictNew.getId());
                unite.setFkDistrict(fkDistrictNew);
            }
            Collection<Student> attachedStudentCollectionNew = new ArrayList<Student>();
            for (Student studentCollectionNewStudentToAttach : studentCollectionNew) {
                studentCollectionNewStudentToAttach = em.getReference(studentCollectionNewStudentToAttach.getClass(), studentCollectionNewStudentToAttach.getId());
                attachedStudentCollectionNew.add(studentCollectionNewStudentToAttach);
            }
            studentCollectionNew = attachedStudentCollectionNew;
            unite.setStudentCollection(studentCollectionNew);
            unite = em.merge(unite);
            if (fkDistrictOld != null && !fkDistrictOld.equals(fkDistrictNew)) {
                fkDistrictOld.getUniteCollection().remove(unite);
                fkDistrictOld = em.merge(fkDistrictOld);
            }
            if (fkDistrictNew != null && !fkDistrictNew.equals(fkDistrictOld)) {
                fkDistrictNew.getUniteCollection().add(unite);
                fkDistrictNew = em.merge(fkDistrictNew);
            }
            for (Student studentCollectionNewStudent : studentCollectionNew) {
                if (!studentCollectionOld.contains(studentCollectionNewStudent)) {
                    Unite oldFkUnitOfStudentCollectionNewStudent = studentCollectionNewStudent.getFkUnit();
                    studentCollectionNewStudent.setFkUnit(unite);
                    studentCollectionNewStudent = em.merge(studentCollectionNewStudent);
                    if (oldFkUnitOfStudentCollectionNewStudent != null && !oldFkUnitOfStudentCollectionNewStudent.equals(unite)) {
                        oldFkUnitOfStudentCollectionNewStudent.getStudentCollection().remove(studentCollectionNewStudent);
                        oldFkUnitOfStudentCollectionNewStudent = em.merge(oldFkUnitOfStudentCollectionNewStudent);
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
                Integer id = unite.getId();
                if (findUnite(id) == null) {
                    throw new NonexistentEntityException("The unite with id " + id + " no longer exists.");
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
            Unite unite;
            try {
                unite = em.getReference(Unite.class, id);
                unite.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The unite with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Student> studentCollectionOrphanCheck = unite.getStudentCollection();
            for (Student studentCollectionOrphanCheckStudent : studentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Unite (" + unite + ") cannot be destroyed since the Student " + studentCollectionOrphanCheckStudent + " in its studentCollection field has a non-nullable fkUnit field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            District fkDistrict = unite.getFkDistrict();
            if (fkDistrict != null) {
                fkDistrict.getUniteCollection().remove(unite);
                fkDistrict = em.merge(fkDistrict);
            }
            em.remove(unite);
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

    public List<Unite> findUniteEntities() {
        return findUniteEntities(true, -1, -1);
    }

    public List<Unite> findUniteEntities(int maxResults, int firstResult) {
        return findUniteEntities(false, maxResults, firstResult);
    }

    private List<Unite> findUniteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Unite.class));
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

    public Unite findUnite(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Unite.class, id);
        } finally {
            em.close();
        }
    }

    public int getUniteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Unite> rt = cq.from(Unite.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
