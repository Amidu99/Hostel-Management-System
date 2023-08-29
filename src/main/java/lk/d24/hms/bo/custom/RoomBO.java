package lk.d24.hms.bo.custom;

import lk.d24.hms.bo.SuperBO;
import lk.d24.hms.dto.RoomDTO;
import java.util.List;

public interface RoomBO extends SuperBO {
    List<RoomDTO> getAllRooms();
}