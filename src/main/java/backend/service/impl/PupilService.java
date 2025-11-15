package backend.service.impl;

import backend.entity.Parent;
import backend.entity.Pupil;
import backend.entity.User;
import backend.entity.validation.PupilValidator;
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
public class PupilService implements I_PupilService {
    @Autowired
    private I_PupilRepository pupilRepository;
    @Autowired
    private I_UserRepository userRepository;
    @Autowired
    private I_ParentRepository parentRepository;

    //    public final Class_AnnoucementRepository classAnnoucementRepository;

    public PupilService(I_PupilRepository pupilRepository, I_UserRepository userRepository) {
        this.pupilRepository = pupilRepository;
        this.userRepository = userRepository;
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
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        Parent parent = parentRepository.findById(parent_id)
                .orElseThrow(() -> new RuntimeException("Parent not found with id: " + parent_id));
        Pupil pupil = new Pupil();
        pupil.setUser(user);
        pupil.setClass_id(class_id);
        pupil.setParent(parent);
        return pupilRepository.save(pupil);
    }

//    @Override
//    @Transactional
//    public Pupil updatePupil(Long id, Long class_id, Long parent_id) {
//        Pupil existingPupil = pupilRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Pupil not found with id: " + id));
//        PupilValidator.validate(existingPupil.getUser().getEmail(), userRepository, pupilRepository);
//        existingPupil.setClass_id(class_id);
//        existingPupil.getParent().setId(parent_id);
//        return pupilRepository.save(existingPupil);
//    }

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
            throw new RuntimeException("Email cannot be null or empty");
        }
        return pupilRepository.findByEmail(email);
    }

//    @Override
//    public List<Pupil> findPupilByClass_id(Long id) {
//        return pupilRepository.findByClass_id(id);
//    }
}
