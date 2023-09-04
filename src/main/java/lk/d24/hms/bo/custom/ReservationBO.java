package lk.d24.hms.bo.custom;

import lk.d24.hms.bo.SuperBO;
import lk.d24.hms.dto.ReservationDTO;
import lk.d24.hms.dto.RoomDTO;
import lk.d24.hms.dto.StudentDTO;
import java.util.List;

public interface ReservationBO extends SuperBO {
    RoomDTO getRoom(String room_id);
    String getNextReservationID();
    StudentDTO getStudent(String student_id);
    List<ReservationDTO> getAllReservations();
    boolean saveReservation(ReservationDTO reservationDTO);
    boolean deleteReservation(String reservation_id);
    boolean updateReservation(ReservationDTO reservationDTO);
}