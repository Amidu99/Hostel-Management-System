package lk.d24.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomDTO {
    private String room_id;
    private String type;
    private double key_money;
    private int qty;
}