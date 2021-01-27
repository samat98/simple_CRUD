package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    User findUser(Long id);
    List<User> listUsers();
    void deleteUser(Long id);
    void updateUser(User user);
}
