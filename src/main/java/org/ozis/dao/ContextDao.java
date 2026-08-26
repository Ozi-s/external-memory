package org.ozis.dao;

import jakarta.persistence.EntityManager;
import org.ozis.entity.Context;
import org.ozis.util.HibernateUtil;

import java.util.List;

public class ContextDao extends GenericRepository{
    public ContextDao(Class entityClass) {
        super(entityClass);
    }

    public List<Context> fingContextByProject(String project){
        try(EntityManager em= HibernateUtil.createEntityManager()){
            return em.createQuery("""
                    select c from Context where c.project_id.name like :project""", Context.class).setParameter("project","%"+project+"%").getResultList();
        }
    }
}
