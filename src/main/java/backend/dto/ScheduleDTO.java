package backend.dto;

import backend.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleDTO {
    private Long id;

    @NotNull
    @Min(1)
    private Long teacherId;

    @NotNull
    @Min(1)
    private Long subjectId;

    @NotNull
    @Min(1)
    private Long classId;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime startHour;

    @NotNull
    private LocalTime endHour;

    public ScheduleDTO() {}

    public ScheduleDTO(Long id, Long teacherId, Long subjectId, Long classId, LocalDate date, LocalTime startHour, LocalTime endHour) {
        this.id = id;
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.classId = classId;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public static ScheduleDTO toDTO(Schedule schedule) {
        if (schedule == null) return null;

        Long teacherId = (schedule.getTeacher() != null) ? schedule.getTeacher().getId() : null;
        Long subjectId = (schedule.getSubject() != null) ? schedule.getSubject().getId() : null;
        Long classId = (schedule.getClassEntity() != null) ? schedule.getClassEntity().getClassId() : null; // Corectat ClassEntity

        return new ScheduleDTO(
                schedule.getId(),
                teacherId,
                subjectId,
                classId,
                schedule.getDate(),
                schedule.getStartHour(),
                schedule.getEndHour()
        );
    }

    public Schedule toEntity() {
        Schedule schedule = new Schedule();
        if (this.id != null) schedule.setId(this.id);
        schedule.setDate(this.date);
        schedule.setStartHour(this.startHour);
        schedule.setEndHour(this.endHour);
        return schedule;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @JsonProperty("teacher_id")
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    @JsonProperty("subject_id")
    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }

    @JsonProperty("class_id")
    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    @JsonProperty("start_hour")
    public LocalTime getStartHour() { return startHour; }
    public void setStartHour(LocalTime startHour) { this.startHour = startHour; }

    @JsonProperty("end_hour")
    public LocalTime getEndHour() { return endHour; }
    public void setEndHour(LocalTime endHour) { this.endHour = endHour; }
}