package lk.d24.hms.dao.custom.impl;

import lk.d24.hms.dao.custom.RoomDAO;
import lk.d24.hms.entity.Reservation;
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
        Session session = FactoryConfiguration.getInstance().getSession();
        Room room = session.load(Room.class, newRoom.getRoom_id());
        if (room == null) {
            session.close();
            return false;
        } else{
            room.setType(newRoom.getType());
            room.setKey_money(newRoom.getKey_money());
            room.setQty(newRoom.getQty());
            Transaction transaction = session.beginTransaction();
            session.update(room);
            transaction.commit();
            session.close();
            return true;
        }
    }

    @Override
    public boolean delete(String room_id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Room room = session.load(Room.class, room_id);
            List<Reservation> reservationList = room.getReservationList();
            for (Reservation reservation : reservationList) {
                reservation.setRoom(null);
                session.delete(reservation);
            }
            session.delete(room);
            Transaction transaction = session.beginTransaction();
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("RoomDAOImpl : " + e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public String generateNextID() {
        return null;
    }

    @Override
    public Room search(String room_id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            String hql = "SELECT r FROM room r WHERE r.room_id = :room_id";
            Query<Room> query = session.createQuery(hql, Room.class);
            query.setParameter("room_id", room_id);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }

    @Override
    public double getRoomTypeCount(String room_id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Room room = session.load(Room.class, room_id);
        if (room == null) {
            session.close();
            return 0;
        } else{
            int count = room.getQty();
            session.close();
            return count;
        }
    }
}