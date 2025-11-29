package backend.entity;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;


@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private SchoolClass classEntity;

    @NonNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NonNull
    @Column(name = "start_hour", nullable = false)
    private LocalTime start_hour;

    @NonNull
    @Column(name = "end_hour", nullable = false)
    private LocalTime end_hour;

    @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn mapează câmpul 'subject_id' din tabela 'class_schedule'
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;


    // 1. Constructor implicit (fără argumente) - NECESAR pentru JPA/Hibernate
    public Schedule() {
    }

    // 2. Constructor complet - NECESAR pentru metoda toEntity() din Service
    public Schedule(Long id, Teacher teacher, Subject subject, SchoolClass classEntity, LocalDate date, LocalTime start_hour, LocalTime end_hour) {
        this.id = id;
        this.teacher = teacher;
        this.subject = subject;
        this.classEntity = classEntity;
        this.date = date;
        this.start_hour = start_hour;
        this.end_hour = end_hour;
    }

    // 3. Constructor pentru creare (fără ID)
    public Schedule(Teacher teacher , Subject subject, SchoolClass classEntity, LocalDate date, LocalTime start_hour, LocalTime end_hour) {
        this.teacher = teacher;
        this.subject = subject;
        this.classEntity = classEntity;
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

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

    public Subject getSubject() { return subject; }

    public void setSubject(Subject subject) { this.subject = subject; }

    public SchoolClass getClassEntity() { return classEntity; }
    public void setClassEntity(SchoolClass classEntity) { this.classEntity = classEntity; }

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
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id) && Objects.equals(teacher, schedule.teacher) && Objects.equals(subject, schedule.subject) && Objects.equals(classEntity, schedule.classEntity) && Objects.equals(date, schedule.date) && Objects.equals(start_hour, schedule.start_hour) && Objects.equals(end_hour, schedule.end_hour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teacher, subject, classEntity, date, start_hour, end_hour);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", teacher_id=" + teacher +
                ", subject_id=" + subject +
                ", class_id=" + classEntity +
                ", date=" + date +
                ", start_hour=" + start_hour +
                ", end_hour=" + end_hour +
                '}';
    }
}