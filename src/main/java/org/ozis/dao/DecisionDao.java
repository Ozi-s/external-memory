package org.ozis.dao;

import jakarta.persistence.EntityManager;
import org.ozis.entity.Decision;
import org.ozis.util.HibernateUtil;

import java.util.List;

public class DecisionDao extends GenericRepository<Decision,Integer>{

    public DecisionDao(Class<Decision> entityClass) {
        super(entityClass);
    }

    public List<Decision> fingDesigionByProject(String project){
        try(EntityManager em= HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select d from Desigion where d.project_id.name like:project""", Decision.class).setParameter("project","%"+project+"%").getResultList();
        }
    }

    public List<Decision> findDEsigionByTask(Integer task_id){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select d from Desigion d where d.dependent_task,id=:task_id""", Decision.class).setParameter("task_id",task_id).getResultList();
        }
    }

    public List<Decision> findDesigionByStatus(String status){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select d from Desigion d where d.status.name like :status""", Decision.class).setParameter("status","%"+ status+"%").getResultList();
        }
    }

    public List<Decision> findDesigionByProjectAndStatus(String project, String status){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select d from Desigion d where d.status.name like:status and d.project_id.name like:project""", Decision.class).setParameter("project","%"+project+"%").setParameter("status","%"+ status+"%").getResultList();
        }
    }

    public List<Decision> findAlternativeDesigionsNative(Long desigionId) {//на sql в этом случае думалось лучше
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            String sql = """
            SELECT d2 FROM Desigion d1 JOIN Desigion d2 ON d1.dependent_task = d2.dependent_task WHERE d1.id = :id AND d1.id != d2.id
        """;
            return em.createNativeQuery(sql, Decision.class)
                    .setParameter("desigionId", desigionId)
                    .getResultList();
        }
    }


}
