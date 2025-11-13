package backend.service;

import backend.entity.Pupil;

import java.util.List;
import java.util.Optional;

public interface I_PupilService {
    public List<Pupil> getAllPupils();

    public Optional<Pupil> getPupilById(Long id);

    public Pupil createPupil(String email, Long class_id, Long parent_id);

    public Pupil updatePupil(Long id, Long class_id, Long parent_id);

    public void deletePupil(Long id);

    public Optional<Pupil> findPupilByEmail(String email);

    Pupil createPupilByEmail(String email);

//    public List<Pupil> findPupilByClass_id(Long id);

    ///TODO methods for class announcements and class schedule
}
