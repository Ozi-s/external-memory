package org.ozis.dao;

import jakarta.persistence.EntityManager;
import org.ozis.entity.Context;
import org.ozis.entity.Desigion;
import org.ozis.util.HibernateUtil;

import java.util.List;

public class DesigionDao extends GenericRepository{
    public DesigionDao(Class entityClass) {
        super(entityClass);
    }

    public List<Object[]> fingDesigionByProject(String project){
        try(EntityManager em= HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select d.id, d.name, d.alternative_desigion, d.dependent_task, d.project_id.name from Desigion where d.project_id.name like:project""").setParameter("project","%"+project+"%").getResultList();
        }
    }

    public List<Object[]> findDEsigionByTask(int task_id){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select d.id, d.name, d.alternative_desigion, d.dependent_task, d.project_id.name from Desigion d where d.dependent_task=:task_id""").setParameter("task_id",task_id).getResultList();
        }
    }

    public List<Object[]> findDesigionByStatus(String status){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select d.id, d.name, d.alternative_desigion, d.dependent_task, d.project_id.name from Desigion d where d.desigion_status.name like :status""").setParameter("status","%"+ status+"%").getResultList();
        }
    }

    public List<Object[]> findDesigionByProjectAndStatus(String project, String status){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select d.id, d.name, d.alternative_desigion, d.dependent_task, d.project_id.name from Desigion d where d.desigion_status.name like:status and d.project_id.name like:project""").setParameter("project","%"+project+"%").setParameter("status","%"+ status+"%").getResultList();
        }
    }

    public List<Object[]> findAlternativeDesigionsNative(Long desigionId) {//на sql в этом случае думалось лучше
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            String sql = """
            select d1.id, d1.name, d1.dependent_task, d1.project_id, d2.id, d2.name, d2.dependent_task, d2.project_id
            from desigion d1
            join desigion d2 ON d1.dependent_task = d2.dependent_task
            where d1.id = :desigionId AND d1.id != d2.id
        """;
            return em.createNativeQuery(sql)
                    .setParameter("desigionId", desigionId)
                    .getResultList();
        }
    }


}
