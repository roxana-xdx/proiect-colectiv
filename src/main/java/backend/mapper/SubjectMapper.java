package backend.mapper;

import backend.dto.SubjectDTO;
import backend.entity.Subject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper utility class for converting between {@link Subject} and {@link SubjectDTO}.
 * All methods are static and the class should not be instantiated.
 */
public final class SubjectMapper {

    private SubjectMapper() {}

    /**
     * Convert Subject entity to SubjectDTO.
     *
     * @param subject entity
     * @return dto
     */
    public static SubjectDTO toDTO(Subject subject) {
        return SubjectDTO.fromEntity(subject);
    }

    /**
     * Convert SubjectDTO to Subject entity.
     *
     * @param dto subject dto
     * @return Subject entity
     */
    public static Subject toEntity(SubjectDTO dto) {
        if (dto == null) return null;
        return dto.toEntity();
    }

    /**
     * Convert a list of Subject entities to a list of SubjectDTO objects.
     *
     * @param entities list of subject entities
     * @return list of subject DTOs
     */
    public static List<SubjectDTO> toDTOList(List<Subject> entities) {
        return entities.stream().map(SubjectMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * Convert a list of SubjectDTO objects to a list of Subject entities.
     *
     * @param dtoList list of subject DTOs
     * @return list of subject entities
     */
    public static List<Subject> toEntityList(List<SubjectDTO> dtoList) {
        return dtoList.stream().map(SubjectMapper::toEntity).collect(Collectors.toList());
    }
}