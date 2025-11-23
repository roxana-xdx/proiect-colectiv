package backend.service.impl;

import backend.dto.schoolclass.CreateSchoolClassRequest;
import backend.entity.SchoolClass;
import backend.entity.Teacher;
import backend.repository.I_SchoolClassRepository;
import backend.repository.I_TeacherRepository;
import backend.service.I_SchoolClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@Transactional(readOnly = true)
public class SchoolClassService implements I_SchoolClassService {

    private final I_SchoolClassRepository classRepository;
    private final I_TeacherRepository teacherRepository;

    @Autowired
    public SchoolClassService(I_SchoolClassRepository classRepository, I_TeacherRepository teacherRepository) {
        this.classRepository = classRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    @Transactional
    public SchoolClass createClass(String name, Long homeroomTeacherId) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Class name is required");
        }
        if (homeroomTeacherId == null) {
            throw new IllegalArgumentException("homeroomTeacherId is required");
        }
        // getReferenceById returns a reference (no immediate select) but will validate FK on flush if invalid
        Teacher teacherRef = teacherRepository.getReferenceById(homeroomTeacherId);
        SchoolClass clasa = new SchoolClass(name, teacherRef);
        return classRepository.save(clasa);
    }

    @Override
    public Optional<SchoolClass> getClassById(Long id) {
        return classRepository.findById(id);
    }

    @Override
    @Transactional
    public SchoolClass updateClass(Long id, CreateSchoolClassRequest request) {
        return classRepository.findById(id)
                .map(current -> {
                    if (request.getClassName() != null && !request.getClassName().isBlank()) {
                        current.setClassName(request.getClassName());
                    }
                    if (request.getHomeroomTeacherId() != null) {
                        Teacher teacherRef = teacherRepository.getReferenceById(request.getHomeroomTeacherId());
                        current.setHomeroomTeacher(teacherRef);
                    }
                    return classRepository.save(current);
                })
                .orElseThrow(() -> new RuntimeException("No class found with id: " + id));
    }

    @Override
    @Transactional
    public void deleteClassById(Long id) {
        if (!classRepository.existsById(id)) {
            throw new RuntimeException("No class found with id: " + id);
        }
        classRepository.deleteById(id);
    }

    @Override
    public List<SchoolClass> getAllClasses() {
        return classRepository.findAll();
    }

    @Override
    public Optional<SchoolClass> findClassByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid class name");
        }
        return classRepository.findByClassName(name);
    }

    @Override
    public List<SchoolClass> findClassesByTeacherId(Long teacherId) {
        if (teacherId == null || teacherId <= 0) {
            throw new IllegalArgumentException("Invalid homeroom teacher id");
        }
        return classRepository.findByHomeroomTeacherId(teacherId);
    }
}