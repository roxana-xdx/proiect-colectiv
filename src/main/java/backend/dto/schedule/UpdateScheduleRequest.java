package backend.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;

public class UpdateScheduleRequest {
    @JsonProperty("teacher_id")
    private Long teacher_id;

    @JsonProperty("subject_id")
    private Long subject_id;

    @JsonProperty("class_id")
    private Long class_id;

    private LocalDate date;

    @JsonProperty("start_hour")
    private LocalTime start_hour;

    @JsonProperty("end_hour")
    private LocalTime end_hour;

    public UpdateScheduleRequest() {}

    public UpdateScheduleRequest(Long teacher_id, Long subject_id, Long class_id, LocalDate date, LocalTime start_hour, LocalTime end_hour) {
        this.teacher_id = teacher_id;
        this.subject_id = subject_id;
        this.class_id = class_id;
        this.date = date;
        this.start_hour = start_hour;
        this.end_hour = end_hour;
    }

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