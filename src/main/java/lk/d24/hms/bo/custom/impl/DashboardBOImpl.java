package lk.d24.hms.bo.custom.impl;

import lk.d24.hms.bo.custom.DashboardBO;
import lk.d24.hms.dao.DAOFactory;
import lk.d24.hms.dao.custom.StudentDAO;

public class DashboardBOImpl implements DashboardBO {
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    @Override
    public double getGenderCount(String gender) {
        return studentDAO.getGenderCount(gender);
    }
}