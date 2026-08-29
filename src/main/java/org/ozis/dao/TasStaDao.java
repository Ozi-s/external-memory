package org.ozis.dao;

import jakarta.persistence.EntityManager;
import org.ozis.entity.Task_Status;
import org.ozis.util.HibernateUtil;

import java.util.List;

public class TasStaDao extends GenericRepository<Task_Status, Integer> {
    public TasStaDao(Class<Task_Status> taskStatusClass) {
        super(Task_Status.class);
    }

    public List<Task_Status> findByName(String name){
        try(EntityManager em = HibernateUtil.createEntityManager()){
            return em.createQuery("select t from Task_Status t where t.name like :name", Task_Status.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        }
    }
}