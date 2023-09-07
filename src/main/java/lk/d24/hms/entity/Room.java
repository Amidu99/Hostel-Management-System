package lk.d24.hms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="room")
public class Room{
    @Id
    private String room_id;
    private String type;
    private double key_money;
    private int qty;

    @ToString.Exclude
    @OneToMany(mappedBy = "room",targetEntity = Reservation.class)
    private List<Reservation> reservationList = new ArrayList<>();

    public Room(String room_id, String type, double key_money, int qty) {
        this.room_id = room_id;
        this.type = type;
        this.key_money = key_money;
        this.qty = qty;
    }
}