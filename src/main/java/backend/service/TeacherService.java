package backend.service;

import backend.entities.Teacher;
import backend.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;


@Service
@Validated
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public Teacher createTeacher(Teacher teacher) {

        if (teacherRepository.findByEmail(teacher.getEmail()).isPresent() ) {
            throw new IllegalArgumentException("This email is already used.");
        }
        return teacherRepository.save(teacher);
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
    }

    public Teacher updateTeacher(Long id, Teacher newDetails) {
        return teacherRepository.findById(id)
                .map(current -> {
                    current.setEmail(newDetails.getEmail());
                    return teacherRepository.save(current);
                })
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
    }

    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
        teacherRepository.deleteById(id);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> findTeacherByEmail(String email) {
        if(email == null || email.trim().isEmpty()){
            throw new RuntimeException("Email cannot be empty.");
        }
        return teacherRepository.findByEmail(email);
    }
}