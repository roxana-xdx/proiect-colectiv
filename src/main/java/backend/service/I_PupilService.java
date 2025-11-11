package backend.service;

import backend.entity.Pupil;

import java.util.List;
import java.util.Optional;

public interface I_PupilService {
    public List<Pupil> getAllPupils();

    public Pupil getPupilById(Long id);

    public Pupil createPupil(Pupil pupil);

    public void updatePupil(Pupil pupil);

    public void deletePupil(Long id);

    public Optional<Pupil> findPupilByEmail(String email);

//    public List<Pupil> findPupilByClass_id(Long id);

    ///TODO methods for class announcements and class schedule
}
