package backend.service;

import backend.domain.Admin;
import backend.domain.ClassAnnouncement;
import backend.domain.ClassEntity;
import backend.repository.ClassAnnouncementDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClassAnnouncementService {

    private final ClassAnnouncementDBRepository classAnnouncementRepository;

    @Autowired
    public ClassAnnouncementService(ClassAnnouncementDBRepository classAnnouncementRepository) {
        this.classAnnouncementRepository = classAnnouncementRepository;
    }

    // Create a new announcement
    @Transactional
    public ClassAnnouncement createAnnouncement(Admin admin, ClassEntity classEntity, String message, LocalDate date) {
        // Validare date de intrare
        if (admin == null) {
            throw new IllegalArgumentException("Admin cannot be null");
        }
        if (classEntity == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        ClassAnnouncement announcement = new ClassAnnouncement(admin, classEntity, message, date);
        return classAnnouncementRepository.save(announcement);
    }

    // Find announcement by ID
    public Optional<ClassAnnouncement> getAnnouncementById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return classAnnouncementRepository.findById(id);
    }

    // Find all announcements
    public List<ClassAnnouncement> getAllAnnouncements() {
        return classAnnouncementRepository.findAll();
    }

    // Find announcements for a specific class
    public List<ClassAnnouncement> getAnnouncementsByClassId(Long classId) {
        if (classId == null) {
            throw new IllegalArgumentException("Class ID cannot be null");
        }
        return classAnnouncementRepository.findByClassEntityId(classId);
    }

    // Find announcements by admin ID
    public List<ClassAnnouncement> getAnnouncementsByAdminId(Long adminId) {
        if (adminId == null) {
            throw new IllegalArgumentException("Admin ID cannot be null");
        }
        return classAnnouncementRepository.findByAdminId(adminId);
    }

    // Find announcements for a class after a specific date
    public List<ClassAnnouncement> getAnnouncementsForClassAfterDate(Long classId, LocalDate date) {
        if (classId == null || date == null) {
            throw new IllegalArgumentException("Class ID and date cannot be null");
        }
        return classAnnouncementRepository.findByClassEntityIdAndDateAfter(classId, date);
    }

    // Get announcements for a class sorted by date descending
    public List<ClassAnnouncement> getAnnouncementsByClassIdSortedByDate(Long classId) {
        if (classId == null) {
            throw new IllegalArgumentException("Class ID cannot be null");
        }
        return classAnnouncementRepository.findByClassEntityIdOrderByDateDesc(classId);
    }

    // Update an announcement
    @Transactional
    public ClassAnnouncement updateAnnouncement(Long id, String message, LocalDate date) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Optional<ClassAnnouncement> existingAnnouncement = classAnnouncementRepository.findById(id);
        if (existingAnnouncement.isEmpty()) {
            throw new IllegalArgumentException("Announcement with ID " + id + " not found");
        }

        ClassAnnouncement announcement = existingAnnouncement.get();

        if (message != null && !message.trim().isEmpty()) {
            announcement.setMessage(message);
        }

        if (date != null) {
            announcement.setDate(date);
        }

        return classAnnouncementRepository.save(announcement);
    }

    // Delete an announcement
    @Transactional
    public void deleteAnnouncement(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!classAnnouncementRepository.existsById(id)) {
            throw new IllegalArgumentException("Announcement with ID " + id + " not found");
        }

        classAnnouncementRepository.deleteById(id);
    }
}