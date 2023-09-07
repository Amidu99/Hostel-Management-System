package lk.d24.hms.dao.custom;

import lk.d24.hms.dao.CrudDAO;
import lk.d24.hms.entity.Reservation;
import java.util.List;

public interface ReservationDAO extends CrudDAO<Reservation> {
    List<Reservation> getPaymentCounts(String status);
    double getRoomTypeFilledCount(String room_id);
}