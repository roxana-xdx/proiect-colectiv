package backend.controller;

import backend.dto.ClassAnnouncementDTO;
import backend.dto.classannouncement.CreateClassAnnouncementRequest;
import backend.dto.classannouncement.UpdateClassAnnouncementRequest;
import backend.entity.ClassAnnouncement;
import backend.mapper.ClassAnnouncementMapper;
import backend.service.I_ClassAnnouncementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/class-announcements")
public class ClassAnnouncementController {

    private final I_ClassAnnouncementService classAnnouncementService;

    @Autowired
    public ClassAnnouncementController(I_ClassAnnouncementService classAnnouncementService) {
        this.classAnnouncementService = classAnnouncementService;
    }

    /**
     * Create new announcement
     */
    @PostMapping
    public ResponseEntity<ClassAnnouncementDTO> createAnnouncement(@RequestBody @Valid CreateClassAnnouncementRequest request) {
        try {
            ClassAnnouncement created = classAnnouncementService.createAnnouncement(
                    request.getAdminId(),
                    request.getClassId(),
                    request.getMessage(),
                    request.getDate()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(ClassAnnouncementMapper.toDTO(created));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found (FK missing)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all announcements
     */
    @GetMapping
    public ResponseEntity<List<ClassAnnouncementDTO>> getAllAnnouncements() {
        List<ClassAnnouncementDTO> dtos = ClassAnnouncementMapper.toDTOList(
                classAnnouncementService.getAllAnnouncements());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get announcement by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClassAnnouncementDTO> getAnnouncementById(@PathVariable Long id) {
        return classAnnouncementService.getAnnouncementById(id)
                .map(ClassAnnouncementMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Get announcements by Class ID
     */
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<ClassAnnouncementDTO>> getAnnouncementsByClassId(@PathVariable Long classId) {
        List<ClassAnnouncementDTO> dtos = ClassAnnouncementMapper.toDTOList(
                classAnnouncementService.getAnnouncementsByClassId(classId));
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get announcements by Admin ID
     */
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<ClassAnnouncementDTO>> getAnnouncementsByAdminId(@PathVariable Long adminId) {
        List<ClassAnnouncementDTO> dtos = ClassAnnouncementMapper.toDTOList(
                classAnnouncementService.getAnnouncementsByAdminId(adminId));
        return ResponseEntity.ok(dtos);
    }

    /**
     * Update announcement (message and date only)
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClassAnnouncementDTO> updateAnnouncement(
            @PathVariable Long id,
            @RequestBody @Valid UpdateClassAnnouncementRequest request) {
        try {
            ClassAnnouncement updated = classAnnouncementService.updateAnnouncement(
                    id,
                    request.getMessage(),
                    request.getDate()
            );
            return ResponseEntity.ok(ClassAnnouncementMapper.toDTO(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete announcement
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        try {
            classAnnouncementService.deleteAnnouncement(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // ID null
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}