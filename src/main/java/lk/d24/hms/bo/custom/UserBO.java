package lk.d24.hms.bo.custom;

import lk.d24.hms.bo.SuperBO;
import lk.d24.hms.dto.UserDTO;
import java.util.List;

public interface UserBO extends SuperBO {
    String getNextUserID();
    boolean saveUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    boolean deleteUser(String userID);
    boolean updateUser(UserDTO userDTO);
}