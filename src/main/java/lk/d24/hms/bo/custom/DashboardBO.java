package lk.d24.hms.bo.custom;

import lk.d24.hms.bo.SuperBO;

public interface DashboardBO extends SuperBO {
    double getGenderCount(String male);
    double getRoomTypeCount(String room_id);
    int getPaymentCounts(String status);
    int getRoomTypeFilledCount(String s);
}