package lk.d24.hms.bo.custom.impl;

import lk.d24.hms.bo.custom.UserBO;
import lk.d24.hms.dao.DAOFactory;
import lk.d24.hms.dao.custom.UserDAO;
import lk.d24.hms.dto.UserDTO;
import lk.d24.hms.entity.User;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public String getNextUserID() {
        return userDAO.generateNextID();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> allUsers = new ArrayList<>();
        List<User> users = userDAO.getAll();
        for (User user : users){
            allUsers.add(new UserDTO(user.getUserID(), user.getUsername(), user.getPassword()));
        }
        return allUsers;
    }

    @Override
    public boolean saveUser(UserDTO userDTO) {
        return userDAO.add(new User(userDTO.getUserID(), userDTO.getUsername(), userDTO.getPassword()));
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        return userDAO.update(new User(userDTO.getUserID(), userDTO.getUsername(), userDTO.getPassword()));
    }

    @Override
    public boolean deleteUser(String userID) {
        return userDAO.delete(userID);
    }
}