package lk.d24.hms.bo;

import lk.d24.hms.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        DASHBOARD, LOGIN, ROOM, STUDENT, USER, RESERVATION, PAYMENT
    }

    public SuperBO getBO(BOFactory.BOTypes type) {
        switch (type) {
            case DASHBOARD:
                return new DashboardBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            case ROOM:
                return new RoomBOImpl();
            case STUDENT:
                return new StudentBOImpl();
            case RESERVATION:
                return new ReservationBOImpl();
            case PAYMENT:
                return new PaymentsBOImpl();
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }
}