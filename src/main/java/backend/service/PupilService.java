package backend.service;

import backend.dto.PupilDTO;
import backend.entity.Pupil;
import backend.entity.validation.PupilValidator;
import backend.mapper.PupilMapper;
import backend.repository.I_PupilRepository;
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
    private final I_PupilRepository pupilRepository;
    public final PupilValidator pupilValidator = new PupilValidator();

//    public final Class_ScheduleRepository classScheduleRepository;
//    public final Class_AnnoucementRepository classAnnoucementRepository;

    public PupilService(I_PupilRepository pupilRepository) {
        this.pupilRepository = pupilRepository;
    }

    @Override
    public List<PupilDTO> getAllPupils() {
        return PupilMapper.toDTOList(pupilRepository.findAll());
    }

    @Override
    public PupilDTO getPupilById(Long id) {
        Pupil pupil =  pupilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pupil not found with ID: " + id));
        return PupilMapper.toDTO(pupil);
    }

    @Override
    @Transactional
    public PupilDTO createPupil(PupilDTO pupildto) {
        if (pupilRepository.findByEmail(pupildto.getEmail()).isPresent()) {
            throw new RuntimeException("Pupil already exists" + pupildto.getEmail());
        }
        Pupil pupil = PupilMapper.toEntity(pupildto);
        pupilValidator.validate(pupil);
        Pupil savedPupil = pupilRepository.save(pupil);
        return PupilMapper.toDTO(savedPupil);
    }

    @Override
    @Transactional
    public void updatePupil(PupilDTO pupilDTO) {
        Pupil existingPupil =  pupilRepository.findById(pupilDTO.getId())
                .orElseThrow(() -> new RuntimeException("Pupil not found with ID: " + pupilDTO.getEmail()));

        Pupil pupil = PupilMapper.toEntity(pupilDTO);
        pupilValidator.validate(pupil);
        pupil.setId(existingPupil.getId());
        pupilRepository.save(pupil);
    }

    @Override
    @Transactional
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
