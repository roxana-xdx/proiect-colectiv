package backend.dto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class ScheduleDTO {
    // ID-ul este necesar pentru operațiile de citire și actualizare
    private Long id;

    private Long teacher_id;
    private Long subject_id;
    private Long class_id;

    private LocalDate date;
    private LocalTime start_hour;
    private LocalTime end_hour;

    // Constructor implicit (necesar pentru deserializarea JSON/Jackson)
    public ScheduleDTO() {
    }

    // Constructor pentru creare (fără ID)
    public ScheduleDTO(Long teacher_id, Long subject_id, Long class_id, LocalDate date, LocalTime start_hour, LocalTime end_hour) {
        this.teacher_id = teacher_id;
        this.subject_id = subject_id;
        this.class_id = class_id;
        this.date = date;
        this.start_hour = start_hour;
        this.end_hour = end_hour;
    }

    // Constructor complet
    public ScheduleDTO(Long id, Long teacher_id, Long subject_id, Long class_id, LocalDate date, LocalTime start_hour, LocalTime end_hour) {
        this.id = id;
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

    public Long getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Long subject_id) {
        this.subject_id = subject_id;
    }

    public Long getClass_id() {
        return class_id;
    }

    public void setClass_id(Long class_id) {
        this.class_id = class_id;
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

    // Implementează equals, hashCode și toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleDTO that = (ScheduleDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(teacher_id, that.teacher_id) && Objects.equals(subject_id, that.subject_id) && Objects.equals(class_id, that.class_id) && Objects.equals(date, that.date) && Objects.equals(start_hour, that.start_hour) && Objects.equals(end_hour, that.end_hour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teacher_id, subject_id, class_id, date, start_hour, end_hour);
    }

    @Override
    public String toString() {
        return "ScheduleDTO{" +
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
