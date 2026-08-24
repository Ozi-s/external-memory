package org.ozis.dao;

import jakarta.persistence.EntityManager;
import org.ozis.entity.Project;
import org.ozis.util.HibernateUtil;

import java.util.List;

public class ProjectDao extends GenericRepository{
    public ProjectDao(Class entityClass) {
        super(entityClass);
    }

    public List<Project> findByName(String name) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery("SELECT p FROM Project p WHERE p.name LIKE :name", Project.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        }
    }

    public List<Project> sortAllProjects(){
        try(EntityManager em=HibernateUtil.createEntityManager()){
            return em.createQuery("select p from Project p order by p.name", Project.class).getResultList();
        }
    }

}
