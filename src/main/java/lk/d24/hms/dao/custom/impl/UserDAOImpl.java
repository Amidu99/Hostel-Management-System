package lk.d24.hms.dao.custom.impl;

import lk.d24.hms.dao.custom.UserDAO;
import lk.d24.hms.entity.User;
import lk.d24.hms.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public List<User> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            String hql = "SELECT u FROM user u ORDER BY u.userID DESC";
            Query query = session.createQuery(hql);
            List<User> list = query.list();
            return list;
        } catch (Exception e) {
            System.out.println("UserDAOImpl : " + e);
            return null;
        } finally {
            session.close();
        }
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
    public boolean update(User newUser) {
        Session session = FactoryConfiguration.getInstance().getSession();
        User user = session.load(User.class, newUser.getUserID());
        if (user == null) {
            session.close();
            return false;
        } else{
            user.setUsername(newUser.getUsername());
            user.setPassword(newUser.getPassword());
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            session.close();
            return true;
        }
    }

    @Override
    public boolean delete(String userID) {
        Session session = FactoryConfiguration.getInstance().getSession();
        User user = session.load(User.class, userID);
        if (user == null) {
            session.close();
            return false;
        } else {
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
            session.close();
            return true;
        }
    }

    @Override
    public String generateNextID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            String hql = "SELECT u.userID FROM user u ORDER BY u.userID DESC";
            Query query = session.createQuery(hql);
            query.setMaxResults(1);
            String id = (String) query.uniqueResult();
            if (id != null) {
                int newId = Integer.parseInt(id.replace("U-", "")) + 1;
                return String.format("U-%03d", newId);
            }
            return "U-001";
        } catch (Exception e) {
            System.out.println("UserDAOImpl : " + e);
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean verifyLogin(String username, String password) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            String hql = "SELECT COUNT(*) FROM user u WHERE u.username = :username AND u.password = :password";
            Query query = session.createQuery(hql);
            query.setParameter("username", username);
            query.setParameter("password", password);
            Long count = (Long) query.uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserDAOImpl : " + e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public String getPassword(String username) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            String hql = "SELECT u.password FROM user u WHERE u.username = :username";
            Query query = session.createQuery(hql);
            query.setParameter("username", username);
            return (String) query.uniqueResult();
        } catch (Exception e) {
            System.out.println("Error in password : " + e);
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public User search(String userID) {
        Session session = FactoryConfiguration.getInstance().getSession();
        User user = session.load(User.class, userID);
        session.close();
        return user;
    }
}