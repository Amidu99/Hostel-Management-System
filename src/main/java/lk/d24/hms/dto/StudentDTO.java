package lk.d24.hms.dto;

import java.time.LocalDate;
import lombok.*;

@Data
@AllArgsConstructor
public class StudentDTO {
    private String student_id;
    private String name;
    private LocalDate birthday;
    private String gender;
    private String contact;
    private String address;
}