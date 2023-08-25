package lk.d24.hms.bo.custom.impl;

import lk.d24.hms.bo.custom.StudentBO;
import lk.d24.hms.dao.DAOFactory;
import lk.d24.hms.dao.custom.StudentDAO;
import lk.d24.hms.dto.StudentDTO;
import lk.d24.hms.entity.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    @Override
    public List<StudentDTO> getAllStudents() {
        List<StudentDTO> allStudents = new ArrayList<>();
        List<Student> students = studentDAO.getAll();
        for (Student s : students){
            allStudents.add(new StudentDTO(s.getStudent_id(), s.getName(), s.getBirthday(), s.getGender(), s.getContact(), s.getAddress()));
        }
        return allStudents;
    }

    @Override
    public boolean saveStudent(StudentDTO s) {
        return studentDAO.add(new Student(s.getStudent_id(), s.getName(), s.getBirthday(), s.getGender(), s.getContact(), s.getAddress()));
    }
}