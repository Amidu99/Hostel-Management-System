package lk.d24.hms.dao.custom.impl;

import lk.d24.hms.dao.custom.StudentDAO;
import lk.d24.hms.entity.Student;
import lk.d24.hms.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public List<Student> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            String hql = "SELECT s FROM student s ORDER BY s.student_id DESC";
            Query query = session.createQuery(hql);
            List<Student> list = query.list();
            return list;
        } catch (Exception e) {
            System.out.println("StudentDAOImpl : " + e);
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public Student search(String id) {
        return null;
    }

    @Override
    public boolean add(Student student) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(student);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("StudentDAOImpl : " + e);
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Student newStudent) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Student student = session.load(Student.class, newStudent.getStudent_id());
        if (student == null) {
            session.close();
            return false;
        } else{
            student.setName(newStudent.getName());
            student.setBirthday(newStudent.getBirthday());
            student.setGender(newStudent.getGender());
            student.setContact(newStudent.getContact());
            student.setAddress(newStudent.getAddress());
            Transaction transaction = session.beginTransaction();
            session.update(student);
            transaction.commit();
            session.close();
            return true;
        }
    }

    @Override
    public boolean delete(String student_id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Student student = session.load(Student.class, student_id);
        if (student == null) {
            session.close();
            return false;
        } else {
            Transaction transaction = session.beginTransaction();
            session.delete(student);
            transaction.commit();
            session.close();
            return true;
        }
    }

    @Override
    public String generateNextID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            String hql = "SELECT s.student_id FROM student s ORDER BY s.student_id DESC";
            Query query = session.createQuery(hql);
            query.setMaxResults(1);
            String id = (String) query.uniqueResult();
            if (id != null) {
                int newId = Integer.parseInt(id.replace("S-", "")) + 1;
                return String.format("S-%04d", newId);
            }
            return "S-0001";
        } catch (Exception e) {
            System.out.println("UserDAOImpl : " + e);
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public double getGenderCount(String gender) {
        Session session = FactoryConfiguration.getInstance().getSession();
        String hql = "SELECT COUNT(*) FROM student s WHERE s.gender = :gender";
        Query query = session.createQuery(hql);
        query.setParameter("gender", gender);
        return (long) query.uniqueResult();
    }
}