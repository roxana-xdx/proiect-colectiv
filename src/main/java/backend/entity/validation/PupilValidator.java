package backend.entity.validation;

import backend.entity.Pupil;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PupilValidator implements Validator<Pupil> {

    @Override
    public void validate(Pupil pupil) throws ValidationException {
        List<String> errors = new ArrayList<>();
        if(pupil.getEmail() == null || pupil.getEmail().isEmpty()){
            errors.add("Email cannot be empty");
        }

        if(pupil.getClass_id() == null){
            errors.add("Class ID cannot be null");
        }

        if(pupil.getParent_id() == null){
            errors.add("Parent ID cannot be null");
        }
    }
}
