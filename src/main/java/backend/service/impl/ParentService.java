package backend.service.impl;

import backend.entity.Parent;
import backend.repository.I_ParentRepository;
import backend.service.I_ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
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
    public Optional<Parent> getParentByEmail(String email) {
        return parentRepository.findByEmail(email);
    }

    @Override
    public Parent createParent(Parent parent) {
        if (parentRepository.findByEmail(parent.getEmail()).isPresent()) {
            throw new RuntimeException("Parent already exists" + parent.getEmail());
        }
        Parent savedParent = parentRepository.save(parent);
        validateParent(savedParent);
        return savedParent;
    }

    @Override
    public Parent updateParent(String email, Parent parent) {
        Parent existing = parentRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Parent not found"));
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
