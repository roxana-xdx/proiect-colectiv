package backend.dto.feedback;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateFeedbackRequest {

    @NotNull(message = "Teacher ID must not be null")
    private Long teacherId;

    @NotNull(message = "Pupil ID must not be null")
    private Long pupilId;

    @NotNull(message = "Subject ID must not be null")
    private Long subjectId; // Activat

    @NotBlank(message = "Message must not be blank")
    @Size(max = 255, message = "Message cannot exceed 255 characters")
    private String message;

    @NotNull(message = "Grade must not be null")
    @Min(value = 1, message = "Grade must be at least 1")
    @Max(value = 10, message = "Grade must be at most 10")
    private Integer grade;

    public CreateFeedbackRequest() {}

    public CreateFeedbackRequest(Long teacherId, Long pupilId, Long subjectId, String message, Integer grade) { // Activat SubjectId
        this.teacherId = teacherId;
        this.pupilId = pupilId;
        this.subjectId = subjectId;
        this.message = message;
        this.grade = grade;
    }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public Long getPupilId() { return pupilId; }
    public void setPupilId(Long pupilId) { this.pupilId = pupilId; }

    public Long getSubjectId() { return subjectId; } // Activat
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; } // Activat

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Integer getGrade() { return grade; }
    public void setGrade(Integer grade) { this.grade = grade; }
}