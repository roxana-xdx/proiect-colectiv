package backend.service.impl;

import backend.entity.Subject;
import backend.entity.validation.SubjectValidator;
import backend.repository.I_SubjectRepository;
import backend.service.I_SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SubjectService implements I_SubjectService {

    private final I_SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(I_SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    @Transactional
    public Subject createSubject(String name) {
        // DTO-ul a validat ca name să nu fie null/gol și să aibă lungimea corectă
        SubjectValidator.validateCreate(name);

        if (subjectRepository.findByName(name).isPresent()) {
            throw new IllegalStateException("Subject with name '" + name + "' already exists");
        }

        Subject subject = new Subject(name);
        return subjectRepository.save(subject);
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public Optional<Subject> getSubjectById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return subjectRepository.findById(id);
    }

    @Override
    @Transactional
    public Subject updateSubject(Long id, String name) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Subject not found with ID: " + id));

        if (name != null && !name.trim().isEmpty() && !name.equals(subject.getName())) {
            // Verifică unicitatea înainte de a salva, dacă numele se schimbă
            if (subjectRepository.findByName(name).isPresent()) {
                throw new IllegalStateException("Subject with name '" + name + "' already exists");
            }
            subject.setName(name);
        }

        return subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public void deleteSubject(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!subjectRepository.existsById(id)) {
            throw new IllegalStateException("Subject with ID " + id + " not found"); // 404
        }

        try {
            subjectRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // Dacă materia e folosită în Class_Schedule sau Pupil_Teacher_Feedback
            throw new IllegalStateException("Cannot delete subject with ID " + id
                    + " because it is currently assigned to a schedule or feedback entry."); // 409 Conflict
        }
    }
}