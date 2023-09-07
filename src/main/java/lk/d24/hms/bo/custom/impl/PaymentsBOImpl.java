package lk.d24.hms.bo.custom.impl;

import lk.d24.hms.bo.custom.PaymentsBO;
import lk.d24.hms.dao.DAOFactory;
import lk.d24.hms.dao.custom.ReservationDAO;
import lk.d24.hms.dto.ReservationDTO;
import lk.d24.hms.dto.RoomDTO;
import lk.d24.hms.dto.StudentDTO;
import lk.d24.hms.entity.Reservation;
import java.util.ArrayList;
import java.util.List;

public class PaymentsBOImpl implements PaymentsBO {
    ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);

    @Override
    public List<ReservationDTO> getAllPendingPayments() {
        List<ReservationDTO> allReservations = new ArrayList<>();
        List<Reservation> reservations = reservationDAO.getPaymentCounts("Not Paid");
        for (Reservation r : reservations){
            allReservations.add(new ReservationDTO(r.getReservation_id(), r.getDate(),
                    new StudentDTO(r.getStudent().getStudent_id(), r.getStudent().getName(), r.getStudent().getBirthday(), r.getStudent().getGender(), r.getStudent().getContact(), r.getStudent().getAddress()),
                    new RoomDTO(r.getRoom().getRoom_id(), r.getRoom().getType(), r.getRoom().getKey_money(), r.getRoom().getQty()), r.getPayment_status()));
        }
        return allReservations;
    }
}