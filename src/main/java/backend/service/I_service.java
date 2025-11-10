package backend.service;

import backend.entities.Schedule;

import java.util.List;

public interface I_service {
    List<Schedule> getAllSchedules();
    Schedule getScheduleById(Long id);
    Schedule createSchedule(Schedule schedule);
    Schedule updateSchedule(Long id, Schedule updatedSchedule);
    void deleteSchedule(Long id);


    List<Schedule> findByClassId(Long classId);
}
