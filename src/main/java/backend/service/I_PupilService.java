package backend.service;

import backend.entities.Pupil;

import java.util.List;
import java.util.Optional;

public interface I_PupilService {
    public List<Pupil> getAllPupils();

    public Pupil getPupilById(Long id);

    public Pupil createPupile(Pupil pupil);

    public Pupil updatePupil(Long id, Pupil updated);

    public void deletePupil(Long id);

    public Optional<Pupil> findPupilByEmail(String email);

    public List<Pupil> findPupilByClass_id(Long id);

    ///TODO methods for class announcements and class schedule
}
