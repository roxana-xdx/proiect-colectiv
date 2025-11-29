package backend.controller;

import backend.dto.ScheduleDTO;
import backend.dto.schedule.CreateScheduleRequest;
import backend.dto.schedule.UpdateScheduleRequest;
import backend.entity.Schedule;
import backend.mapper.ScheduleMapper;
import backend.service.I_ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final I_ScheduleService scheduleService;

    @Autowired
    public ScheduleController(I_ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * Get all schedules
     */
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        List<ScheduleDTO> dtos = ScheduleMapper.toDTOList(scheduleService.getAllSchedules());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get schedule by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id)
                .map(ScheduleMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Create schedule with teacher, subject and class
     */
    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody @Valid CreateScheduleRequest request) {
        try {
            Schedule created = scheduleService.createSchedule(
                    request.getTeacher_id(),
                    request.getSubject_id(),
                    request.getClass_id(),
                    request.getDate(),
                    request.getStart_hour(),
                    request.getEnd_hour()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(ScheduleMapper.toDTO(created));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update schedule's teacher, subject, class, date and/or hours
     */
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDTO> updateSchedule(
            @PathVariable Long id,
            @RequestBody @Valid UpdateScheduleRequest request) {
        try {
            Schedule updated = scheduleService.updateSchedule(
                    id,
                    request.getTeacher_id(),
                    request.getSubject_id(),
                    request.getClass_id(),
                    request.getDate(),
                    request.getStart_hour(),
                    request.getEnd_hour()
            );
            return ResponseEntity.ok(ScheduleMapper.toDTO(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete schedule
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        try {
            scheduleService.deleteSchedule(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}