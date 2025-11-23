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
import java.util.Optional;

@RestController
@RequestMapping("/pupils")
public class PupilController {

    @Autowired
    private I_PupilService pupilService;

    @GetMapping
    public ResponseEntity<List<PupilDTO>> getAllPupils() {
        List<PupilDTO> dtos = PupilMapper.toDTOList(pupilService.getAllPupils());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PupilDTO> getPupilById(@PathVariable Long id) {
        Optional<Pupil> opt = pupilService.getPupilById(id);
        return opt.map(p -> ResponseEntity.ok(PupilMapper.toDTO(p)))
                .orElseGet(() -> ResponseEntity.<PupilDTO>status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<?> createPupil(@RequestBody @Valid CreatePupilRequest request) {
        try {
            Pupil created = pupilService.createPupil(request.getEmail(), request.getClass_id(), request.getParent_id());
            PupilDTO dto = PupilMapper.toDTO(created);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating pupil: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePupil(@PathVariable Long id, @RequestBody UpdatePupilRequest request) {
        try {
            Pupil updated = pupilService.updatePupil(id, request.getClass_id(), request.getParent_id());
            return ResponseEntity.ok(PupilMapper.toDTO(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating pupil: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePupil(@PathVariable Long id) {
        try {
            pupilService.deletePupil(id);
            return ResponseEntity.ok("Pupil deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting pupil: " + e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PupilDTO> getPupilByEmail(@PathVariable String email) {
        try {
            Optional<Pupil> opt = pupilService.findPupilByEmail(email);
            return opt.map(p -> ResponseEntity.ok(PupilMapper.toDTO(p)))
                    .orElseGet(() -> ResponseEntity.<PupilDTO>status(HttpStatus.NOT_FOUND).build());
        } catch (IllegalArgumentException e) {
            // invalid input
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}