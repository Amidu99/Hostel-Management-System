package lk.d24.hms.dao.custom.impl;

import lk.d24.hms.dao.custom.RoomDAO;
import lk.d24.hms.entity.Room;
import lk.d24.hms.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    @Override
    public List<Room> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            String hql = "SELECT r FROM room r ORDER BY r.room_id DESC";
            Query query = session.createQuery(hql);
            List<Room> roomList = query.list();
            return roomList;
        } catch (Exception e) {
            System.out.println("RoomDAOImpl : " + e);
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean add(Room room) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(room);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("RoomDAOImpl : " + e);
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Room newRoom) {
        return false;
    }

    @Override
    public boolean delete(String room_id) {
        return false;
    }

    @Override
    public String generateNextID() {
        return null;
    }

    @Override
    public Room search(String room_id) {
        return null;
    }
}