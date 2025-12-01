package backend.service.impl;

import backend.entity.Admin;
import backend.entity.SchoolClass;
import backend.entity.validation.ClassAnnouncementValidator;
import backend.repository.I_SchoolClassRepository;
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
@Transactional(readOnly = true)
public class ClassAnnouncementService implements I_ClassAnnouncementService {

    private final I_ClassAnnouncementRepository classAnnouncementRepository;
    private final I_SchoolClassRepository schoolClassRepository;
    private final I_AdminRepository adminRepository;

    @Autowired
    public ClassAnnouncementService(I_ClassAnnouncementRepository classAnnouncementRepository,
                                    I_SchoolClassRepository schoolClassRepository,
                                    I_AdminRepository adminRepository) {
        this.classAnnouncementRepository = classAnnouncementRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    @Transactional
    public ClassAnnouncement createAnnouncement(Long adminId, Long classId, String message, LocalDate date) {

        // 1. Validare (inclusiv existența FK)
        ClassAnnouncementValidator.validateCreate(adminId, classId, message, date, adminRepository, schoolClassRepository);

        // 2. Preluare referințe (eficient)
        Admin admin = adminRepository.getReferenceById(adminId);
        SchoolClass schoolClass = schoolClassRepository.getReferenceById(classId);

        // 3. Creare și salvare
        ClassAnnouncement announcement = new ClassAnnouncement(admin, schoolClass, message, date);
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
    public List<ClassAnnouncement> getAnnouncementsByClassId(Long classId) {
        if (classId == null) {
            throw new IllegalArgumentException("Class ID cannot be null");
        }
        return classAnnouncementRepository.findBySchoolClass_ClassId(classId);
    }

    @Override
    public List<ClassAnnouncement> getAnnouncementsByAdminId(Long adminId) {
        if (adminId == null) {
            throw new IllegalArgumentException("Admin ID cannot be null");
        }
        return classAnnouncementRepository.findByAdmin_Id(adminId);
    }

    @Override
    @Transactional
    public ClassAnnouncement updateAnnouncement(Long id, String message, LocalDate date) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        ClassAnnouncement announcement = classAnnouncementRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Announcement not found with ID: " + id));

        // Validare câmpuri înainte de actualizare
        ClassAnnouncementValidator.validateUpdate(message, date);

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