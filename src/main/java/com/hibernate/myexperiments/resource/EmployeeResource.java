package com.hibernate.myexperiments.resource;

import com.hibernate.myexperiments.dao.EmployeeDao;
import com.hibernate.myexperiments.models.Employee;

import java.util.List;

/**
 * Created by kaustubh on 7/22/16.
 */
public class EmployeeResource {

    public static void testEmployeeData() {
        Employee em1 = new Employee("Mary Smith", 25);
        Employee em2 = new Employee("John Aces", 32);
        Employee em3 = new Employee("Ian Young", 29);

        System.out.println(" =======CREATE =======");
        EmployeeDao.create(em1);
        EmployeeDao.create(em2);
        EmployeeDao.create(em3);
        System.out.println(" =======READ =======");
        List<Employee> ems1 = EmployeeDao.read();
        for(Employee e: ems1) {
            System.out.println(e.toString());
        }
        System.out.println(" =======UPDATE =======");
        em1.setAge(44);
        em1.setName("Mary Rose");
        EmployeeDao.update(em1);
        System.out.println(" =======READ =======");
        List<Employee> ems2 = EmployeeDao.read();
        for(Employee e: ems2) {
            System.out.println(e.toString());
        }
        System.out.println(" =======DELETE ======= ");
        EmployeeDao.delete(em2.getId());
        System.out.println(" =======READ =======");
        List<Employee> ems3 = EmployeeDao.read();
        for(Employee e: ems3) {
            System.out.println(e.toString());
        }
        System.out.println(" =======DELETE ALL ======= ");
        EmployeeDao.deleteAll();
        System.out.println(" =======READ =======");
        List<Employee> ems4 = EmployeeDao.read();
        for(Employee e: ems4) {
            System.out.println(e.toString());
        }
    }
}
