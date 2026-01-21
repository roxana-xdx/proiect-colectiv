package backend.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;


@Entity
@Table(name = "schedules")
public class Schedule implements Serializable { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relatia N:1 cu Teacher
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    // Relatia N:1 cu Subject
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    // Relatia N:1 cu Class (SchoolClass)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "class_id", nullable = false)
    private SchoolClass classEntity;

    @Column(name = "date", nullable = false)
    private LocalDate date; 

    @Column(name = "start_hour", nullable = false)
    private LocalTime startHour;

    @Column(name = "end_hour", nullable = false)
    private LocalTime endHour;

    // Constructori, Getters/Setters, etc.

    public Schedule() {
    }

    public Schedule(Teacher teacher , Subject subject, SchoolClass classEntity, LocalDate date, LocalTime startHour, LocalTime endHour) {
        this.teacher = teacher;
        this.subject = subject;
        this.classEntity = classEntity;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public SchoolClass getClassEntity() { return classEntity; }
    public void setClassEntity(SchoolClass classEntity) { this.classEntity = classEntity; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getStartHour() { return startHour; }
    public void setStartHour(LocalTime startHour) { this.startHour = startHour; }

    public LocalTime getEndHour() { return endHour; }
    public void setEndHour(LocalTime endHour) { this.endHour = endHour; }

    // Implementarea equals/hashCode/toString bazate pe noile nume de câmpuri (startHour, endHour)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", teacher_id=" + (teacher != null ? teacher.getId() : "null") +
                ", subject_id=" + (subject != null ? subject.getId() : "null") +
                ", class_id=" + (classEntity != null ? classEntity.getClassId() : "null") +
                ", date=" + date +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                '}';
    }
}