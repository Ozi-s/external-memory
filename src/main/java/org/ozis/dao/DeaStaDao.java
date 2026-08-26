package org.ozis.dao;

import jakarta.persistence.EntityManager;
import org.ozis.entity.Desigion_Status;
import org.ozis.entity.Task_Status;
import org.ozis.util.HibernateUtil;

import java.util.List;

public class DeaStaDao extends GenericRepository{
    public DeaStaDao(Class entityClass) {
        super(entityClass);
    }

    public List<Desigion_Status> findByName(String name){
        try(EntityManager em= HibernateUtil.createEntityManager()){
            return em.createQuery("select t from Dessigion_Status where t.name like :name", Desigion_Status.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        }
    }
}
