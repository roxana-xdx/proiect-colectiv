package backend.service;

import backend.dto.ScheduleDTO;

import java.util.List;

public interface I_ScheduleService {


    List<ScheduleDTO> getAllSchedules();
    ScheduleDTO getScheduleById(Long id);
    ScheduleDTO createSchedule(ScheduleDTO scheduleDTO);
    void updateSchedule(ScheduleDTO scheduleDTO);
    void deleteSchedule(Long id);

    List<ScheduleDTO> findByClassId(Long classId);
}