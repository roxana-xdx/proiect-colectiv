package backend.service;

import backend.entity.ClassAnnouncement;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface I_ClassAnnouncementService {
    ClassAnnouncement createAnnouncement(Long adminId, Long classId, String message, LocalDate date);
    List<ClassAnnouncement> getAllAnnouncements();
    Optional<ClassAnnouncement> getAnnouncementById(Long id);
    ClassAnnouncement updateAnnouncement(Long id, String message, LocalDate date);
    void deleteAnnouncement(Long id);
}