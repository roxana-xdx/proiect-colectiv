package backend.controller;

import backend.dto.SubjectDTO;
import backend.dto.subject.CreateSubjectRequest;
import backend.dto.subject.UpdateSubjectRequest;
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

    private final I_SubjectService subjectService;

    @Autowired
    public SubjectController(I_SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * Create new subject
     */
    @PostMapping
    public ResponseEntity<SubjectDTO> createSubject(@RequestBody @Valid CreateSubjectRequest request) {
        try {
            Subject created = subjectService.createSubject(request.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(SubjectMapper.toDTO(created));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        } catch (IllegalStateException e) {
            // Prinde eroarea de unicitate din Service
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all subjects
     */
    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        List<SubjectDTO> dtos = SubjectMapper.toDTOList(subjectService.getAllSubjects());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get subject by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id)
                .map(SubjectMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Update subject name
     */
    @PutMapping("/{id}")
    public ResponseEntity<SubjectDTO> updateSubject(
            @PathVariable Long id,
            @RequestBody @Valid UpdateSubjectRequest request) {
        try {
            Subject updated = subjectService.updateSubject(id, request.getName());
            return ResponseEntity.ok(SubjectMapper.toDTO(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        } catch (IllegalStateException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
            }
            // Eroare de unicitate
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete subject
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        try {
            subjectService.deleteSubject(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // ID null
        } catch (IllegalStateException e) {
            // Prinde 404 sau 409 din service (DataIntegrityViolationException)
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
            }
            // Cannot delete (FK constraint)
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}