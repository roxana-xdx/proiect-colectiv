package backend.entity.validation;

import backend.entity.User;
import backend.repository.I_UserRepository;

import java.util.regex.Pattern;

/**
 * Validator helper pentru User. Conține validări folosite la înregistrare și update.
 * Throw RuntimeException (IllegalArgumentException / IllegalStateException) on invalid input.
 */
public final class UserValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private UserValidator() {}

    /**
     * Validate user for registration.
     * - checks basic fields and that email is not already registered (uses repository).
     */
    public static void validateRegister(User user, I_UserRepository userRepository) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        validateCommonFields(user);

        if (userRepository != null && user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalStateException("Email already registered");
        }
    }

    /**
     * Validate an existing (possibly updated) User before saving.
     * Use this after applying partial updates to an existing entity (so email stays the same).
     */
    public static void validateExisting(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        validateCommonFields(user);
    }

    /**
     * Common field validations used by both register and update.
     */
    private static void validateCommonFields(User user) {
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
}