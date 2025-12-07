package backend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant; // Tip modern pentru dată/timp
import java.util.Objects;

@Entity
@Table(name = "feedbacks")
public class Feedback implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pupil_id", nullable = false)
    private Pupil pupil;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(nullable = false, length = 255)
    private String message;

    @Column(name = "date", nullable = false)
    private Instant date;

    @Column(name = "grade", nullable = false)
    private int grade;

    public Feedback() {}

    public Feedback(Teacher teacher, Pupil pupil, Subject subject, String message, Instant date, int grade) {
        this.teacher = teacher;
        this.pupil = pupil;
        this.subject = subject;
        this.message = message;
        this.date = date;
        this.grade = grade;
    }

    // Getters and Setters (implementare completă)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
    public Pupil getPupil() { return pupil; }
    public void setPupil(Pupil pupil) { this.pupil = pupil; }
    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Instant getDate() { return date; }
    public void setDate(Instant date) { this.date = date; }
    public int getGrade() { return grade; }
    public void setGrade(int grade) { this.grade = grade; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feedback)) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(id, feedback.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", teacherId=" + (teacher != null ? teacher.getId() : null) +
                ", pupilId=" + (pupil != null ? pupil.getId() : null) +
                ", subjectId=" + (subject != null ? subject.getId() : null) +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", grade=" + grade +
                '}';
    }
}