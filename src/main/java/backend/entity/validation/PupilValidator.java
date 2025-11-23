package backend.entity.validation;

import backend.entity.User;
import backend.repository.I_PupilRepository;
import backend.repository.I_UserRepository;

import java.util.regex.Pattern;

public final class PupilValidator {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private PupilValidator() {}

    public static void validate(String email, I_UserRepository userRepo, I_PupilRepository pupilRepo) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email is not valid");
        }

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No user found with email: " + email));

        if (user.getType() != User.Type.PUPIL) {
            throw new IllegalArgumentException("User must be of type PUPIL");
        }

        if (pupilRepo.findByUser_Email(email).isPresent()) {
            throw new IllegalStateException("Pupil already exists for email: " + email);
        }
    }
}