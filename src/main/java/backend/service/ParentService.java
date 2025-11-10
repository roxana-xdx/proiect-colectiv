package backend.service;

import backend.dto.ParentDTO;
import backend.entity.Parent;
import backend.repository.I_ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Validated
public class ParentService implements I_ParentService {
    @Autowired
    private I_ParentRepository parentRepository;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public ParentService(I_ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    @Override
    public List<Parent> getAllPupils() {
        return parentRepository.findAll();
    }

    @Override
    public ParentDTO getParentById(Long id) {
        return parentRepository.findById(id);
    }

    @Override
    public ParentDTO createParent(ParentDTO parentDTO) {
        return null;
    }

    @Override
    public void updateParent(ParentDTO parentDTO) {

    }

    @Override
    public void deleteParent(Long id) {

    }

    @Override
    public void deleteParent(String email) {
        if(!parentRepository.existsByEmail(email)){
            throw new IllegalStateException("Parent with email " + email + " not found");
        }
        parentRepository.deleteById(email);
    }

    @Override
    public ParentDTO findParentByEmail(String email) {
        return parentRepository.findByEmail(email);
    }
}
