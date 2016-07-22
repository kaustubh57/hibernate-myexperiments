package com.hibernate.myexperiments.dao;

import com.hibernate.myexperiments.HibernateUtil;
import com.hibernate.myexperiments.models.Employee;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by kaustubh on 7/22/16.
 */
public class EmployeeDao {
    public static Integer create(Employee e) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(e);
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully created " + e.toString());
        return e.getId();

    }

    public static List<Employee> read() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        @SuppressWarnings("unchecked")
        List<Employee> employees = session.createQuery("FROM Employee").list();
        session.close();
        System.out.println("Found " + employees.size() + " Employees");
        return employees;

    }

    public static void update(Employee e) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Employee em = (Employee) session.load(Employee.class, e.getId());
        em.setName(e.getName());
        em.setAge(e.getAge());
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully updated " + e.toString());

    }

    public static void delete(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Employee e = findByID(id);
        session.delete(e);
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully deleted " + e.toString());

    }

    public static Employee findByID(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Employee e = (Employee) session.load(Employee.class, id);
        session.close();
        return e;
    }

    public static void deleteAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Employee ");
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully deleted all employees.");

    }
}
