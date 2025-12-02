package backend.controller;

import backend.dto.PupilDTO;
import backend.dto.pupil.CreatePupilRequest;
import backend.dto.pupil.UpdatePupilRequest;
import backend.entity.Pupil;
import backend.mapper.PupilMapper;
import backend.service.I_PupilService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pupils")
public class PupilController {

    private final I_PupilService pupilService;

    @Autowired
    public PupilController(I_PupilService pupilService) {
        this.pupilService = pupilService;
    }

    /**
     * Get all pupils
     */
    @GetMapping
    public ResponseEntity<List<PupilDTO>> getAllPupils() {
        List<PupilDTO> dtos = PupilMapper.toDTOList(pupilService.getAllPupils());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get pupil by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PupilDTO> getPupilById(@PathVariable Long id) {
        return pupilService.getPupilById(id)
                .map(PupilMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Get pupil by email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<PupilDTO> getPupilByEmail(@PathVariable String email) {
        return pupilService.findPupilByEmail(email)
                .map(PupilMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Create pupil with parent and class
     */
    @PostMapping
    public ResponseEntity<PupilDTO> createPupil(@RequestBody @Valid CreatePupilRequest request) {
        try {
            Pupil created = pupilService.createPupil(
                    request.getEmail(),
                    request.getClass_id(),
                    request.getParent_id()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(PupilMapper.toDTO(created));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update pupil's parent and/or class
     */
    @PutMapping("/{id}")
    public ResponseEntity<PupilDTO> updatePupil(
            @PathVariable Long id,
            @RequestBody UpdatePupilRequest request) {
        try {
            Pupil updated = pupilService.updatePupil(id, request.getClass_id(), request.getParent_id());
            return ResponseEntity.ok(PupilMapper.toDTO(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete pupil
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePupil(@PathVariable Long id) {
        try {
            pupilService.deletePupil(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}