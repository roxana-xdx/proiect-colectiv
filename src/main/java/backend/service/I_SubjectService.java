package backend.service;

import backend.entity.Subject;

import java.util.List;
import java.util.Optional;

public interface I_SubjectService {
    Subject createSubject(String name);
    List<Subject> getAllSubjects();
    Optional<Subject> getSubjectById(Long id);
    Subject updateSubject(Long id, String name);
    void deleteSubject(Long id);
}