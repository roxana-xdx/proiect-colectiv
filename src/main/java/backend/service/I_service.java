package backend.service;

import backend.dto.ScheduleDTO;
import backend.entities.Schedule;

import java.util.List;

public interface I_service {
    List<ScheduleDTO> getAllSchedules();
    ScheduleDTO getScheduleById(Long id);

    ScheduleDTO createSchedule(ScheduleDTO scheduleDTO);
    void updateSchedule(ScheduleDTO scheduleDTO); // Se folosește DTO-ul, ID-ul e în DTO
    void deleteSchedule(Long id);

    // Poți păstra metodele care lucrează cu entitatea direct dacă nu le expui public.
    // Dar pentru consistență, ar trebui să le transformi și pe acestea.
    List<ScheduleDTO> findByClassId(Long classId);
}
