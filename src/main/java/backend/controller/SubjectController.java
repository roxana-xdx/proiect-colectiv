package backend.controller;

import backend.dto.SubjectDTO;
import backend.entity.Subject;
import backend.mapper.SubjectMapper;
import backend.service.I_SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    private I_SubjectService subjectService;

    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody @Valid SubjectDTO subjectDTO) {
        try {
            Subject created = subjectService.createSubject(subjectDTO.getName());
            SubjectDTO dto = SubjectMapper.toDTO(created);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        List<SubjectDTO> dtos = SubjectMapper.toDTOList(subjectService.getAllSubjects());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable Long id) {
        Optional<Subject> opt = subjectService.getSubjectById(id);
        if (opt.isPresent()) {
            SubjectDTO dto = SubjectMapper.toDTO(opt.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subject not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubject(
            @PathVariable Long id,
            @RequestBody @Valid SubjectDTO subjectDTO) {
        try {
            Subject updated = subjectService.updateSubject(id, subjectDTO.getName());
            SubjectDTO dto = SubjectMapper.toDTO(updated);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            if (e instanceof IllegalStateException) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } else {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
        try {
            subjectService.deleteSubject(id);
            return ResponseEntity.ok("Subject deleted");
        } catch (RuntimeException e) {
            if (e instanceof IllegalStateException) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } else {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }
}