package backend.controller;


import backend.dto.PupilDTO;
import backend.dto.pupil.CreatePupilRequest;
import backend.dto.pupil.UpdatePupilRequest;
import backend.entity.Pupil;
import backend.entity.User;
import backend.mapper.PupilMapper;
import backend.service.I_PupilService;
import backend.service.I_UserService;
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

    @Autowired
    private I_UserService userService;

    @GetMapping
    public ResponseEntity<List<PupilDTO>> getAllPupils() {
        try {
            List<PupilDTO> dtos = PupilMapper.toDTOList(pupilService.getAllPupils());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //doesn't work: gives error 404 Not Found all the time
    @GetMapping("/{id}")
    public ResponseEntity<?> getPupilById(@PathVariable Long id) {
        Optional<Pupil> pupils = pupilService.getPupilById(id);
        if (pupils.isPresent()) {
            PupilDTO dto = PupilMapper.toDTO(pupils.get());
            return ResponseEntity.ok(dto);
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pupil not found");
    }

    @PostMapping
    public ResponseEntity<?> createPupil(@RequestBody CreatePupilRequest request) {
        try {
            Pupil created = pupilService.createPupil(request.getEmail(), request.getClass_id(), request.getParent_id());
//            created.setClass_id(request.getClass_id());
//            created.setParent_id(request.getParent_id());
            PupilDTO dto = PupilMapper.toDTO(created);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pupil created successfully: " + dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating pupil: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePupil(@PathVariable Long id, @RequestBody @Valid UpdatePupilRequest request) {
        try {
            Pupil updated = pupilService.updatePupil(id, request.getClass_id(), request.getParent_id());
            PupilDTO dto = PupilMapper.toDTO(updated);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating pupil: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePupil(@PathVariable Long id) {
        try {
            pupilService.deletePupil(id);
            return ResponseEntity.ok("Pupil deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting pupil: " + e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getPupilByEmail(@PathVariable String email) {
        try {
            Optional<PupilDTO> pupil = pupilService.findPupilByEmail(email).stream().map(PupilDTO::toDTO).findFirst();
            return pupil.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving pupil: " + e.getMessage());
        }
    }


}
