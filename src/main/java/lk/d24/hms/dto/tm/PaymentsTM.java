package lk.d24.hms.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentsTM {
    private String reservation_id;
    private LocalDate date;
    private String room_id;
    private double key_money;
    private String student_id;
    private String name;
}