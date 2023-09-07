package lk.d24.hms.dto;

import lk.d24.hms.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private String room_id;
    private String type;
    private double key_money;
    private int qty;

    @ToString.Exclude
    private List<Reservation> reservationList = new ArrayList<>();

    public RoomDTO(String room_id, String type, double key_money, int qty) {
        this.room_id = room_id;
        this.type = type;
        this.key_money = key_money;
        this.qty = qty;
    }
}