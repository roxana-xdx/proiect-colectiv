package backend.controller;

import backend.dto.ScheduleDTO;
import backend.dto.schedule.CreateScheduleRequest; // Import NOU
import backend.dto.schedule.UpdateScheduleRequest; // Import NOU
import backend.service.I_ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/class-schedules")
public class ScheduleController {

    private final I_ScheduleService scheduleService;

    @Autowired
    public ScheduleController(I_ScheduleService ScheduleService) {
        this.scheduleService = ScheduleService;
    }

    // ... (Metodele GET rămân la fel) ...

    /**
     * POST /class-schedules
     * Folosește CreateScheduleRequest
     */
    @PostMapping
    public ResponseEntity<?> createSchedule(@Valid @RequestBody CreateScheduleRequest request) {
        try {
            // Conversie Request -> DTO intern
            ScheduleDTO scheduleDTO = new ScheduleDTO(
                    request.getTeacher_id(),
                    request.getSubject_id(),
                    request.getClass_id(),
                    request.getDate(),
                    request.getStart_hour(),
                    request.getEnd_hour()
            );

            ScheduleDTO createdSchedule = scheduleService.createSchedule(scheduleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Validation Error (Service): " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating schedule: " + e.getMessage());
        }
    }

    /**
     * PUT /class-schedules/{id}
     * Folosește UpdateScheduleRequest
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long id, @Valid @RequestBody UpdateScheduleRequest request) {
        try {
            // Conversie Request -> DTO intern
            // NOTĂ: Dacă folosești UpdateRequest, trebuie să te asiguri că toate câmpurile sunt copiate,
            // chiar și cele nule, sau să folosești o logică de mapare mai complexă.
            ScheduleDTO scheduleDTO = new ScheduleDTO(
                    id, // ID-ul din PathVariable
                    request.getTeacher_id(),
                    request.getSubject_id(),
                    request.getClass_id(),
                    request.getDate(),
                    request.getStart_hour(),
                    request.getEnd_hour()
            );

            // Daca un camp este null, se va face update cu null in Service,
            // deci Service-ul tau va trebui sa fie mai inteligent (ex: nu face set la null)

            scheduleService.updateSchedule(scheduleDTO);
            return ResponseEntity.ok(scheduleDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ... (Metoda deleteSchedule și Exception Handler rămân la fel) ...

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {
        try {
            scheduleService.deleteSchedule(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage()
                ));

        return errors;
    }
}