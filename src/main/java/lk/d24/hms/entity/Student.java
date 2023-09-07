package lk.d24.hms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @ToString.Exclude
    @OneToMany(mappedBy = "student",targetEntity = Reservation.class)
    private List<Reservation> reservationList = new ArrayList<>();

    public Student(String student_id, String name, LocalDate birthday, String gender, String contact, String address) {
        this.student_id = student_id;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.contact = contact;
        this.address = address;
    }
}