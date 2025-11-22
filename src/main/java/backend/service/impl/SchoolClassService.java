package backend.service.impl;

import backend.entity.SchoolClass;
import backend.repository.I_SchoolClassRepository;
import backend.service.I_SchoolClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class SchoolClassService implements I_SchoolClassService {
    private final I_SchoolClassRepository classRepository;

    @Autowired
    public SchoolClassService(I_SchoolClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public SchoolClass createClass(String name, Long homeroomTeacherId) {
        SchoolClass clasa = new SchoolClass(name, homeroomTeacherId);
        return classRepository.save(clasa);
    }

    public Optional<SchoolClass> getClassById(Long id) {
        return classRepository.findById(id);
    }

    public SchoolClass updateClass(Long id, SchoolClass newClass) {
        return classRepository.findById(id)
                .map(current -> {
                    current.setHomeroomTeacherId(newClass.getHomeroomTeacherId());
                    current.setClassName(newClass.getClassName());
                    return classRepository.save(current);
                })
                .orElseThrow(() -> new RuntimeException("No class found with id: " + id));
    }

    public void deleteClassByID(Long id) {
        if (!classRepository.existsById(id)) {
            throw new RuntimeException("No class found with id: " + id);
        }
        classRepository.deleteById(id);
    }

    public List<SchoolClass> getAllClasses() {
        return classRepository.findAll();
    }

    public Optional<List<SchoolClass>> findClassByTeacherID(Long homeroomTeacherId) {
        if (homeroomTeacherId <= 0) {
            throw new RuntimeException("Invalid homeroom teacher id");
        }
        return classRepository.findByHomeroomTeacherId(homeroomTeacherId);
    }

    public Optional<SchoolClass> findClassByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Invalid class name");
        }
        return classRepository.findByClassName(name);
    }
}
