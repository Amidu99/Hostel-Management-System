package lk.d24.hms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "reservation")
public class Reservation {
    @Id
    private String reservation_id;
    private LocalDate date;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private Student student;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private Room room;

    private String payment_status;
}