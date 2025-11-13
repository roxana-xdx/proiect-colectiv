package backend.service.impl;

import backend.entity.Admin;
import backend.entity.ClassAnnouncement;
import backend.repository.I_AdminRepository;
import backend.repository.I_ClassAnnouncementRepository;
import backend.service.I_ClassAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClassAnnouncementService implements I_ClassAnnouncementService {

    @Autowired
    private I_ClassAnnouncementRepository classAnnouncementRepository;

    @Autowired
    private I_AdminRepository adminRepository;

    @Override
    @Transactional
    public ClassAnnouncement createAnnouncement(Long adminId, Long classId, String message, LocalDate date) {
        // Validare date
        if (adminId == null) {
            throw new IllegalArgumentException("Admin ID cannot be null");
        }
        if (classId == null) {
            throw new IllegalArgumentException("Class ID cannot be null");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        // Gasim adminul din baza de date
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalStateException("Admin not found with ID: " + adminId));

        // Cream anuntul
        ClassAnnouncement announcement = new ClassAnnouncement();
        announcement.setAdmin(admin);
        announcement.setClassId(classId);
        announcement.setMessage(message);
        announcement.setDate(date);

        return classAnnouncementRepository.save(announcement);
    }

    @Override
    public List<ClassAnnouncement> getAllAnnouncements() {
        return classAnnouncementRepository.findAll();
    }

    @Override
    public Optional<ClassAnnouncement> getAnnouncementById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return classAnnouncementRepository.findById(id);
    }

    @Override
    @Transactional
    public ClassAnnouncement updateAnnouncement(Long id, String message, LocalDate date) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        ClassAnnouncement announcement = classAnnouncementRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Announcement not found with ID: " + id));

        if (message != null && !message.trim().isEmpty()) {
            announcement.setMessage(message);
        }

        if (date != null) {
            announcement.setDate(date);
        }

        return classAnnouncementRepository.save(announcement);
    }

    @Override
    @Transactional
    public void deleteAnnouncement(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!classAnnouncementRepository.existsById(id)) {
            throw new IllegalStateException("Announcement with ID " + id + " not found");
        }

        classAnnouncementRepository.deleteById(id);
    }
}