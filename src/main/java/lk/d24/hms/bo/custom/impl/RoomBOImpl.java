package lk.d24.hms.bo.custom.impl;

import lk.d24.hms.bo.custom.RoomBO;
import lk.d24.hms.dao.DAOFactory;
import lk.d24.hms.dao.custom.RoomDAO;
import lk.d24.hms.dto.RoomDTO;
import lk.d24.hms.entity.Room;
import java.util.ArrayList;
import java.util.List;

public class RoomBOImpl implements RoomBO {
    RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);
    @Override
    public List<RoomDTO> getAllRooms() {
        List<RoomDTO> allRooms = new ArrayList<>();
        List<Room> rooms = roomDAO.getAll();
        for (Room r : rooms){
            allRooms.add(new RoomDTO(r.getRoom_id(), r.getType(), r.getKey_money(), r.getQty()));
        }
        return allRooms;
    }

    @Override
    public boolean addRoom(RoomDTO roomDTO) {
        return roomDAO.add(new Room(roomDTO.getRoom_id(), roomDTO.getType(), roomDTO.getKey_money(), roomDTO.getQty()));
    }

    @Override
    public boolean updateRoom(RoomDTO roomDTO) {
        return roomDAO.update(new Room(roomDTO.getRoom_id(), roomDTO.getType(), roomDTO.getKey_money(), roomDTO.getQty()));
    }

    @Override
    public boolean deleteRoom(String room_id) {
        return roomDAO.delete(room_id);
    }
}