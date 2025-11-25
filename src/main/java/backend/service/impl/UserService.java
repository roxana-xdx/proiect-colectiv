package backend.service.impl;

import backend.entity.Teacher;
import backend.entity.User;
import backend.entity.validation.UserValidator;
import backend.repository.I_UserRepository;
import backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService implements I_UserService {

    private final I_UserRepository userRepository;

    @Autowired
    public UserService(I_UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> login(String email, String password) {
        if (email == null || password == null) {
            throw new IllegalArgumentException("Email or password cannot be null");
        }
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return Optional.empty();

        User user = userOpt.get();
        if (!password.equals(user.getPassword())) return Optional.empty();
        return Optional.of(user);
    }

    @Override
    @Transactional
    public User register(User user) {
        UserValidator.validateRegister(user, userRepository);
        return userRepository.save(user);
    }

    @Override
    public Optional<User.Type> getUserType(String email) {
        return userRepository.findByEmail(email).map(User::getType);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User updateUser(String email, User user) {
        User existing = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        if (user.getName() != null && !user.getName().isBlank()) {
            existing.setName(user.getName());
        }
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existing.setPassword(user.getPassword());
        }

        // validate the resulting entity
        UserValidator.validateExisting(existing);

        return userRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new IllegalStateException("User with email " + email + " not found");
        }
        userRepository.deleteById(email);
    }
}