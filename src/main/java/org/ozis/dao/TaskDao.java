package org.ozis.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.ozis.dto.TaskDto;
import org.ozis.entity.Task;
import org.ozis.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class TaskDao extends GenericRepository<Task, Integer>{
    public TaskDao(Class<Task> taskClass) {
        super(Task.class);
    }

    public List<Task> findTaskByPoject(int project_id){
        try(EntityManager em= HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select t
                    from Task t
                    join fetch t.task_status
                    where t.project_id.id=:project_id and t.deleted=false """).setParameter("project_id", project_id).getResultList();
        }
    }

    // public List<Task> findTaskByPoject(int project_id){
    //     try(EntityManager em= HibernateUtil.createEntityManager()){
    //         return em.createQuery("""
    //                 select t
    //                 from Task t
    //                 join fetch t.task_status
    //                 where t.project_id=:project_id """).setParameter("project_id", project_id).getResultList();
    //     }
    // }

    public List<Task> findTaskByStatus(String status){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select t from Task where t.task_status.name and t.deleted=false like :status""", Task.class).setParameter("status", "%"+status+"%").getResultList();
        }
    }

    public List<Task> findTaskByProjectAndStatus(String project, String status) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                            "SELECT t " +
                                    "FROM Task t JOIN t.task_status s " +
                                    "WHERE t.project_id.name like :project AND s.name like :status and t.deleted=false", Task.class)
                    .setParameter("project", "%"+project+"%")
                    .setParameter("status","%"+ status+"%")
                    .getResultList();
        }
    }

    public List<Task> findTasksByDeadlineBetween(LocalDate from, LocalDate to) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            String jpql = """
                SELECT t FROM Task t JOIN t.project p JOIN t.task_status s WHERE t.deadline BETWEEN :from AND :to ORDER BY t.deadline ASC
                """;
            return em.createQuery(jpql, Task.class)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .getResultList();
        }
    }

    public List<Task> findTaskByPeriod(int period){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    SELECT t FROM Task t JOIN t.project p JOIN t.task_sgitatus s where t.execution_period<:period""", Task.class).setParameter("period", period).getResultList();
        }
    }

    public boolean softDelete(int id) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int updated = em.createQuery("""
                UPDATE Task t
                SET t.deleted = true
                WHERE t.id = :id
                """).setParameter("id", id).executeUpdate();
            tx.commit();
            return updated > 0;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public boolean restore(int id) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            int updated = em.createQuery("""
                UPDATE Task t
                SET t.deleted = false
                WHERE t.id = :id
                """).setParameter("id", id).executeUpdate();
            tx.commit();
            return updated > 0;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

}
