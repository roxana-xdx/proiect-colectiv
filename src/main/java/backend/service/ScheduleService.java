package backend.service;

import backend.Repository.I_Repo;
import backend.entities.Schedule;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
@Service
@Validated
public class ScheduleService implements I_service{
    private final I_Repo ScheduleRepository;

    public ScheduleService(I_Repo ScheduleRepository) {
        this.ScheduleRepository = ScheduleRepository;
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return ScheduleRepository.findAll();
    }

    @Override
    public Schedule getScheduleById(Long id) {
        return ScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));
    }

    @Override
    public Schedule createSchedule(Schedule schedule) {
        // Validare: ora de incepu sa fie mai mica ca ora de sfarsit
        if (!schedule.getStart_hour().isBefore(schedule.getEnd_hour())) {
            throw new IllegalArgumentException("Start hour must be before end hour.");
        }
        return ScheduleRepository.save(schedule);
    }

    @Override
    public Schedule updateSchedule(Long id, Schedule updatedSchedule) {
        return ScheduleRepository.findById(id)
                .map(existing -> {
                    // VAlidare: ora de incepu sa fie inainte de ora de sfarsit
                    if (!updatedSchedule.getStart_hour().isBefore(updatedSchedule.getEnd_hour())) {
                        throw new IllegalArgumentException("Start hour must be before end hour.");
                    }

                    existing.setTeacher_id(updatedSchedule.getTeacher_id());
                    existing.setSubject_id(updatedSchedule.getSubject_id());
                    existing.setClass_id(updatedSchedule.getClass_id());
                    existing.setDate(updatedSchedule.getDate());
                    existing.setStart_hour(updatedSchedule.getStart_hour());
                    existing.setEnd_hour(updatedSchedule.getEnd_hour());
                    return ScheduleRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));
    }

    @Override
    public void deleteSchedule(Long id) {
        if (!ScheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found with ID: " + id);
        }
        ScheduleRepository.deleteById(id);
    }

    @Override
    public List<Schedule> findByClassId(Long classId) {
        return ScheduleRepository.findByClass_id(classId);
    }
}
