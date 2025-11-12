package backend.dto;

import java.time.LocalDate;

public class ClassAnnouncementDTO {
    private Long id;
    private Long adminId;
    private Long classId;
    private String message;
    private LocalDate date;

    public ClassAnnouncementDTO() {
    }

    public ClassAnnouncementDTO(Long id, Long adminId, Long classId, String message, LocalDate date) {
        this.id = id;
        this.adminId = adminId;
        this.classId = classId;
        this.message = message;
        this.date = date;
    }

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
}