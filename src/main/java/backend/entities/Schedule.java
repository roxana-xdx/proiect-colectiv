package backend.entities;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teacher_id", nullable = false)
    private Long teacher_id;

    @Column(name = "class_id", nullable = false)
    private Long class_id;

    @Column(name = "subject_id", nullable = false)
    private Long subject_id;

    @NonNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NonNull
    @Column(name = "start_hour", nullable = false)
    private LocalTime start_hour;

    @NonNull
    @Column(name = "end_hour", nullable = false)
    private LocalTime end_hour;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", insertable = false, updatable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", insertable = false, updatable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", insertable = false, updatable = false)
    private Class classEntity;
    */

    public Schedule(){
    }

    public Schedule(Long teacher_id, Long subject_id, Long class_id, LocalDate date, LocalTime start_hour, LocalTime end_hour) {
        this.teacher_id = teacher_id;
        this.subject_id = subject_id;
        this.class_id = class_id;
        this.date = date;
        this.start_hour = start_hour;
        this.end_hour = end_hour;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTeacher_id() {
        return teacher_id;
    }
    public void setTeacher_id(Long teacher_id) {
        this.teacher_id = teacher_id;
    }
    public Long getClass_id() {
        return class_id;
    }
    public void setClass_id(Long class_id) {
        this.class_id = class_id;
    }
    public Long getSubject_id() {
        return subject_id;
    }
    public void setSubject_id(Long subject_id) {
        this.subject_id = subject_id;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalTime getStart_hour() {
        return start_hour;
    }
    public void setStart_hour(LocalTime start_hour) {
        this.start_hour = start_hour;
    }
    public LocalTime getEnd_hour() {
        return end_hour;
    }
    public void setEnd_hour(LocalTime end_hour) {
        this.end_hour = end_hour;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule that = (Schedule) o;
        return Objects.equals(id, that.id) && Objects.equals(teacher_id, that.teacher_id) && Objects.equals(subject_id, that.subject_id) && Objects.equals(class_id, that.class_id) && Objects.equals(date, that.date) && Objects.equals(start_hour, that.start_hour) && Objects.equals(end_hour, that.end_hour);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, teacher_id, subject_id, class_id, date, start_hour, end_hour);
    }
    @Override
    public String toString() {
        return "Class_Schedule{" +
                "id=" + id +
                ", teacher_id=" + teacher_id +
                ", subject_id=" + subject_id +
                ", class_id=" + class_id +
                ", date=" + date +
                ", start_hour=" + start_hour +
                ", end_hour=" + end_hour +
                '}';
    }
}
