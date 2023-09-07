package lk.d24.hms.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lk.d24.hms.entity.Reservation;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private String student_id;
    private String name;
    private LocalDate birthday;
    private String gender;
    private String contact;
    private String address;

    @ToString.Exclude
    private List<Reservation> list = new ArrayList<>();

    public StudentDTO(String student_id, String name, LocalDate birthday, String gender, String contact, String address) {
        this.student_id = student_id;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.contact = contact;
        this.address = address;
    }
}