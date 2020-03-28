package kg;

import kg.entities.Employee;
import kg.entities.EmployeeAddress;
import kg.entities.EmployeeDepartment;
import kg.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class App {
    public static void main(String[] args) {
        Employee e1 = new Employee("Aidin", 21);
        Employee e2 = new Employee("Azat", 32);
        Employee e3 = new Employee("Askhat", 25);
        EmployeeAddress address = new EmployeeAddress("Bishkek");
        EmployeeDepartment department = new EmployeeDepartment("IT");
        e3.setEmployeeAddress(address);
        e3.setEmployeeDepartment(department);
        create(department);
        create(address);
        create(e3);
        System.out.println(getAllByNameAndAge("A",20));
        System.out.println(getAllEmployee());
    }

    public static <T> void create(T e) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(e);
        session.getTransaction().commit();
        session.close();
        System.out.println("Created");
    }

    public static void deleteEmployee(Employee e) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(e);
        session.getTransaction().commit();
        session.close();
        System.out.println("Employee deleted");
    }

    public static void updateEmployee(Employee e) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(e);
        session.getTransaction().commit();
        session.close();
        System.out.println("Employee updated");
    }

    public static Employee getEmployeebyId(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Employee e = (Employee) session.get(Employee.class, id);
        session.close();
        return e;
    }

    public static List<Employee> getAllEmployee() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employee> employees = session.createQuery("FROM Employee").list();
        session.close();
        return employees;
    }

    public static List<Employee> getAllByName(String name){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employee> employees = session.createQuery("select e FROM Employee e where name = :p_name")
                .setParameter("p_name", name)
                .list();
        session.close();
        System.out.println("Found: "+employees.size()+"employees");
        return employees;
    }

    public static List<Employee> getAllByNameAndAge(String name,int age){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employee> result = session.createQuery("select e FROM Employee e where name like :p_name and age= :p_age" )
                .setParameter("p_name", name+"%")
                .setParameter("p_age", age)
                .list();
        session.close();
        System.out.println("Found: "+result.size()+" employees");
        return result;
    }

    public static List<Employee> getAllByDepartment(Integer departmentId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employee> result = session.createQuery("select e FROM Employee e where e.employeeDepartment.id=:p_dept_id" )
                .setParameter("p_dept_id", departmentId)
                .list();
        session.close();
        System.out.println("Found: "+result.size()+" employees");
        return result;
    }
}