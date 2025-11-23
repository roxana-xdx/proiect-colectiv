package backend.controller;

import backend.dto.SchoolClassDTO;
import backend.dto.schoolclass.CreateSchoolClassRequest;
import backend.entity.SchoolClass;
import backend.mapper.SchoolClassMapper;
import backend.service.I_SchoolClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classes")
public class SchoolClassController {

    @Autowired
    private I_SchoolClassService classService;

    @GetMapping
    public ResponseEntity<List<SchoolClassDTO>> getAllClasses() {
        List<SchoolClassDTO> dtos = SchoolClassMapper.toDTOList(classService.getAllClasses());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolClassDTO> getClassById(@PathVariable Long id) {
        Optional<SchoolClass> opt = classService.getClassById(id);
        return opt.map(c -> ResponseEntity.ok(SchoolClassMapper.toDTO(c)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<SchoolClassDTO> getClassByName(@PathVariable String name) {
        Optional<SchoolClass> opt = classService.findClassByName(name);
        return opt.map(c -> ResponseEntity.ok(SchoolClassMapper.toDTO(c)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/by-teacher/{teacherId}")
    public ResponseEntity<List<SchoolClassDTO>> getClassesByTeacher(@PathVariable Long teacherId) {
        List<SchoolClass> classes = classService.findClassesByTeacherId(teacherId);
        return ResponseEntity.ok(SchoolClassMapper.toDTOList(classes));
    }

    @PostMapping
    public ResponseEntity<?> createClass(@RequestBody @Valid CreateSchoolClassRequest request) {
        try {
            SchoolClass created = classService.createClass(request.getClassName(), request.getHomeroomTeacherId());
            return ResponseEntity.status(HttpStatus.CREATED).body(SchoolClassMapper.toDTO(created));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating class: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClass (@PathVariable Long id, @RequestBody @Valid CreateSchoolClassRequest request) {
        try {
            SchoolClass updated = classService.updateClass(id, request);
            return ResponseEntity.ok(SchoolClassMapper.toDTO(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClass(@PathVariable Long id) {
        try {
            classService.deleteClassById(id);
            return ResponseEntity.ok("Class deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}