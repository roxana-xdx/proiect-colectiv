package backend.entity.validation;

import backend.entity.Pupil;
import backend.entity.User;
import backend.repository.I_PupilRepository;
import backend.repository.I_UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class PupilValidator {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public List<String> errors = new ArrayList<>();

    public static void validate(String email, I_UserRepository userRepository, I_PupilRepository pupilRepository) throws ValidationException {
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if(!EMAIL_PATTERN.matcher(email).matches()){
            throw new IllegalArgumentException("Email is not valid");
        }
        var user = userRepository.findByEmail(email);
        if(user.isEmpty())
            throw new IllegalArgumentException("No user found with email: " + email);
        if(user.get().getType() != User.Type.PUPIL){
            throw new IllegalArgumentException("User must be of type PUPIL");
        }
    }

//
//    @Override
//    public void validate(Pupil pupil) throws ValidationException {
//        if(pupil.getEmail() == null || pupil.getEmail().isEmpty()){
//            errors.add("Email cannot be empty");
//        }
//
//        if(pupil.getClass_id() == null){
//            errors.add("Class ID cannot be null");
//        }
//
//        if(pupil.getParent_id() == null){
//            errors.add("Parent ID cannot be null");
//        }
//    }
}