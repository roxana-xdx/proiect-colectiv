package backend.entity.validation;

import backend.repository.I_AdminRepository;
import backend.repository.I_SchoolClassRepository;

import java.time.LocalDate;

/**
 * Validator for ClassAnnouncement.
 * Contains validation logic for class announcements.
 */
public final class ClassAnnouncementValidator {

    private ClassAnnouncementValidator() { /* utility class */ }

    /**
     * Validează existența FK-urilor și validitatea mesajului/datei la creare.
     */
    public static void validateCreate(Long adminId, Long classId, String message, LocalDate date, I_AdminRepository adminRepo, I_SchoolClassRepository schoolClassRepo) {

        // 1. Validare existență FK
        if (adminId == null) throw new IllegalArgumentException("Admin ID cannot be null");
        if (!adminRepo.existsById(adminId)) {
            throw new IllegalStateException("Admin not found with ID: " + adminId);
        }

        if (classId == null) throw new IllegalArgumentException("Class ID cannot be null");
        if (!schoolClassRepo.existsById(classId)) {
            throw new IllegalStateException("SchoolClass not found with ID: " + classId);
        }

        // 2. Validare conținut
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        if (message.length() > 255) {
            throw new IllegalArgumentException("Message cannot exceed 255 characters");
        }

        // 3. Validare dată
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        LocalDate today = LocalDate.now();
        if (date.isBefore(today)) {
            throw new IllegalArgumentException("Date cannot be in the past");
        }
    }

    /**
     * Validează cerințele la actualizare (doar mesajul și data sunt permise).
     */
    public static void validateUpdate(String message, LocalDate date) {
        // Message validation (if provided)
        if (message != null) {
            if (message.trim().isEmpty()) {
                throw new IllegalArgumentException("Message cannot be empty");
            }
            if (message.length() > 255) {
                throw new IllegalArgumentException("Message cannot exceed 255 characters");
            }
        }

        // Date validation (if provided)
        if (date != null) {
            LocalDate today = LocalDate.now();
            if (date.isBefore(today)) {
                throw new IllegalArgumentException("Date cannot be in the past");
            }
        }
    }
}