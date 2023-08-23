package lk.d24.hms.bo.custom.impl;

import lk.d24.hms.bo.custom.LoginBO;
import lk.d24.hms.dao.DAOFactory;
import lk.d24.hms.dao.custom.UserDAO;
import lk.d24.hms.dto.UserDTO;
import lk.d24.hms.entity.User;

public class LoginBOImpl implements LoginBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public String getPassword(String username) {
        return userDAO.getPassword(username);
    }

    @Override
    public boolean saveUser(UserDTO userDTO) {
        return userDAO.add(new User(userDTO.getUserID(), userDTO.getUsername(), userDTO.getPassword()));
    }

    @Override
    public String getNextID() {
        return userDAO.generateNextID();
    }

    @Override
    public boolean verifyLogin(String username, String password) {
        return userDAO.verifyLogin(username, password);
    }
}