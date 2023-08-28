package lk.d24.hms.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTM {
    private String userID;
    private String username;
    private String password;
}