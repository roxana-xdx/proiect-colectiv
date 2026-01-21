package backend.service;

import backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface I_UserService {
    Optional<User> login(String email, String password);
    User register(User user);
    Optional<User.Type> getUserType(String email);
    List<User> getAllUsers();
    Optional<User> getUserByEmail(String email);
    User updateUser(String email, User user);
    void deleteUser(String email);
}