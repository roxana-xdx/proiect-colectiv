package backend.service;

import backend.repository.ClassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ClassService {
    private final ClassRepo classRepository;
    @Autowired
    public ClassService(ClassRepo classRepository) {
        this.classRepository = classRepository;
    }

    public Class createClass(Class clazz) {
        return classRepository.save(clazz);
    }
    public Class getClassById(Long id) {
        return classRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("No class found with id: " + id));
    }
//    public Class updateClass(Long id, Class newClass) {
//        return classRepository.findById(id);
//
//    }


}
