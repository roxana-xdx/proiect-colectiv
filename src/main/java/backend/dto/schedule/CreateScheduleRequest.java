package backend.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateScheduleRequest {

    // Relații: Toate ID-urile cheilor externe sunt obligatorii și pozitive la creare
    @JsonProperty("teacher_id")
    @NotNull(message = "Teacher ID is mandatory")
    @Min(value = 1, message = "Teacher ID must be positive")
    private Long teacher_id;

    @JsonProperty("subject_id")
    @NotNull(message = "Subject ID is mandatory")
    @Min(value = 1, message = "Subject ID must be positive")
    private Long subject_id;

    @JsonProperty("class_id")
    @NotNull(message = "Class ID is mandatory")
    @Min(value = 1, message = "Class ID must be positive")
    private Long class_id;

    // Câmpuri orar: Toate sunt obligatorii
    @NotNull(message = "Date is mandatory")
    private LocalDate date;

    @JsonProperty("start_hour")
    @NotNull(message = "Start hour is mandatory")
    private LocalTime start_hour;

    @JsonProperty("end_hour")
    @NotNull(message = "End hour is mandatory")
    private LocalTime end_hour;

    // Constructor implicit (necesar pentru Jackson)
    public CreateScheduleRequest() {}

    // Constructor complet
    public CreateScheduleRequest(Long teacher_id, Long subject_id, Long class_id, LocalDate date, LocalTime start_hour, LocalTime end_hour) {
        this.teacher_id = teacher_id;
        this.subject_id = subject_id;
        this.class_id = class_id;
        this.date = date;
        this.start_hour = start_hour;
        this.end_hour = end_hour;
    }

    // Getters and Setters
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