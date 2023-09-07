package lk.d24.hms.bo.custom;

import lk.d24.hms.bo.SuperBO;
import lk.d24.hms.dto.RoomDTO;
import java.util.List;

public interface RoomBO extends SuperBO {
    List<RoomDTO> getAllRooms();
    boolean addRoom(RoomDTO roomDTO);
    boolean updateRoom(RoomDTO roomDTO);
    boolean deleteRoom(String room_id);
}