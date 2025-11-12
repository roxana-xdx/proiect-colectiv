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
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private I_TeacherService teacherService;

    @PostMapping
    public ResponseEntity<?> createTeacher(@RequestBody @Valid CreateTeacherRequest req) {
        try {
            Teacher created = teacherService.createTeacherByEmail(req.getEmail());
            TeacherDTO dto = TeacherMapper.toDTO(created);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        List<TeacherDTO> dtos = TeacherMapper.toDTOList(teacherService.getAllTeachers());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable Long id) {
        Optional<Teacher> opt = teacherService.getTeacherById(id);
        if (opt.isPresent()) {
            TeacherDTO dto = TeacherMapper.toDTO(opt.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found");
        }
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<?> getTeacherByEmail(@PathVariable String email) {
        Optional<Teacher> opt = teacherService.getTeacherByEmail(email);
        if (opt.isPresent()) {
            TeacherDTO dto = TeacherMapper.toDTO(opt.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found for email: " + email);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
        try {
            teacherService.deleteTeacher(id);
            return ResponseEntity.ok("Teacher deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}