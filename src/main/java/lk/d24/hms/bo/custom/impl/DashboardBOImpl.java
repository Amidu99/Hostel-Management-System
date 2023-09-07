package lk.d24.hms.bo.custom.impl;

import lk.d24.hms.bo.custom.DashboardBO;
import lk.d24.hms.dao.DAOFactory;
import lk.d24.hms.dao.custom.ReservationDAO;
import lk.d24.hms.dao.custom.RoomDAO;
import lk.d24.hms.dao.custom.StudentDAO;
import lk.d24.hms.entity.Reservation;
import java.util.List;

public class DashboardBOImpl implements DashboardBO {
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);
    ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);

    @Override
    public double getGenderCount(String gender) {
        return studentDAO.getGenderCount(gender);
    }

    @Override
    public double getRoomTypeCount(String room_id) {
        return roomDAO.getRoomTypeCount(room_id);
    }

    @Override
    public int getPaymentCounts(String status) {
        List<Reservation> list = reservationDAO.getPaymentCounts(status);
        int count = 0;
        for (Reservation reservation : list){
            count++;
        }
        return count;
    }

    @Override
    public int getRoomTypeFilledCount(String room_id) {
        return (int) reservationDAO.getRoomTypeFilledCount(room_id);
    }
}