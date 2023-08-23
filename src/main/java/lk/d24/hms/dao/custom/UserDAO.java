package lk.d24.hms.dao.custom;

import lk.d24.hms.dao.CrudDAO;
import lk.d24.hms.entity.User;

public interface UserDAO extends CrudDAO<User> {
    boolean verifyLogin(String username, String password);
    String getPassword(String username);
}