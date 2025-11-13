package backend.service.impl;

import backend.entity.User;
import backend.entity.validation.UserValidator;
import backend.repository.I_UserRepository;
import backend.service.I_AdminService;
import backend.service.I_UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService implements I_UserService {

    @Autowired
    private I_UserRepository userRepository;

    // This will be added for every new user type registration
    @Autowired
    private I_AdminService adminService;

    // (kept for potential local use; validation moved to UserValidator)
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

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
        // validation moved to UserValidator
        UserValidator.validateRegister(user, userRepository);

        User saved = userRepository.save(user);

        // If the user is an admin, create an admin profile
        // This lines below will be added for every new user type registration
        if (saved.getType() == User.Type.ADMIN) {
            adminService.createAdminByEmail(saved.getEmail());
        }

        return saved;
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
        if (user.getType() != null) {
            existing.setType(user.getType());
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