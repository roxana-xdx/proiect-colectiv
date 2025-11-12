package backend.dto;

import backend.entity.Admin;
import backend.entity.ClassAnnouncement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Data Transfer Object for ClassAnnouncement.
 * Similar to AdminDTO, includes validation annotations, constructors,
 * getters/setters and helper conversion methods.
 */
public class ClassAnnouncementDTO {

    private Long id;

    @NotNull
    private Long adminId;

    @NotNull
    private Long classId;

    @NotNull
    @Size(min = 1, max = 255)
    private String message;

    @NotNull
    private LocalDate date;

    // Admin information (from linked Admin)
    private String adminEmail;
    private String adminName;

    /**
     * All-args constructor.
     */
    public ClassAnnouncementDTO(Long id, Long adminId, Long classId, String message, LocalDate date,
                                String adminEmail, String adminName) {
        this.id = id;
        this.adminId = adminId;
        this.classId = classId;
        this.message = message;
        this.date = date;
        this.adminEmail = adminEmail;
        this.adminName = adminName;
    }

    /**
     * No-args constructor.
     */
    public ClassAnnouncementDTO() {}

    /**
     * Build DTO from ClassAnnouncement entity. Reads the Admin relation for email/name if present.
     *
     * @param announcement entity
     * @return ClassAnnouncementDTO or null if announcement is null
     */
    public static ClassAnnouncementDTO fromEntity(ClassAnnouncement announcement) {
        if (announcement == null) return null;

        Admin admin = announcement.getAdmin();
        return new ClassAnnouncementDTO(
                announcement.getId(),
                admin != null ? admin.getId() : null,
                announcement.getClassId(),
                announcement.getMessage(),
                announcement.getDate(),
                admin != null && admin.getUser() != null ? admin.getUser().getEmail() : null,
                admin != null && admin.getUser() != null ? admin.getUser().getName() : null
        );
    }

    /**
     * Convert DTO to entity without setting the Admin relation.
     * Service should fetch the Admin and set it on the returned ClassAnnouncement before saving.
     *
     * @return ClassAnnouncement entity with id possibly set; admin NOT set
     */
    public ClassAnnouncement toEntity() {
        ClassAnnouncement announcement = new ClassAnnouncement();
        if (this.id != null) {
            announcement.setId(this.id);
        }
        announcement.setClassId(this.classId);
        announcement.setMessage(this.message);
        announcement.setDate(this.date);
        // do not set announcement.setAdmin(...) here - service must provide managed Admin
        return announcement;
    }

    /**
     * Convert DTO to entity and set provided managed Admin.
     *
     * @param admin managed Admin entity to link
     * @return ClassAnnouncement entity ready to persist
     */
    public ClassAnnouncement toEntityWithAdmin(Admin admin) {
        ClassAnnouncement announcement = new ClassAnnouncement();
        if (this.id != null) {
            announcement.setId(this.id);
        }
        announcement.setAdmin(admin);
        announcement.setClassId(this.classId);
        announcement.setMessage(this.message);
        announcement.setDate(this.date);
        return announcement;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}