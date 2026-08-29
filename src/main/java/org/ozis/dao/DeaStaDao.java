package org.ozis.dao;

import jakarta.persistence.EntityManager;
import org.ozis.entity.Desigion_Status;
import org.ozis.util.HibernateUtil;
import java.util.List;

public class DeaStaDao extends GenericRepository<Desigion_Status, Integer> {

    // Конструктор теперь использует переданный класс
    public DeaStaDao(Class<Desigion_Status> desigionStatusClass) {
        super(desigionStatusClass);
    }

    public List<Desigion_Status> findByName(String name) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery("SELECT d FROM Desigion_Status d WHERE d.name LIKE :name", Desigion_Status.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        }
    }
}