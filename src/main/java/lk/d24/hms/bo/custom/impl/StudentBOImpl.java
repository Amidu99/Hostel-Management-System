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

    @Override
    public String getNextStudentID() {
        return studentDAO.generateNextID();
    }

    @Override
    public boolean updateStudent(StudentDTO s) {
        return studentDAO.update(new Student(s.getStudent_id(), s.getName(), s.getBirthday(), s.getGender(), s.getContact(), s.getAddress()));
    }

    @Override
    public boolean deleteStudent(String student_id) {
        return studentDAO.delete(student_id);
    }

    @Override
    public StudentDTO searchStudent(String student_id) {
        Student s = studentDAO.search(student_id);
        if(s!=null){
            return new StudentDTO(s.getStudent_id(), s.getName(), s.getBirthday(), s.getGender(), s.getContact(), s.getAddress());
        }
        return null;
    }
}