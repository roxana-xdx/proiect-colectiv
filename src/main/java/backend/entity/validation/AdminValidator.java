package backend.entity.validation;

import backend.entity.User;
import backend.repository.I_AdminRepository;
import backend.repository.I_UserRepository;

import java.util.regex.Pattern;

public final class AdminValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private AdminValidator() { /* utility class */ }

    /**
     * Validate requirements for creating an Admin row.
     * - email non-null, matches pattern
     * - User with email exists
     * - User type is ADMIN
     * - Admin row not already present for this email
     */
    public static void validateCreate(String email, I_UserRepository userRepo, I_AdminRepository adminRepo) {
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
        if (user.getType() != User.Type.ADMIN) {
            throw new IllegalStateException("User must be of type ADMIN to create Admin record");
        }
        if (adminRepo.existsByUser_Email(email)) {
            throw new IllegalStateException("Admin profile already exists for email: " + email);
        }
    }
}