package backend.controller;

import backend.dto.ClassAnnouncementDTO;
import backend.entity.ClassAnnouncement;
import backend.mapper.ClassAnnouncementMapper;
import backend.service.I_ClassAnnouncementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/class-announcements")
public class ClassAnnouncementController {

    @Autowired
    private I_ClassAnnouncementService classAnnouncementService;

    @PostMapping
    public ResponseEntity<?> createAnnouncement(@RequestBody @Valid ClassAnnouncementDTO announcementDTO) {
        try {
            ClassAnnouncement created = classAnnouncementService.createAnnouncement(
                    announcementDTO.getAdminId(),
                    announcementDTO.getClassId(),
                    announcementDTO.getMessage(),
                    announcementDTO.getDate()
            );
            ClassAnnouncementDTO dto = ClassAnnouncementMapper.toDTO(created);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ClassAnnouncementDTO>> getAllAnnouncements() {
        List<ClassAnnouncementDTO> dtos = ClassAnnouncementMapper.toDTOList(
                classAnnouncementService.getAllAnnouncements());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnnouncementById(@PathVariable Long id) {
        Optional<ClassAnnouncement> opt = classAnnouncementService.getAnnouncementById(id);
        if (opt.isPresent()) {
            ClassAnnouncementDTO dto = ClassAnnouncementMapper.toDTO(opt.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Announcement not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnnouncement(
            @PathVariable Long id,
            @RequestBody @Valid ClassAnnouncementDTO announcementDTO) {
        try {
            ClassAnnouncement updated = classAnnouncementService.updateAnnouncement(
                    id,
                    announcementDTO.getMessage(),
                    announcementDTO.getDate()
            );
            ClassAnnouncementDTO dto = ClassAnnouncementMapper.toDTO(updated);
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
    public ResponseEntity<?> deleteAnnouncement(@PathVariable Long id) {
        try {
            classAnnouncementService.deleteAnnouncement(id);
            return ResponseEntity.ok("Announcement deleted");
        } catch (RuntimeException e) {
            if (e instanceof IllegalStateException) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } else {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }
}