package backend.service;

import backend.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface I_ScheduleService {

    List<Schedule> getAllSchedules();
    Optional<Schedule> getScheduleById(Long id);
    Schedule createSchedule(Long teacherId, Long subjectId, Long classId, LocalDate date, LocalTime startHour, LocalTime endHour);
    Schedule updateSchedule(Long id, Long teacherId, Long subjectId, Long classId, LocalDate date, LocalTime startHour, LocalTime endHour);
    void deleteSchedule(Long id);

    // Metodele din repository folosite: findByClassId, findByTeacherId
    List<Schedule> findByClassId(Long classId);
    List<Schedule> findByTeacherId(Long teacherId);
}