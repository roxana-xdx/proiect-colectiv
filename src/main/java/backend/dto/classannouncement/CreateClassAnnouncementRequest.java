package backend.dto.classannouncement;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Request DTO for creating a new ClassAnnouncement.
 * Does not include id (auto-generated).
 */
public class CreateClassAnnouncementRequest {

    @NotNull(message = "Admin ID is mandatory")
    @JsonProperty("admin_id")
    private Long adminId;

    @NotNull(message = "Class ID is mandatory")
    @JsonProperty("class_id")
    private Long classId;

    @NotNull(message = "Message is mandatory")
    @Size(min = 1, max = 255, message = "Message must be between 1 and 255 characters")
    private String message;

    @NotNull(message = "Date is mandatory")
    private LocalDate date;

    // Constructor implicit (necesar pentru Jackson)
    public CreateClassAnnouncementRequest() {}

    // Constructor complet
    public CreateClassAnnouncementRequest(Long adminId, Long classId, String message, LocalDate date) {
        this.adminId = adminId;
        this.classId = classId;
        this.message = message;
        this.date = date;
    }

    // Getters and Setters
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