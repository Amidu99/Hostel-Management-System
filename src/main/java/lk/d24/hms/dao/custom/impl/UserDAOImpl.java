package lk.d24.hms.dao.custom.impl;

import lk.d24.hms.dao.custom.UserDAO;
import lk.d24.hms.entity.User;
import lk.d24.hms.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User search(String id) {
        return null;
    }

    @Override
    public boolean add(User user) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("UserDAOImpl : " + e);
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(User user) {
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
            NativeQuery query = session.createNativeQuery("SELECT userID FROM user ORDER BY userID DESC LIMIT 1");
            String id = (String) query.uniqueResult();
            if (id != null) {
                int newId = Integer.parseInt(id.replace("U", "")) + 1;
                return String.format("U%03d", newId);
            }
            return "U001";
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
    public boolean verifyLogin(String username, String password) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            NativeQuery query = session.createNativeQuery("SELECT COUNT(*) FROM user WHERE username = :username AND password = :password");
            query.setParameter("username", username);
            query.setParameter("password", password);
            Long count = (Long) query.uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserDAOImpl : " + e);
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public String getPassword(String username) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            NativeQuery query = session.createNativeQuery("SELECT password FROM user WHERE username = :username");
            query.setParameter("username", username);
            String password = (String) query.uniqueResult();
            if(password!=null){
                return password;
            }else{
                return null;
            }
        }catch (Exception e) {
            System.out.println("Error in password : " + e);
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
    }
}