package backend.controller;

import backend.dto.ScheduleDTO;
import backend.service.I_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class-schedules")
public class ScheduleController {

    // 1. Variabila injectată, redenumită pentru a respecta convențiile (scheduleService)
    private final I_service scheduleService;

    // 2. Numele Constructorului trebuie să se potrivească cu numele clasei (ScheduleController)
    @Autowired
    public ScheduleController(I_service ScheduleService) {
        // Asignare
        this.scheduleService = ScheduleService;
    }

    /**
     * GET /class-schedules
     * Preia toate înregistrările din orar.
     */
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        try {
            // Folosim variabila corectă: scheduleService
            List<ScheduleDTO> schedules = scheduleService.getAllSchedules();
            return ResponseEntity.ok(schedules); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    /**
     * GET /class-schedules/{id}
     * Preia o înregistrare din orar după ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> getScheduleById(@PathVariable Long id) {
        try {
            // Folosim variabila corectă: scheduleService
            ScheduleDTO schedule = scheduleService.getScheduleById(id);
            return ResponseEntity.ok(schedule); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    /**
     * POST /class-schedules
     * Creează o nouă înregistrare în orar.
     */
    @PostMapping
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        try {
            // Folosim variabila corectă: scheduleService
            ScheduleDTO createdSchedule = scheduleService.createSchedule(scheduleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule); // 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Validation Error: " + e.getMessage()); // 400 Bad Request
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating schedule: " + e.getMessage()); // 500
        }
    }

    /**
     * PUT /class-schedules/{id}
     * Actualizează o înregistrare existentă în orar.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long id, @RequestBody ScheduleDTO scheduleDTO) {
        try {
            scheduleDTO.setId(id);
            // Folosim variabila corectă: scheduleService
            scheduleService.updateSchedule(scheduleDTO);
            return ResponseEntity.ok(scheduleDTO); // 200 OK și returnăm DTO-ul actualizat
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }

    /**
     * DELETE /class-schedules/{id}
     * Șterge o înregistrare din orar.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {
        try {
            // Folosim variabila corectă: scheduleService
            scheduleService.deleteSchedule(id);
            return ResponseEntity.noContent().build(); // 204 No Content (Succes)
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }
}