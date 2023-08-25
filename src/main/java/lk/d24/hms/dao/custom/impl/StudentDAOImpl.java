package lk.d24.hms.dao.custom.impl;

import lk.d24.hms.dao.custom.StudentDAO;
import lk.d24.hms.entity.Student;
import lk.d24.hms.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public List<Student> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "FROM student s ORDER BY s.student_id DESC";
            Query query = session.createQuery(hql);
            List<Student> list= query.list();
            return list;
        }catch (Exception e) {
            System.out.println("StudentDAOImpl : " + e);
            transaction.rollback();
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
        }catch (Exception e) {
            System.out.println("StudentDAOImpl : " + e);
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Student student) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public String generateNextID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            NativeQuery query = session.createNativeQuery("SELECT student_id FROM student ORDER BY student_id DESC LIMIT 1");
            String id = (String) query.uniqueResult();
            if (id != null) {
                int newId = Integer.parseInt(id.replace("S-", "")) + 1;
                return String.format("S-%04d", newId);
            }
            return "S-0001";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserDAOImpl : " + e);
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public double getGenderCount(String gender) {
        return 0;
    }
}