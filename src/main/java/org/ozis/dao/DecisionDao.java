package org.ozis.dao;

import jakarta.persistence.EntityManager;
import org.ozis.entity.Decision;
import org.ozis.util.HibernateUtil;

import java.util.List;

public class DecisionDao extends GenericRepository<Decision,Integer>{

    public DecisionDao(Class<Decision> entityClass) {
        super(entityClass);
    }

    public List<Decision> fingDecisionByProject(String project){
        try(EntityManager em= HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select d from Decision where d.project_id.name like:project""", Decision.class).setParameter("project","%"+project+"%").getResultList();
        }
    }

    public List<Decision> findDecisionByTask(Integer task_id){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select d from Decision d where d.dependent_task.id=:task_id""", Decision.class).setParameter("task_id",task_id).getResultList();
        }
    }

    public List<Decision> findDecisionByStatus(String status){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select d from Decision d where d.status.name like :status""", Decision.class).setParameter("status","%"+ status+"%").getResultList();
        }
    }

    public List<Decision> findDecisionByProjectAndStatus(String project, String status){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select d from Decision d where d.status.name like:status and d.project_id.name like:project""", Decision.class).setParameter("project","%"+project+"%").setParameter("status","%"+ status+"%").getResultList();
        }
    }

    public List<Decision> findAlternativeDecisionsNative(Long decisionId) {//на sql в этом случае думалось лучше
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            String sql = """
            SELECT d2 FROM Decision d1 JOIN Decision d2 ON d1.dependent_task = d2.dependent_task WHERE d1.id = :id AND d1.id != d2.id
        """;
            return em.createNativeQuery(sql, Decision.class)
                    .setParameter("decisionId", decisionId)
                    .getResultList();
        }
    }


}
