package backend.service;

import backend.entity.Teacher;

import java.util.List;
import java.util.Optional;

public interface I_TeacherService {
    Teacher createTeacherByEmail(String email);
    List<Teacher> getAllTeachers();
    Optional<Teacher> getTeacherById(Long id);
    Optional<Teacher> getTeacherByEmail(String email);
    Teacher updateTeacher(Long id, Teacher newDetails);
    void deleteTeacher(Long id);
}