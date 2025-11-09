package backend.service;

import backend.entity.Pupil;
import backend.repository.I_PupilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class PupilService implements I_PupilService {
    @Autowired
    private final I_PupilRepository pupilRepository;
//    public final Class_ScheduleRepository classScheduleRepository;
//    public final Class_AnnoucementRepository classAnnoucementRepository;

    public PupilService(I_PupilRepository pupilRepository) {
        this.pupilRepository = pupilRepository;
    }

    @Override
    public List<Pupil> getAllPupils() {
        return pupilRepository.findAll();
    }

    @Override
    public Pupil getPupilById(Long id) {
        return pupilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pupil not found with ID: " + id));
    }

    @Override
    public Pupil createPupile(Pupil pupil) {
        if (pupilRepository.findById(pupil.getId()).isPresent()) {
            throw new RuntimeException("Pupil already exists");
        }
        return pupilRepository.save(pupil);
    }

    @Override
    public Pupil updatePupil(Long id, Pupil updated) {
        return pupilRepository.findById(id)
                .map(existing -> {
                    existing.setEmail(updated.getEmail());
                    existing.setClass_id(updated.getClass_id());
                    existing.setParent_id(updated.getParent_id());
                    return pupilRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Pupil not found with ID: " + id));
    }

    @Override
    public void deletePupil(Long id) {
        if(!pupilRepository.existsById(id)){
            throw new RuntimeException("Pupil not found with ID: " + id);
        }
        pupilRepository.deleteById(id);
    }

    @Override
    public Optional<Pupil> findPupilByEmail(String email) {
        if(email == null || email.trim().isEmpty()){
            throw new RuntimeException("Email cannot be null or empty");
        }
        return pupilRepository.findByEmail(email);
    }

//    @Override
//    public List<Pupil> findPupilByClass_id(Long id) {
//        return pupilRepository.findByClass_id(id);
//    }
}
