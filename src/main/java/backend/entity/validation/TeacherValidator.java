package backend.entity.validation;

import backend.entity.User;
import backend.repository.I_TeacherRepository;
import backend.repository.I_UserRepository;

import java.util.regex.Pattern;

public final class TeacherValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private TeacherValidator() { /* utility class */ }

    public static void validateCreate(String email, I_UserRepository userRepo, I_TeacherRepository teacherRepo) {
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
        if (user.getType() != User.Type.TEACHER) {
            throw new IllegalStateException("User must be of type TEACHER to create a Teacher record");
        }
        if (teacherRepo.existsByUser_Email(email)) {
            throw new IllegalStateException("Teacher profile already exists for email: " + email);
        }
    }
}