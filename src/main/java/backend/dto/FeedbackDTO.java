package backend.dto;

import backend.entity.Feedback;
import backend.entity.Pupil;
import backend.entity.Teacher;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import javax.security.auth.Subject;
import java.util.Date;

public class FeedbackDTO {

    private Long id;

    @NotNull
    private Long teacherId;

    @NotNull
    private Long pupilId;

//    @NotNull
//    private Long subjectId;

    @NotNull
    @Size(min = 1, max = 255)
    private String message;

    @NotNull
    private Date date;

    @Min(1)
    @Max(10)
    private int grade;

    public FeedbackDTO() {}

    public FeedbackDTO(Long id, Long teacherId, Long pupilId /*,Long SubjectId*/, String message, Date date, int grade) {
        this.id = id;
        this.teacherId = teacherId;
        this.pupilId = pupilId;
//        this.subjectId = SubjectId;
        this.message = message;
        this.date = date;
        this.grade = grade;
    }

    public static FeedbackDTO fromEntity(Feedback feedback) {
        if (feedback == null) return null;

        return new FeedbackDTO(
                feedback.getId(),
                feedback.getTeacher() != null ? feedback.getTeacher().getId() : null,
                feedback.getPupil() != null ? feedback.getPupil().getId() : null,
//                feedback.getSubject() != null ? feedback.getSubject().getId() : null,
                feedback.getMessage(),
                feedback.getDate(),
                feedback.getGrade()
        );
    }

    public Feedback toEntity() {
        Feedback fb = new Feedback();
        if (this.id != null) {
            fb.setId(this.id);
        }
        fb.setMessage(this.message);
        fb.setDate(this.date);
        fb.setGrade(this.grade);
        return fb;
    }

    public Feedback toEntityWithRefs(Teacher teacher, Pupil pupil /*,Subject subject*/) {
        Feedback fb = new Feedback();
        if (this.id != null) {
            fb.setId(this.id);
        }
        fb.setTeacher(teacher);
        fb.setPupil(pupil);
//        fb.setSubject(subject);
        fb.setMessage(this.message);
        fb.setDate(this.date);
        fb.setGrade(this.grade);
        return fb;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public Long getPupilId() { return pupilId; }
    public void setPupilId(Long pupilId) { this.pupilId = pupilId; }

//    public Long getSubjectId() { return subjectId; }
//    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public int getGrade() { return grade; }
    public void setGrade(int grade) { this.grade = grade; }
}
