package backend.service;

import backend.entity.User;
import backend.repository.I_UserRepository;
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
        validateUser(user);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalStateException("Email already registered");
        }
        return userRepository.save(user);
    }

    private void validateUser(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (user.getType() == null) {
            throw new IllegalArgumentException("User type must be specified");
        }
        if (user.getPassword() == null || user.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
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
        validateUser(existing);
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