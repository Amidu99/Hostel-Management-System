package lk.d24.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private String reservation_id;
    private LocalDate date;

    @ToString.Exclude
    private StudentDTO studentDTO;

    @ToString.Exclude
    private RoomDTO roomDTO;

    private String payment_status;
}