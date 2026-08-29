package org.ozis.dao;

import jakarta.persistence.EntityManager;
import org.ozis.entity.Context;
import org.ozis.util.HibernateUtil;
import java.util.List;

public class ContextDao extends GenericRepository<Context, Integer> {

    public ContextDao(Class<Context> contextClass) {
        super(contextClass);
    }

    // исправлено имя метода и JPQL
    public List<Context> findContextByProject(String projectName) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                            "SELECT c FROM Context c WHERE c.project.name LIKE :projectName",  // ← исправлено
                            Context.class)
                    .setParameter("projectName", "%" + projectName + "%")
                    .getResultList();
        }
    }
}