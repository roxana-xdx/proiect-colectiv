package backend.entity.validation;

import backend.entity.User;
import backend.repository.I_ParentRepository;
import backend.repository.I_UserRepository;

import java.util.regex.Pattern;

public class ParentValidator {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private ParentValidator() {}

    public static void validateCreate(String email, I_UserRepository userRepo, I_ParentRepository parentRepo) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        var userOpt = userRepo.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalStateException("No user found with email: " + email);
        }
        var user = userOpt.get();
        if (user.getType() != User.Type.PARENT) {
            throw new IllegalStateException("User must be of type PARENT to create Parent record");
        }
        if (parentRepo.existsByEmail(email)) {
            throw new IllegalStateException("Parent profile already exists for email: " + email);
        }
    }
}
