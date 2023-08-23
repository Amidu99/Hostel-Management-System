package lk.d24.hms.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class UserDTO {
    private String userID;
    private String username;
    private String password;
}