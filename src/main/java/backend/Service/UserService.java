package backend.Service;

import backend.Entity.User;
import backend.Repository.I_UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService implements I_UserService {

    @Autowired
    private I_UserRepository userRepository;

    //am scris un pattern standard pentru adresa de email
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");


    @Override
    public Optional<User> login(String email, String password) {
        if (email == null || password == null) {
            throw new RuntimeException("Email or password cannot be null");
        }

        Optional<User> userOpt = userRepository.findByEmailAndPassword(email, password);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        return userOpt;
    }

    @Override
    public User register(User user) {
        validateUser(user);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        return userRepository.save(user);
    }

    private void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new RuntimeException("Email cannot be null or empty");
        }

        // Format email
        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new RuntimeException("Invalid email format");
        }

        if (user.getType() == null) {
            throw new RuntimeException("User type must be specified");
        }

        if (user.getPassword() == null || user.getPassword().length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters");
        }

//        // Reguli speciale pe tip
//        switch (user.getType()) {
//            case TEACHER:
//                break;
//            case PUPIL:
//                break;
//            case ADMIN:
//                break;
//            case PARENT:
//                break;
//        }
    }

    @Override
    public Optional<User.Type> getUserType(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.map(User::getType);
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
    public User updateUser(String email, User user) {
        Optional<User> existingUserOpt = userRepository.findByEmail(email);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setName(user.getName());
            existingUser.setPassword(user.getPassword());
            existingUser.setType(user.getType());
            validateUser(user);
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void deleteUser(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("User with email " + email + " not found");
        }
        userRepository.deleteById(email);
    }
}
