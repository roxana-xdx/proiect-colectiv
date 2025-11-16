package backend.service.impl;

import backend.entity.Clasa;
import backend.repository.I_ClassRepository;
import backend.service.I_ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ClassService implements I_ClassService {
    private final I_ClassRepository classRepository;

    @Autowired
    public ClassService(I_ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public Clasa createClass(String name, Long homeroomTeacherId) {
        Clasa clasa = new Clasa(name, homeroomTeacherId);
        return classRepository.save(clasa);
    }

    public Optional<Clasa> getClassById(Long id) {
        return classRepository.findById(id);
    }

    public Clasa updateClass(Long id, Clasa newClass) {
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

    public List<Clasa> getAllClasses() {
        return classRepository.findAll();
    }

    public Optional<List<Clasa>> findClassByTeacherID(Long homeroomTeacherId) {
        if (homeroomTeacherId <= 0) {
            throw new RuntimeException("Invalid homeroom teacher id");
        }
        return classRepository.findByHomeroomTeacherId(homeroomTeacherId);
    }

    public Optional<Clasa> findClassByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Invalid class name");
        }
        return classRepository.findByClassName(name);
    }
}

