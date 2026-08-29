package org.ozis;

import org.ozis.dao.*;
import org.ozis.entity.*;
import org.ozis.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class WorkTest {

    public static void main(String[] args) {


        System.out.println("========== ЗАПУСК ТЕСТА DAO ==========\n");

        // 2. Создаём и сохраняем тестовые данные (в отдельной транзакции)
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // Создаём сущности
            Task_Status taskStatus = new Task_Status("Совершенная");
            Project project = new Project("Офис", "Проект А");
            Desigion_Status desigionStatus = new Desigion_Status("Разрабатывается");
            // Для контекста нужен проект, но пока не сохраняем, т.к. нет связи

            // Сохраняем через DAO (используем EntityManager, переданный в DAO)
            // Но наши DAO создают свой EntityManager, поэтому лучше использовать generic методы,
            // которые принимают EntityManager? Или просто сохраняем через em.persist()
            //em.persist(taskStatus);
            em.persist(project);
            em.persist(desigionStatus);
            // Если есть Context, то тоже сохраняем

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            em.close();
            return;
        }
        em.close();

        // 3. Теперь выполняем проверки (каждая в своей транзакции, но можно и в одной)
        testTaskStatus();
        testProject();
        testDesigionStatus();
        // testContext(); если есть данные

        System.out.println("\n========== ТЕСТ ЗАВЕРШЁН ==========");
    }

    private static void testTaskStatus() {
        TasStaDao dao = new TasStaDao(Task_Status.class);
        System.out.println("======= ПРОВЕРКА Task_Status =======");
        List<Task_Status> all = dao.findAll();
        System.out.println("--- findAll() ---");
        all.forEach(System.out::println);

        if (!all.isEmpty()) {
            Integer id = all.get(0).getId();
            System.out.println("--- findById(" + id + ") ---");
            Optional<Task_Status> found = dao.findById(id);
            System.out.println(found);
        }

        System.out.println("--- findByName('Новая') ---");
        List<Task_Status> byName = dao.findByName("Новая");
        byName.forEach(System.out::println);
    }

    private static void testProject() {
        ProjectDao dao = new ProjectDao(Project.class);
        System.out.println("\n======= ПРОВЕРКА Project =======");
        List<Project> all = dao.findAll();
        System.out.println("--- findAll() ---");
        all.forEach(System.out::println);

        if (!all.isEmpty()) {
            Integer id = all.get(0).getId();
            System.out.println("--- findById(" + id + ") ---");
            Optional<Project> found = dao.findById(id);
            System.out.println(found);
        }

        System.out.println("--- findByName('Проект') ---");
        List<Project> byName = dao.findByName("Проект");
        byName.forEach(System.out::println);

        System.out.println("--- sortAllProjects() ---");
        List<Project> sorted = dao.sortAllProjects();
        sorted.forEach(System.out::println);
    }

    private static void testDesigionStatus() {
        DeaStaDao dao = new DeaStaDao(Desigion_Status.class);
        System.out.println("\n======= ПРОВЕРКА Desigion_Status =======");
        List<Desigion_Status> all = dao.findAll();
        System.out.println("--- findAll() ---");
        all.forEach(System.out::println);

        if (!all.isEmpty()) {
            Integer id = all.get(0).getId();
            System.out.println("--- findById(" + id + ") ---");
            Optional<Desigion_Status> found = dao.findById(id);
            System.out.println(found);
        }

        System.out.println("--- findByName('работе') ---");
        List<Desigion_Status> byName = dao.findByName("работе");
        byName.forEach(System.out::println);
    }
}