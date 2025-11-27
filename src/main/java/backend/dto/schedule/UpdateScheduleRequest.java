package backend.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull; // Le vom păstra pentru a evita erori de compilare în Service

import java.time.LocalDate;
import java.time.LocalTime;

public class UpdateScheduleRequest {

    // Câmpurile din DTO-ul de actualizare ar trebui să fie de tipul celor din CreateRequest,
    // dar de obicei permit și valoarea null dacă nu sunt modificate.

    @JsonProperty("teacher_id")
    @Min(value = 1, message = "Teacher ID must be positive")
    // @NotNull nu este necesar aici dacă permitem update parțial, dar Service-ul trebuie să gestioneze null-ul.
    private Long teacher_id;

    @JsonProperty("subject_id")
    @Min(value = 1, message = "Subject ID must be positive")
    private Long subject_id;

    @JsonProperty("class_id")
    @Min(value = 1, message = "Class ID must be positive")
    private Long class_id;

    private LocalDate date;

    @JsonProperty("start_hour")
    private LocalTime start_hour;

    @JsonProperty("end_hour")
    private LocalTime end_hour;

    // Constructori și Getters/Setters
    // (omise pentru concizie)
    public UpdateScheduleRequest() {}
    public Long getTeacher_id() { return teacher_id; }
    public void setTeacher_id(Long teacher_id) { this.teacher_id = teacher_id; }
    public Long getSubject_id() { return subject_id; }
    public void setSubject_id(Long subject_id) { this.subject_id = subject_id; }
    public Long getClass_id() { return class_id; }
    public void setClass_id(Long class_id) { this.class_id = class_id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getStart_hour() { return start_hour; }
    public void setStart_hour(LocalTime start_hour) { this.start_hour = start_hour; }
    public LocalTime getEnd_hour() { return end_hour; }
    public void setEnd_hour(LocalTime end_hour) { this.end_hour = end_hour; }
}