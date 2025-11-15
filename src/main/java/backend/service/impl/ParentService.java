package backend.service.impl;

import backend.entity.Parent;
import backend.entity.User;
import backend.entity.validation.ParentValidator;
import backend.repository.I_ParentRepository;
import backend.repository.I_UserRepository;
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
    @Autowired
    private I_UserRepository userRepository;

//    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
//    private void validateParent(Parent parent) {
//        if (parent == null) throw new IllegalArgumentException("Parent cannot be null");
//        if (parent.getEmail() == null || parent.getEmail().isBlank()) {
//            throw new IllegalArgumentException("Email cannot be null or empty");
//        }
//        if (!EMAIL_PATTERN.matcher(parent.getEmail()).matches()) {
//            throw new IllegalArgumentException("Invalid email format");
//        }
//        if (parent.getId() == null) {
//            throw new IllegalArgumentException("Parent id cannot be null");
//        }
//    }

    @Override
    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    @Override
    public Optional<Parent> getParentById(Long id) {
        return parentRepository.findById(id);
    }

    @Override
    public Optional<Parent> getParentByEmail(String email) {
        return parentRepository.findByEmail(email);
    }

    @Override
    public Parent createParent(String email) {
        ParentValidator.validateCreate(email, userRepository, parentRepository);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        Parent parent = new Parent();
        parent.setUser(user);
        return parentRepository.save(parent);
    }

//    @Override
//    public Parent updateParent(String email, Parent parent) {
//        Parent existing = parentRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalStateException("Parent not found"));
//        if (parent.getId() != null) {
//            existing.setId(parent.getId());
//        }
//        validateParent(existing);
//        return parentRepository.save(existing);
//    }

    @Override
    public void deleteParent(Long id) {
        if(!parentRepository.existsById(id)){
            throw new IllegalStateException("Parent with ID " + id + " not found.");
        }
        parentRepository.deleteById(id);
    }
}
