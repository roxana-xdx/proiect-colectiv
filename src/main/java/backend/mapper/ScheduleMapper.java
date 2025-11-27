package backend.mapper;

import backend.dto.ScheduleDTO;
import backend.entity.Schedule;
import backend.entity.Subject;
import backend.entity.Teacher;
import backend.entity.SchoolClass; // Folosim SchoolClass, conform proiectului tău
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper utility class for converting between {@link Schedule} and {@link ScheduleDTO}.
 * All methods static.
 */
public final class ScheduleMapper {

    private ScheduleMapper() {}

    /**
     * Converts a Schedule entity to a ScheduleDTO.
     * Extracts IDs from all related entities (Teacher, Subject, SchoolClass).
     * @param entity The Schedule entity.
     * @return The corresponding ScheduleDTO.
     */
    public static ScheduleDTO toDTO(Schedule entity) {
        if (entity == null) return null;

        // Extragem ID-urile din entitățile relaționate.
        // Presupunem getTeacher(), getSubject() și getClassEntity() pentru câmpurile din Schedule.
        Long teacherId = entity.getTeacher() != null ? entity.getTeacher().getId() : null;
        Long subjectId = entity.getSubject() != null ? entity.getSubject().getId() : null;
        // CORECTIE: Extragem ID-ul clasei din entitatea Class/SchoolClass
        Long classId = entity.getClassEntity() != null ? entity.getClassEntity().getClassId() : null;

        return new ScheduleDTO(
                entity.getId(),
                teacherId,
                subjectId,
                classId, // ID-ul Clasei
                entity.getDate(),
                entity.getStart_hour(),
                entity.getEnd_hour()
        );
    }

    /**
     * Converts a ScheduleDTO and its required related entities to a Schedule entity.
     * Service layer is responsible for fetching all three required entities based on DTO IDs.
     * * @param dto The ScheduleDTO.
     * @param teacher The fetched Teacher entity.
     * @param subject The fetched Subject entity.
     * @param schoolClass The fetched SchoolClass entity.
     * @return The corresponding Schedule entity.
     */
    public static Schedule toEntity(ScheduleDTO dto, Teacher teacher, Subject subject, SchoolClass schoolClass) {
        if (dto == null) return null;

        // CORECTIE: Metoda primeste acum Teacher si SchoolClass ca argumente.
        // Apelam constructorul entitatii Schedule cu cele trei obiecte.
        return new Schedule(
                dto.getId(),
                teacher,        // Injectăm entitatea Teacher
                subject,        // Injectăm entitatea Subject
                schoolClass,    // Injectăm entitatea SchoolClass
                dto.getDate(),
                dto.getStart_hour(),
                dto.getEnd_hour()
        );
    }

    /**
     * Converts a list of Schedule entities to a list of ScheduleDTOs.
     * @param entities The list of Schedule entities.
     * @return The list of ScheduleDTOs.
     */
    public static List<ScheduleDTO> toDTOList(List<Schedule> entities) {
        return entities.stream().map(ScheduleMapper::toDTO).collect(Collectors.toList());
    }
}