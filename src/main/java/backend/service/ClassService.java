package backend.service;
import backend.entities.Clasa;
import backend.repository.ClassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ClassService {
    private final ClassRepo classRepository;
    @Autowired
    public ClassService(ClassRepo classRepository) {
        this.classRepository = classRepository;
    }

    public Clasa createClass(Clasa clazz) {
        return classRepository.save(clazz);
    }
    public Clasa getClassById(Long id) {
        return classRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("No class found with id: " + id));
    }
    public Clasa updateClass(Long id, Clasa newClass) {
        return classRepository.findById(id)
                .map(current -> {
                    current.set_homeroom_teacher_id(newClass.get_homeroom_teacher_id()); current.set_class_name(newClass.get_class_name());
                    return classRepository.save(current);})
                .orElseThrow(() -> new RuntimeException("No class found with id: " + id));
    }

    public void deleteClassByID(Long id) {
        if (!classRepository.existsById(id)) {
            throw new RuntimeException("No class found with id: " + id);
        }
        classRepository.deleteById(id);
    }

    public List<Clasa> getAllClasses() {
        return classRepository.findAll();
    }

    public Optional<List<Clasa>> findClassByTeacherID (Long homeroom_teacher_id) {
        if(homeroom_teacher_id <= 0) {
            throw new RuntimeException("Invalid homeroom teacher id");
        }
        return classRepository.findClassByTeacherID(homeroom_teacher_id);
    }
    public Optional<List<Clasa>> findClassByName (String name) {
        if(name==null || name.isEmpty()) {
            throw new RuntimeException("Invalid class name");
        }
        return classRepository.findClassByName(name);
    }
}

