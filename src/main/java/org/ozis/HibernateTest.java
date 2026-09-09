package org.ozis;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.ozis.util.HibernateUtil;

import java.util.List;
import java.util.Scanner;

public class HibernateTest {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        // Получаем фабрику сессий
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        System.out.println("✅ SessionFactory создана успешно!");

        try (Session session = sessionFactory.openSession()) {
            System.out.println("✅ Соединение с БД установлено!");

            // Используем имя класса Task_status (с подчёркиванием)
            Query<Object[]> query = session.createQuery(
                    "SELECT ts.id, ts.name FROM Task_Status ts", Object[].class
            );
            List<Object[]> results = query.list();

            System.out.println("✅ Запрос выполнен. Найдено записей: " + results.size());
            for (Object[] row : results) {
                System.out.println("  id: " + row[0] + ", name: " + row[1]);
            }

            System.out.println("✅ Тест прошёл успешно!");
        } catch (Exception e) {
            System.err.println("❌ Ошибка при работе с БД:");
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}