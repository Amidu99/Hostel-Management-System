package lk.d24.hms.bo.custom;

import lk.d24.hms.bo.SuperBO;

public interface DashboardBO extends SuperBO {
    double getGenderCount(String male);
}