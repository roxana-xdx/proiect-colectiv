package backend.controller;


import backend.dto.PupilDTO;
import backend.entity.Pupil;
import backend.entity.User;
import backend.service.I_PupilService;
import backend.service.I_UserService;
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
            List<PupilDTO> dtos = pupilService.getAllPupils().stream()
                    .map(PupilDTO::toDTO)
                    .toList();
            return ResponseEntity.ok(dtos);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //doesn't work: gives error 404 Not Found all the time
    @GetMapping("/{id}")
    public ResponseEntity<PupilDTO> getPupilById(@PathVariable Long id) {
       try {
           Pupil pupil = pupilService.getPupilById(id);
           PupilDTO dto = PupilDTO.toDTO(pupil);
           return ResponseEntity.ok(dto);
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
    }

    @PostMapping
    public ResponseEntity<?> createPupil(@RequestBody PupilDTO pupildto) {
        try {
            User user = userService.getUserByEmail(pupildto.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found for email: " + pupildto.getEmail()));
            Pupil pupil = new Pupil();
            pupil.setClass_id(pupildto.getClass_id());
            pupil.setParent_id(pupildto.getParent_id());
            pupil.setUser(user);
            pupilService.createPupil(pupil);
            return ResponseEntity.status(HttpStatus.CREATED).body(pupil);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating pupil: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePupil(@PathVariable Long id, @RequestBody PupilDTO pupildto) {
        try {
            pupildto.setId(id);
            Pupil pupil = PupilDTO.toEntity(pupildto);
            pupilService.updatePupil(pupil);
            return ResponseEntity.ok(pupil);
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
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting pupil: " + e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getPupilByEmail(@PathVariable String email) {
        try{
            Optional<PupilDTO> pupil = pupilService.findPupilByEmail(email).stream().map(PupilDTO::toDTO).findFirst();
            return pupil.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving pupil: " + e.getMessage());
        }
    }


}
