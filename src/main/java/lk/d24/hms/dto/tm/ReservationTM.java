package lk.d24.hms.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationTM {
    private String reservation_id;
    private LocalDate date;
    private String student_id;
    private String name;
    private String room_id;
    private String payment_status;
}