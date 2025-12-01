package backend.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class CreateScheduleRequest {
    @JsonProperty("teacher_id")
    @NotNull
    private Long teacher_id;

    @JsonProperty("subject_id")
    @NotNull
    private Long subject_id;

    @JsonProperty("class_id")
    @NotNull
    private Long class_id;

    @NotNull
    private LocalDate date;

    @JsonProperty("start_hour")
    @NotNull
    private LocalTime startHour;

    @JsonProperty("end_hour")
    @NotNull
    private LocalTime endHour;

    public CreateScheduleRequest() {}

    public CreateScheduleRequest(Long teacher_id, Long subject_id, Long class_id, LocalDate date, LocalTime startHour, LocalTime endHour) {
        this.teacher_id = teacher_id;
        this.subject_id = subject_id;
        this.class_id = class_id;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public Long getTeacher_id() { return teacher_id; }
    public void setTeacher_id(Long teacher_id) { this.teacher_id = teacher_id; }

    public Long getSubject_id() { return subject_id; }
    public void setSubject_id(Long subject_id) { this.subject_id = subject_id; }

    public Long getClass_id() { return class_id; }
    public void setClass_id(Long class_id) { this.class_id = class_id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getStartHour() { return startHour; }
    public void setStartHour(LocalTime startHour) { this.startHour = startHour; }

    public LocalTime getEndHour() { return endHour; }
    public void setEndHour(LocalTime endHour) { this.endHour = endHour; }
}