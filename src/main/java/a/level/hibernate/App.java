package a.level.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import a.level.hibernate.entity.Student;
import a.level.hibernate.util.HibernateUtil;

public class App {
    public static void main(String[] args) {

        Student student = new Student("Sherlock", "Holmes", "sherlock@holmes.com");
        Student student1 = new Student("John", "Watson", "john@watson.com");
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student objects
            session.save(student);
            session.save(student1);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List < Student > students = session.createQuery("from Student", Student.class).list();
            students.forEach(s -> System.out.println(s.getFirstName()));
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
