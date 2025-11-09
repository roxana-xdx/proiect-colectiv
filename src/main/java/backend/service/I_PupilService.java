package backend.service;

import backend.dto.PupilDTO;
import backend.entity.Pupil;

import java.util.List;
import java.util.Optional;

public interface I_PupilService {
    public List<PupilDTO> getAllPupils();

    public PupilDTO getPupilById(Long id);

    public PupilDTO createPupil(PupilDTO pupildto);

    public void updatePupil(PupilDTO pupildto);

    public void deletePupil(Long id);

    public Optional<Pupil> findPupilByEmail(String email);

//    public List<Pupil> findPupilByClass_id(Long id);

    ///TODO methods for class announcements and class schedule
}
