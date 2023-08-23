package lk.d24.hms.bo.custom;

import lk.d24.hms.bo.SuperBO;
import lk.d24.hms.dto.UserDTO;

public interface LoginBO extends SuperBO {
    String getPassword(String username);
    boolean saveUser(UserDTO userDTO);
    String getNextID();
    boolean verifyLogin(String username, String password);
}