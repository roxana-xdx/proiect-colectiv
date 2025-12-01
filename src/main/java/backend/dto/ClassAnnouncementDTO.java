package backend.dto;

import backend.entity.Admin;
import backend.entity.SchoolClass;
import backend.entity.ClassAnnouncement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

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

    // Informații suplimentare pentru Admin (opțional, dar util în DTO)
    private String adminEmail;
    private String adminName;

    // All-args constructor
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

    // No-args constructor
    public ClassAnnouncementDTO() {}

    public static ClassAnnouncementDTO fromEntity(ClassAnnouncement announcement) {
        if (announcement == null) return null;

        Admin admin = announcement.getAdmin();
        SchoolClass schoolclass = announcement.getSchoolClass();
        return new ClassAnnouncementDTO(
                announcement.getId(),
                announcement.getAdminId(), // Citim direct FK-ul din entitate
                announcement.getClassId(), // Citim direct FK-ul din entitate
                announcement.getMessage(),
                announcement.getDate(),
                admin != null && admin.getUser() != null ? admin.getUser().getEmail() : null,
                admin != null && admin.getUser() != null ? admin.getUser().getName() : null
        );
    }

    // Eliminat toEntity și toEntityWithAdmin/Refs pentru coerență. Service-ul construiește entitatea.

    // Getters and setters (complete)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAdminId() { return adminId; }
    public void setAdminId(Long adminId) { this.adminId = adminId; }
    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getAdminEmail() { return adminEmail; }
    public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }
    public String getAdminName() { return adminName; }
    public void setAdminName(String adminName) { this.adminName = adminName; }
}