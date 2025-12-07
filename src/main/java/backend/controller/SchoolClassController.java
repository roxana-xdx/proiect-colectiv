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

@RestController
@RequestMapping("/api/v1/classes")
public class SchoolClassController {

    private final I_SchoolClassService classService;

    @Autowired
    public SchoolClassController(I_SchoolClassService classService) {
        this.classService = classService;
    }

    /**
     * Get all classes
     */
    @GetMapping
    public ResponseEntity<List<SchoolClassDTO>> getAllClasses() {
        List<SchoolClassDTO> dtos = SchoolClassMapper.toDTOList(classService.getAllClasses());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get class by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<SchoolClassDTO> getClassById(@PathVariable Long id) {
        return classService.getClassById(id)
                .map(SchoolClassMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Get class by name
     */
    @GetMapping("/by-name/{name}")
    public ResponseEntity<SchoolClassDTO> getClassByName(@PathVariable String name) {
        return classService.findClassByName(name)
                .map(SchoolClassMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Get all classes for a specific teacher (homeroom teacher)
     */
    @GetMapping("/by-teacher/{teacherId}")
    public ResponseEntity<List<SchoolClassDTO>> getClassesByTeacher(@PathVariable Long teacherId) {
        List<SchoolClass> classes = classService.findClassesByTeacherId(teacherId);
        return ResponseEntity.ok(SchoolClassMapper.toDTOList(classes));
    }

    /**
     * Create new class
     */
    @PostMapping
    public ResponseEntity<SchoolClassDTO> createClass(@RequestBody @Valid CreateSchoolClassRequest request) {
        try {
            SchoolClass created = classService.createClass(
                    request.getClassName(),
                    request.getHomeroomTeacherId()
            );
            SchoolClassDTO dto = SchoolClassMapper.toDTO(created);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update class
     */
    @PutMapping("/{id}")
    public ResponseEntity<SchoolClassDTO> updateClass(
            @PathVariable Long id,
            @RequestBody @Valid CreateSchoolClassRequest request) {
        try {
            SchoolClass updated = classService.updateClass(id, request);
            return ResponseEntity.ok(SchoolClassMapper.toDTO(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete class
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        try {
            classService.deleteClassById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}