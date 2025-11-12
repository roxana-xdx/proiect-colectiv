package backend.controller;

import backend.domain.Admin;
import backend.domain.ClassAnnouncement;
import backend.domain.ClassEntity;
import backend.dto.ClassAnnouncementDTO;
import backend.dto.ClassAnnouncementMapper;
import backend.repository.AdminRepository;
import backend.repository.ClassEntityRepository;
import backend.service.ClassAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/class-announcements")
public class ClassAnnouncementController {

    private final ClassAnnouncementService classAnnouncementService;
    private final ClassAnnouncementMapper classAnnouncementMapper;
    private final AdminRepository adminRepository;
    private final ClassEntityRepository classEntityRepository;

    @Autowired
    public ClassAnnouncementController(
            ClassAnnouncementService classAnnouncementService,
            ClassAnnouncementMapper classAnnouncementMapper,
            AdminRepository adminRepository,
            ClassEntityRepository classEntityRepository) {
        this.classAnnouncementService = classAnnouncementService;
        this.classAnnouncementMapper = classAnnouncementMapper;
        this.adminRepository = adminRepository;
        this.classEntityRepository = classEntityRepository;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ClassAnnouncementDTO> createAnnouncement(@RequestBody ClassAnnouncementDTO announcementDTO) {
        try {
            // Gasim adminul si clasa din baza de date
            Admin admin = adminRepository.findById(announcementDTO.getAdminId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Admin not found with ID: " + announcementDTO.getAdminId()));

            ClassEntity classEntity = classEntityRepository.findById(announcementDTO.getClassId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Class not found with ID: " + announcementDTO.getClassId()));

            ClassAnnouncement announcement = classAnnouncementService.createAnnouncement(
                    admin,
                    classEntity,
                    announcementDTO.getMessage(),
                    announcementDTO.getDate()
            );

            // Convertim inapoi la DTO si returnam
            return new ResponseEntity<>(classAnnouncementMapper.toDTO(announcement), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // READ - toate anunturile
    @GetMapping
    public ResponseEntity<List<ClassAnnouncementDTO>> getAllAnnouncements() {
        List<ClassAnnouncement> announcements = classAnnouncementService.getAllAnnouncements();
        return ResponseEntity.ok(classAnnouncementMapper.toDTOList(announcements));
    }

    // READ - un anunt dupa ID
    @GetMapping("/{id}")
    public ResponseEntity<ClassAnnouncementDTO> getAnnouncementById(@PathVariable Long id) {
        try {
            Optional<ClassAnnouncement> announcement = classAnnouncementService.getAnnouncementById(id);

            if (announcement.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(classAnnouncementMapper.toDTO(announcement.get()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // READ - anunturi pentru o clasa specificata
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<ClassAnnouncementDTO>> getAnnouncementsByClassId(@PathVariable Long classId) {
        try {
            List<ClassAnnouncement> announcements = classAnnouncementService.getAnnouncementsByClassId(classId);
            return ResponseEntity.ok(classAnnouncementMapper.toDTOList(announcements));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // READ - anunturi pentru o clasa, sortate dupa data (cele mai recente primele)
    @GetMapping("/class/{classId}/sorted")
    public ResponseEntity<List<ClassAnnouncementDTO>> getAnnouncementsByClassIdSorted(@PathVariable Long classId) {
        try {
            List<ClassAnnouncement> announcements = classAnnouncementService.getAnnouncementsByClassIdSortedByDate(classId);
            return ResponseEntity.ok(classAnnouncementMapper.toDTOList(announcements));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // READ - anunturi postate de un admin specificat
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<ClassAnnouncementDTO>> getAnnouncementsByAdminId(@PathVariable Long adminId) {
        try {
            List<ClassAnnouncement> announcements = classAnnouncementService.getAnnouncementsByAdminId(adminId);
            return ResponseEntity.ok(classAnnouncementMapper.toDTOList(announcements));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // READ - anunturi pentru o clasa dupa o data specificata
    @GetMapping("/class/{classId}/after")
    public ResponseEntity<List<ClassAnnouncementDTO>> getAnnouncementsForClassAfterDate(
            @PathVariable Long classId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<ClassAnnouncement> announcements =
                    classAnnouncementService.getAnnouncementsForClassAfterDate(classId, date);
            return ResponseEntity.ok(classAnnouncementMapper.toDTOList(announcements));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ClassAnnouncementDTO> updateAnnouncement(
            @PathVariable Long id,
            @RequestBody ClassAnnouncementDTO announcementDTO) {
        try {
            ClassAnnouncement updatedAnnouncement = classAnnouncementService.updateAnnouncement(
                    id,
                    announcementDTO.getMessage(),
                    announcementDTO.getDate()
            );

            return ResponseEntity.ok(classAnnouncementMapper.toDTO(updatedAnnouncement));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        try {
            classAnnouncementService.deleteAnnouncement(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}