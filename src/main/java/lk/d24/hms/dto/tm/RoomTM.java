package lk.d24.hms.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomTM {
    private String room_id;
    private String type;
    private double key_money;
    private int qty;
}