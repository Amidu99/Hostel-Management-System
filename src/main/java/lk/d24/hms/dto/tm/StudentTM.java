package lk.d24.hms.dto.tm;

import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class StudentTM {
        String student_id;
        private String name;
        private LocalDate birthday;
        private String gender;
        private String contact;
        private String address;
}