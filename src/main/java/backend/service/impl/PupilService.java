package backend.service.impl;

import backend.entity.SchoolClass;
import backend.entity.Parent;
import backend.entity.Pupil;
import backend.entity.User;
import backend.entity.validation.PupilValidator;
import backend.repository.I_SchoolClassRepository;
import backend.repository.I_ParentRepository;
import backend.repository.I_PupilRepository;
import backend.repository.I_UserRepository;
import backend.service.I_PupilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@Transactional(readOnly = true)
public class PupilService implements I_PupilService {

    private final I_PupilRepository pupilRepository;
    private final I_UserRepository userRepository;
    private final I_ParentRepository parentRepository;
    private final I_SchoolClassRepository classRepository;

    @Autowired
    public PupilService(I_PupilRepository pupilRepository,
                        I_UserRepository userRepository,
                        I_ParentRepository parentRepository,
                        I_SchoolClassRepository classRepository) {
        this.pupilRepository = pupilRepository;
        this.userRepository = userRepository;
        this.parentRepository = parentRepository;
        this.classRepository = classRepository;
    }

    @Override
    public List<Pupil> getAllPupils() {
        return pupilRepository.findAll();
    }

    @Override
    public Optional<Pupil> getPupilById(Long id) {
        return pupilRepository.findById(id);
    }

    @Override
    @Transactional
    public Pupil createPupil(String email, Long class_id, Long parent_id) {
        PupilValidator.validate(email, userRepository, pupilRepository);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
        Parent parent = parentRepository.findById(parent_id)
                .orElseThrow(() -> new IllegalArgumentException("Parent not found with id: " + parent_id));
        SchoolClass clasa = classRepository.findById(class_id)
                .orElseThrow(() -> new IllegalArgumentException("Class not found with id: " + class_id));
        Pupil pupil = new Pupil();
        pupil.setUser(user);
        pupil.setClasa(clasa);
        pupil.setParent(parent);
        return pupilRepository.save(pupil);
    }

    @Override
    @Transactional
    public void deletePupil(Long id) {
        if (!pupilRepository.existsById(id)) {
            throw new RuntimeException("Pupil not found with ID: " + id);
        }
        pupilRepository.deleteById(id);
    }

    @Override
    public Optional<Pupil> findPupilByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        // preferăm găsirea pe user.email (mai robust) dar păstrăm fallback
        return pupilRepository.findByUser_Email(email).or(() -> pupilRepository.findByEmail(email));
    }

    @Override
    @Transactional
    public Pupil createPupilByEmail(String email) {
        PupilValidator.validate(email, userRepository, pupilRepository);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
        Pupil pupil = new Pupil();
        pupil.setUser(user);
        return pupilRepository.save(pupil);
    }

    @Override
    @Transactional
    public Pupil updatePupil(Long id, Long class_id, Long parent_id) {
        Pupil existing = pupilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pupil not found with id: " + id));
        if (parent_id != null) {
            Parent parent = parentRepository.findById(parent_id)
                    .orElseThrow(() -> new IllegalArgumentException("Parent not found with id: " + parent_id));
            existing.setParent(parent);
        } else {
            existing.setParent(null);
        }
        if (class_id != null) {
            SchoolClass clasa = classRepository.findById(class_id)
                    .orElseThrow(() -> new IllegalArgumentException("Class not found with id: " + class_id));
            existing.setClasa(clasa);
        } else {
            existing.setClasa(null);
        }
        return pupilRepository.save(existing);
    }
}