package backend.service;

import backend.dto.ParentDTO;
import backend.entity.Parent;
import backend.repository.I_ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Validated
public class ParentService implements I_ParentService {

    @Autowired
    private I_ParentRepository parentRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private void validateParent(Parent parent) {
        if (parent == null) throw new IllegalArgumentException("Parent cannot be null");
        if (parent.getEmail() == null || parent.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!EMAIL_PATTERN.matcher(parent.getEmail()).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (parent.getId() == null) {
            throw new IllegalArgumentException("Parent id cannot be null");
        }
    }

    @Override
    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    @Override
    public Parent getParentByEmail(String email) {
        return parentRepository.findByEmail(email);
    }

    @Override
    public Parent createParent(Parent parent) {
        Parent existing =  parentRepository.findByEmail(parent.getEmail());
        if (existing!=null) {
            throw new RuntimeException("Pupil already exists" + parent.getEmail());
        }
        Parent savedParent = parentRepository.save(parent);
        validateParent(savedParent);
        return parentRepository.save(savedParent);
    }

    @Override
    public Parent updateParent(String email, Parent parent) {
        Parent existing = parentRepository.findByEmail(email);
        if (existing == null) {
            throw new IllegalStateException("Parent with email " + email + "not found.");
        }
        if (parent.getId() != null) {
            existing.setId(parent.getId());
        }
        validateParent(existing);
        return parentRepository.save(existing);
    }

    @Override
    public void deleteParent(String email) {
        if(!parentRepository.existsByEmail(email)){
            throw new IllegalStateException("Parent with email" + email + " not found.");
        }
        parentRepository.deleteById(email);
    }
}
