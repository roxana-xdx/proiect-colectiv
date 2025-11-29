package backend.service.impl;

import backend.entity.Subject;
import backend.repository.I_SubjectRepository;
import backend.service.I_SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService implements I_SubjectService {

    @Autowired
    private I_SubjectRepository subjectRepository;

    @Override
    @Transactional
    public Subject createSubject(String name) {
        // Validare date
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        // Cream subject-ul
        Subject subject = new Subject();
        subject.setName(name);

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

        if (name != null && !name.trim().isEmpty()) {
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
            throw new IllegalStateException("Subject with ID " + id + " not found");
        }

        subjectRepository.deleteById(id);
    }
}