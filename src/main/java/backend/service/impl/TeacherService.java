package backend.service.impl;

import backend.entity.Teacher;
import backend.entity.User;
import backend.entity.validation.TeacherValidator;
import backend.repository.I_TeacherRepository;
import backend.repository.I_UserRepository;
import backend.service.I_TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class TeacherService implements I_TeacherService {

    @Autowired
    private I_TeacherRepository teacherRepository;

    @Autowired
    private I_UserRepository userRepository;


    @Override
    @Transactional
    public Teacher createTeacherByEmail(String email) {
        TeacherValidator.validateCreate(email, userRepository, teacherRepository);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found after validation"));
        Teacher teacher = new Teacher();
        teacher.setUser(user);
        return teacherRepository.save(teacher);
    }

    @Override
    public Optional<Teacher> getTeacherById(Long id) {
        return Optional.ofNullable(teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id)));
    }

    @Override
    public Optional<Teacher> getTeacherByEmail(String email) {
        return Optional.ofNullable(teacherRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher not found with email: " + email)));
    }

    @Override
    public Teacher updateTeacher(Long id, Teacher newDetails) {
        return teacherRepository.findById(id)
                .map(current -> {
                    current.setEmail(newDetails.getEmail());
                    return teacherRepository.save(current);
                })
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
    }

    @Override
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
        teacherRepository.deleteById(id);
    }

    @Override
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