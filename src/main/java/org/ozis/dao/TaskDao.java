package org.ozis.dao;

import jakarta.persistence.EntityManager;
import org.ozis.dto.TaskDto;
import org.ozis.entity.Task;
import org.ozis.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class TaskDao extends GenericRepository{
    public TaskDao(Class entityClass) {
        super(entityClass);
    }

    public List<Object[]> findTaskByPoject(int project_id){
        try(EntityManager em= HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select t.name, t.description, t.deadline, t.task_status
                    from Task t
                    join fetch t.task_status
                    where t.project_id=:project_id """).setParameter("project_id", project_id).getResultList();
        }
    }

    public List<Task> findTaskByStatus(String status){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select t from Task where t.task_status.name=:status""", Task.class).setParameter("status", status).getResultList();
        }
    }

    public List<Object[]> findTaskByProjectAndStatus(String project, String status) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                            "SELECT t.name, t.description, s.name " +
                                    "FROM Task t JOIN t.task_status s " +
                                    "WHERE t.project.id = :project AND s.name = :status")
                    .setParameter("project", project)
                    .setParameter("status", status)
                    .getResultList();
        }
    }

    public List<TaskDto> findTasksByDeadlineBetween(LocalDate from, LocalDate to) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            String jpql = """
                SELECT NEW org.ozis.dto.TaskDto(
                    t.id,
                    t.name,
                    t.description,
                    t.executionPeriod,
                    t.deadline,
                    p.name,
                    s.name
                )
                FROM Task t
                JOIN t.project p
                JOIN t.taskStatus s
                WHERE t.deadline BETWEEN :from AND :to
                ORDER BY t.deadline ASC
                """;
            return em.createQuery(jpql, TaskDto.class)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .getResultList();
        }
    }

    public List<TaskDto> findTaskByPeriod(int period){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    SELECT NEW org.ozis.dto.TaskDetailsDto(
                                        t.id,
                                        t.name,
                                        t.description,
                                        t.executionPeriod,
                                        t.deadline,
                                        p.name,
                                        s.name
                                    )
                                    FROM Task t
                                    JOIN t.project p
                                    JOIN t.taskStatus s
                                    where t.execution_period<:period""", TaskDto.class).setParameter("t.execution_period", period).getResultList();
        }
    }



}
