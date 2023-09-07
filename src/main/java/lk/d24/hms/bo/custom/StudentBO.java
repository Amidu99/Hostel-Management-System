package lk.d24.hms.bo.custom;

import lk.d24.hms.bo.SuperBO;
import lk.d24.hms.dto.StudentDTO;
import java.util.List;

public interface StudentBO extends SuperBO {
    List<StudentDTO> getAllStudents();
    boolean saveStudent(StudentDTO studentDTO);
    String getNextStudentID();
    boolean updateStudent(StudentDTO studentDTO);
    boolean deleteStudent(String student_id);
    StudentDTO searchStudent(String student_id);
}