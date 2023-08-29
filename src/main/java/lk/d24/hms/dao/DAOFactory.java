package lk.d24.hms.dao;

import lk.d24.hms.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        RESERVATION, ROOM, STUDENT, USER
    }

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
//            case RESERVATION:
//                return ReservationDaoImpl();
            case ROOM:
                return new RoomDAOImpl();
            case STUDENT:
                return new StudentDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }
}