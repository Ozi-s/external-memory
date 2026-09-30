package org.ozis.dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.ozis.entity.Project;
import org.ozis.entity.Task;
import org.ozis.entity.WorkSession;
import org.ozis.util.HibernateUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class WorkSessionDao extends GenericRepository<WorkSession, Integer> {

    public WorkSessionDao() {
        super(WorkSession.class);
    }

    public WorkSession startSession(Project project, Task focus) {
        WorkSession session = new WorkSession(project, focus);
        return save(session);
    }

    public boolean finishSession(int id) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            WorkSession session = em.find(WorkSession.class, id);
            if (session == null || session.getEndTime() != null) {
                tx.rollback();
                return false;
            }
            session.setEndTime(LocalTime.now());
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Optional<WorkSession> findActiveSession() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery("""
                    SELECT s
                    FROM WorkSession s
                    WHERE s.endTime IS NULL
                    ORDER BY s.date DESC, s.startTime DESC
                    """, WorkSession.class).getResultStream().findFirst();
        }
    }

    public List<WorkSession> findSesionByProject(Project project) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery("""
                    SELECT s
                    FROM WorkSession s
                    WHERE s.project = :project
                    ORDER BY s.date DESC, s.startTime DESC
                    """, WorkSession.class).setParameter("project", project).getResultList();
        }
    }

    public List<WorkSession> findSessionByDate(LocalDate date) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery("""
                    SELECT s
                    FROM WorkSession s
                    WHERE s.date = :date
                    ORDER BY s.startTime DESC
                    """, WorkSession.class).setParameter("date", date).getResultList();
        }
    }

    public List<WorkSession> findSessionByTask(Task focus) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {

            return em.createQuery("""
                    SELECT s
                    FROM WorkSession s
                    WHERE s.focus = :focus
                    ORDER BY s.date DESC, s.startTime DESC
                    """, WorkSession.class).setParameter("focus", focus).getResultList();
        }
    }
}