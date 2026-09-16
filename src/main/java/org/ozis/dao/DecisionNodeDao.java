package org.ozis.dao;

import jakarta.persistence.EntityManager;
import org.ozis.entity.DecisionNode;
import org.ozis.util.HibernateUtil;

import java.util.List;

public class DecisionNodeDao extends GenericRepository<DecisionNode, Integer> {

    public DecisionNodeDao(Class<DecisionNode> entityClass) {
        super(entityClass);
    }

     //Получить корневые узлы дерева проекта.
     //Корневой узел не имеет родителя.
    public List<DecisionNode> findRootNodesByProject(Integer projectId) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery("""
                    select dn
                    from DecisionNode dn
                    where dn.project.id = :projectId
                    and dn.parent is null
                    order by dn.name
                    """, DecisionNode.class)
                    .setParameter("projectId", projectId)
                    .getResultList();
        }
    }

     //Получить дочерние узлы конкретного узла.
    public List<DecisionNode> findChildren(Integer parentId) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery("""
                    select dn
                    from DecisionNode dn
                    where dn.parent.id = :parentId
                    order by dn.name
                    """, DecisionNode.class)
                    .setParameter("parentId", parentId)
                    .getResultList();
        }
    }

     // Получить все узлы конкретного проекта.
    public List<DecisionNode> findNodesByProject(Integer projectId) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery("""
                    select dn
                    from DecisionNode dn
                    where dn.project.id = :projectId
                    order by dn.name
                    """, DecisionNode.class)
                    .setParameter("projectId", projectId)
                    .getResultList();
        }
    }

     //Найти узел, связанный с конкретным Decision.

    public DecisionNode findNodeByDecision(Integer decisionId) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery("""
                    select dn
                    from DecisionNode dn
                    where dn.decision.id = :decisionId
                    """, DecisionNode.class)
                    .setParameter("decisionId", decisionId)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }

     //Найти узлы проекта по названию.
    public List<DecisionNode> findNodesByNameAndProject(String name, Integer projectId) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery("""
                    select dn
                    from DecisionNode dn
                    where dn.name like :name
                    and dn.project.id = :projectId
                    order by dn.name
                    """, DecisionNode.class)
                    .setParameter("name", "%" + name + "%")
                    .setParameter("projectId", projectId)
                    .getResultList();
        }
    }


     //Найти узлы по названию среди детей конкретного узла.
    public List<DecisionNode> findChildrenByName(Integer parentId, String name) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery("""
                    select dn
                    from DecisionNode dn
                    where dn.parent.id = :parentId
                    and dn.name like :name
                    order by dn.name
                    """, DecisionNode.class)
                    .setParameter("parentId", parentId)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        }
    }
}