package backend.controller;

import backend.dto.TeacherDTO;
import backend.dto.teacher.CreateTeacherRequest;
import backend.entity.Teacher;
import backend.mapper.TeacherMapper;
import backend.service.I_TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final I_TeacherService teacherService;

    @Autowired
    public TeacherController(I_TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /**
     * Create teacher from existing user
     */
    @PostMapping
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody @Valid CreateTeacherRequest req) {
        try {
            Teacher created = teacherService.createTeacherByEmail(req.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(TeacherMapper.toDTO(created));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all teachers
     */
    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        List<TeacherDTO> dtos = TeacherMapper.toDTOList(teacherService.getAllTeachers());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get teacher by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id)
                .map(TeacherMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Get teacher by email
     */
    @GetMapping("/by-email/{email}")
    public ResponseEntity<TeacherDTO> getTeacherByEmail(@PathVariable String email) {
        return teacherService.getTeacherByEmail(email)
                .map(TeacherMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Delete teacher
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        try {
            teacherService.deleteTeacher(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            if (e.getMessage().contains("still assigned")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // HTTP 409 Conflict
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // HTTP 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}