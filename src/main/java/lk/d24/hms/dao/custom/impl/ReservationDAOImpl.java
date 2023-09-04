package lk.d24.hms.dao.custom.impl;

import lk.d24.hms.dao.custom.ReservationDAO;
import lk.d24.hms.entity.Reservation;
import lk.d24.hms.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {
    @Override
    public List<Reservation> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            String hql = "SELECT r FROM reservation r ORDER BY r.reservation_id DESC";
            Query query = session.createQuery(hql);
            List<Reservation> reservationList = query.list();
            return reservationList;
        } catch (Exception e) {
            System.out.println("ReservationDAOImpl : " + e);
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public Reservation search(String reservation_id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Reservation reservation = session.load(Reservation.class, reservation_id);
        session.close();
        return reservation;
    }

    @Override
    public boolean add(Reservation reservation) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(reservation);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("ReservationDAOImpl : " + e);
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Reservation new_reservation) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Reservation reservation = session.merge(new_reservation);
            if (reservation != null) {
                session.update(reservation);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            System.out.println("ReservationDAOImpl : " + e);
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String reservation_id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Reservation reservation = session.get(Reservation.class, reservation_id);
            if (reservation != null) {
                reservation.setRoom(null);
                reservation.setStudent(null);
                Transaction transaction = session.beginTransaction();
                session.delete(reservation);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("ReservationDAOImpl : " + e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public String generateNextID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            String hql = "SELECT r.reservation_id FROM reservation r ORDER BY r.reservation_id DESC";
            Query query = session.createQuery(hql);
            query.setMaxResults(1);
            String id = (String) query.uniqueResult();
            if (id != null) {
                int newId = Integer.parseInt(id.replace("RE-", "")) + 1;
                return String.format("RE-%04d", newId);
            }
            return "RE-0001";
        } catch (Exception e) {
            System.out.println("ReservationDAOImpl : " + e);
            return null;
        } finally {
            session.close();
        }
    }
}