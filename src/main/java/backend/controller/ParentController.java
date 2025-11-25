package backend.controller;

import backend.dto.ParentDTO;
import backend.dto.parent.CreateParentRequest;
import backend.entity.Parent;
import backend.mapper.ParentMapper;
import backend.service.I_ParentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parents")
public class ParentController {

    private final I_ParentService parentService;

    @Autowired
    public ParentController(I_ParentService parentService) {
        this.parentService = parentService;
    }

    /**
     * Create parent from existing user
     */
    @PostMapping
    public ResponseEntity<ParentDTO> createParent(@RequestBody @Valid CreateParentRequest request) {
        try {
            Parent created = parentService.createParent(request.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(ParentMapper.toDTO(created));
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all parents
     */
    @GetMapping
    public ResponseEntity<List<ParentDTO>> getAllParents() {
        List<ParentDTO> dtos = ParentMapper.toDTOList(parentService.getAllParents());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get parent by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ParentDTO> getParentById(@PathVariable Long id) {
        return parentService.getParentById(id)
                .map(ParentMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Get parent by email
     */
    @GetMapping("/by-email/{email}")
    public ResponseEntity<ParentDTO> getParentByEmail(@PathVariable String email) {
        return parentService.getParentByEmail(email)
                .map(ParentMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Delete parent
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        try {
            parentService.deleteParent(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}