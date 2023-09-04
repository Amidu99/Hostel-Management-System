package lk.d24.hms.bo.custom.impl;

import lk.d24.hms.bo.custom.ReservationBO;
import lk.d24.hms.dao.DAOFactory;
import lk.d24.hms.dao.custom.ReservationDAO;
import lk.d24.hms.dao.custom.RoomDAO;
import lk.d24.hms.dao.custom.StudentDAO;
import lk.d24.hms.dto.ReservationDTO;
import lk.d24.hms.dto.RoomDTO;
import lk.d24.hms.dto.StudentDTO;
import lk.d24.hms.entity.Reservation;
import lk.d24.hms.entity.Room;
import lk.d24.hms.entity.Student;
import java.util.ArrayList;
import java.util.List;

public class ReservationBOImpl implements ReservationBO {
    RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);
    ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);

    @Override
    public List<ReservationDTO> getAllReservations() {
        List<ReservationDTO> allReservations = new ArrayList<>();
        List<Reservation> reservations = reservationDAO.getAll();
        for (Reservation r : reservations){
            allReservations.add(new ReservationDTO(r.getReservation_id(), r.getDate(),
            new StudentDTO(r.getStudent().getStudent_id(), r.getStudent().getName(), r.getStudent().getBirthday(), r.getStudent().getGender(), r.getStudent().getContact(), r.getStudent().getAddress()),
            new RoomDTO(r.getRoom().getRoom_id(), r.getRoom().getType(), r.getRoom().getKey_money(), r.getRoom().getQty()), r.getPayment_status()));
        }
        return allReservations;
    }

    @Override
    public boolean saveReservation(ReservationDTO r) {
        return reservationDAO.add(new Reservation(r.getReservation_id(), r.getDate(),
            new Student(r.getStudentDTO().getStudent_id(), r.getStudentDTO().getName(), r.getStudentDTO().getBirthday(), r.getStudentDTO().getGender(), r.getStudentDTO().getContact(), r.getStudentDTO().getAddress()),
            new Room(r.getRoomDTO().getRoom_id(), r.getRoomDTO().getType(), r.getRoomDTO().getKey_money(), r.getRoomDTO().getQty()), r.getPayment_status()));
    }

    @Override
    public boolean updateReservation(ReservationDTO r) {
        return reservationDAO.update(new Reservation(r.getReservation_id(), r.getDate(),
                new Student(r.getStudentDTO().getStudent_id(), r.getStudentDTO().getName(), r.getStudentDTO().getBirthday(), r.getStudentDTO().getGender(), r.getStudentDTO().getContact(), r.getStudentDTO().getAddress()),
                new Room(r.getRoomDTO().getRoom_id(), r.getRoomDTO().getType(), r.getRoomDTO().getKey_money(), r.getRoomDTO().getQty()), r.getPayment_status()));
    }

    @Override
    public boolean deleteReservation(String reservation_id) {
        return reservationDAO.delete(reservation_id);
    }

    @Override
    public RoomDTO getRoom(String room_id) {
        Room room = roomDAO.search(room_id);
        if(room!=null){
            return new RoomDTO(room.getRoom_id(), room.getType(), room.getKey_money(), room.getQty());
        }else{
            return null;
        }
    }

    @Override
    public String getNextReservationID() {
        return reservationDAO.generateNextID();
    }

    @Override
    public StudentDTO getStudent(String student_id) {
        Student student = studentDAO.search(student_id);
        if(student!=null){
            return new StudentDTO(student.getStudent_id(), student.getName(), student.getBirthday(), student.getGender(), student.getContact(), student.getAddress());
        }else{
            return null;
        }
    }
}