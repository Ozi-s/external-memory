package org.ozis.dao;

import jakarta.persistence.EntityManager;
import org.ozis.entity.Desigion;
import org.ozis.util.HibernateUtil;

import java.util.List;

public class DesigionDao extends GenericRepository<Desigion,Integer>{
    public DesigionDao(Class<Desigion> desigionClass) {
        super(Desigion.class);
    }

    public List<Desigion> fingDesigionByProject(String project){
        try(EntityManager em= HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select d from Desigion where d.project_id.name like:project""", Desigion.class).setParameter("project","%"+project+"%").getResultList();
        }
    }

    public List<Desigion> findDEsigionByTask(Integer task_id){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select d from Desigion d where d.dependent_task,id=:task_id""", Desigion.class).setParameter("task_id",task_id).getResultList();
        }
    }

    public List<Desigion> findDesigionByStatus(String status){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select d from Desigion d where d.status.name like :status""", Desigion.class).setParameter("status","%"+ status+"%").getResultList();
        }
    }

    public List<Desigion> findDesigionByProjectAndStatus(String project, String status){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select d from Desigion d where d.status.name like:status and d.project_id.name like:project""", Desigion.class).setParameter("project","%"+project+"%").setParameter("status","%"+ status+"%").getResultList();
        }
    }

    public List<Desigion> findAlternativeDesigionsNative(Long desigionId) {//на sql в этом случае думалось лучше
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            String sql = """
            SELECT d2 FROM Desigion d1 JOIN Desigion d2 ON d1.dependent_task = d2.dependent_task WHERE d1.id = :id AND d1.id != d2.id
        """;
            return em.createNativeQuery(sql, Desigion.class)
                    .setParameter("desigionId", desigionId)
                    .getResultList();
        }
    }


}
