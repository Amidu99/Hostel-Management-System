package lk.d24.hms.dao.custom.impl;

import lk.d24.hms.dao.custom.RoomDAO;
import lk.d24.hms.entity.Room;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    @Override
    public List<Room> getAll() {
        return null;
    }

    @Override
    public Room search(String id) {
        return null;
    }

    @Override
    public boolean add(Object entity) {
        return false;
    }

    @Override
    public boolean update(Object entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public String generateNextID() {
        return null;
    }
}