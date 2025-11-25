package backend.service.impl;

import backend.entity.Teacher;
import backend.entity.User;
import backend.entity.validation.TeacherValidator;
import backend.repository.I_TeacherRepository;
import backend.repository.I_UserRepository;
import backend.service.I_TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService implements I_TeacherService {

    private final I_TeacherRepository teacherRepository;
    private final I_UserRepository userRepository;

    @Autowired
    public TeacherService(I_TeacherRepository teacherRepository,
                          I_UserRepository userRepository) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Teacher createTeacherByEmail(String email) {
        TeacherValidator.validateCreate(email, userRepository, teacherRepository);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found after validation"));
        Teacher teacher = new Teacher(user);
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public Optional<Teacher> getTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new IllegalStateException("Teacher not found with id: " + id);
        }
        teacherRepository.deleteById(id);
    }
}