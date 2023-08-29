package lk.d24.hms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="room")
public class Room{
    @Id
    private String room_id;
    private String type;
    private double key_money;
    private int qty;
}