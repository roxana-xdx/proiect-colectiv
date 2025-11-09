package backend.controller;


import backend.entity.Pupil;
import backend.service.I_PupilService;
import backend.service.PupilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<List<Pupil>> getAllPupils() {
        try {
            List<Pupil> pupils = pupilService.getAllPupils();
            return ResponseEntity.ok(pupils);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //doesn't work: gives error 404 Not Found all the time
    @GetMapping("/{id}")
    public ResponseEntity<Pupil> getPupilById(Long id) {
        try {
            Pupil pupil = pupilService.getPupilById(id);
            return ResponseEntity.ok(pupil);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createPupil(@RequestBody Pupil pupil) {
        try {
            Pupil pupilCreated = pupilService.createPupil(pupil);
            return ResponseEntity.status(HttpStatus.CREATED).body(pupilCreated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating pupil: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePupil(@PathVariable Long id, @RequestBody Pupil pupil) {
        try {
            Pupil pupilUpdated = pupilService.updatePupil(id, pupil);
            return ResponseEntity.ok(pupilUpdated);
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
            Optional<Pupil> pupil = pupilService.findPupilByEmail(email);
            return pupil.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving pupil: " + e.getMessage());
        }
    }


}
