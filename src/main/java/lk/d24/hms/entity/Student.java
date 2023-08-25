package lk.d24.hms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="student")
public class Student {
    @Id
    private String student_id;
    private String name;
    private LocalDate birthday;
    private String gender;
    private String contact;
    private String address;
}