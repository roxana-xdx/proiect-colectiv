package backend.service;

import backend.Repository.I_Repo;
import backend.dto.ScheduleDTO;
import backend.entities.Schedule;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class ScheduleService implements I_service{
    private final I_Repo ScheduleRepository;

    public ScheduleService(I_Repo ScheduleRepository) {
        this.ScheduleRepository = ScheduleRepository;
    }

    // ===================================================================
    // METODE DE MAPARE (CONVERSIE DTO <-> ENTITY)
    // ===================================================================

    /**
     * Convertește Schedule Entity -> ScheduleDTO
     */
    private ScheduleDTO toDTO(Schedule entity) {
        return new ScheduleDTO(
                entity.getId(),
                entity.getTeacher_id(),
                entity.getSubject_id(),
                entity.getClass_id(),
                entity.getDate(),
                entity.getStart_hour(),
                entity.getEnd_hour()
        );
    }

    /**
     * Convertește ScheduleDTO -> Schedule Entity
     * ACEASTA ERA METODA CARE LIPSEA ȘI GENERA EROAREA.
     */
    private Schedule toEntity(ScheduleDTO dto) {
        // Presupunem că constructorul entității Schedule suportă toate câmpurile
        return new Schedule(
                dto.getId(),
                dto.getTeacher_id(),
                dto.getSubject_id(),
                dto.getClass_id(),
                dto.getDate(),
                dto.getStart_hour(),
                dto.getEnd_hour()
        );
    }

    // ===================================================================
    // IMPLEMENTAREA METODELOR DIN INTERFATA SERVICE
    // ===================================================================

    @Override
    public List<ScheduleDTO> getAllSchedules() {
        return ScheduleRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleDTO getScheduleById(Long id) {
        Schedule entity = ScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));

        return toDTO(entity);
    }

    @Override
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        // 1. Validare
        if (!scheduleDTO.getStart_hour().isBefore(scheduleDTO.getEnd_hour())) {
            throw new IllegalArgumentException("Start hour must be before end hour.");
        }

        // 2. Mapare DTO -> Entity (apelăm metoda nou adăugată)
        Schedule scheduleEntity = toEntity(scheduleDTO);

        // 3. Salvare Entity
        Schedule savedEntity = ScheduleRepository.save(scheduleEntity);

        // 4. Mapare Entity salvat -> DTO de returnat
        return toDTO(savedEntity);
    }

    @Override
    public void updateSchedule(ScheduleDTO scheduleDTO) {
        Long id = scheduleDTO.getId();
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null for update operation.");
        }

        ScheduleRepository.findById(id)
                .map(existingEntity -> {
                    // 1. Validare
                    if (!scheduleDTO.getStart_hour().isBefore(scheduleDTO.getEnd_hour())) {
                        throw new IllegalArgumentException("Start hour must be before end hour.");
                    }

                    // 2. Transfer date din DTO in Entity-ul existent
                    existingEntity.setTeacher_id(scheduleDTO.getTeacher_id());
                    existingEntity.setSubject_id(scheduleDTO.getSubject_id());
                    existingEntity.setClass_id(scheduleDTO.getClass_id());
                    existingEntity.setDate(scheduleDTO.getDate());
                    existingEntity.setStart_hour(scheduleDTO.getStart_hour());
                    existingEntity.setEnd_hour(scheduleDTO.getEnd_hour());

                    // 3. Salvare Entity actualizat
                    return ScheduleRepository.save(existingEntity);
                })
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));
    }

    @Override
    public void deleteSchedule(Long id) {
        if(!ScheduleRepository.existsById(id)){
            throw new RuntimeException("Schedule not found with ID: " + id);
        }
        ScheduleRepository.deleteById(id);
    }

    @Override
    public List<ScheduleDTO> findByClassId(Long classId) {
        return ScheduleRepository.findByClass_id(classId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}