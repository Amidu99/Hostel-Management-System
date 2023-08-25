package lk.d24.hms.dao.custom;

import lk.d24.hms.dao.CrudDAO;
import lk.d24.hms.entity.Student;

public interface StudentDAO extends CrudDAO<Student> {
    double getGenderCount(String gender);
}