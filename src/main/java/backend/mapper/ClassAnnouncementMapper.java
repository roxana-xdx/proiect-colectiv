package backend.mapper;

import backend.dto.ClassAnnouncementDTO;
import backend.entity.Admin;
import backend.entity.ClassAnnouncement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper utility class for converting between {@link ClassAnnouncement} and {@link ClassAnnouncementDTO}.
 * All methods are static and the class should not be instantiated.
 *
 * Important: service layer is responsible for fetching the Admin entity and passing it to
 * toEntity(dto, admin) when creating/updating ClassAnnouncement entities.
 */
public final class ClassAnnouncementMapper {

    private ClassAnnouncementMapper() {}

    /**
     * Convert ClassAnnouncement entity to ClassAnnouncementDTO.
     *
     * @param announcement entity
     * @return dto
     */
    public static ClassAnnouncementDTO toDTO(ClassAnnouncement announcement) {
        return ClassAnnouncementDTO.fromEntity(announcement);
    }

    /**
     * Convert ClassAnnouncementDTO to ClassAnnouncement entity without setting the Admin relation.
     * Service must set the managed Admin on the returned ClassAnnouncement before persisting.
     *
     * @param dto announcement dto
     * @return ClassAnnouncement entity (admin not set)
     */
    public static ClassAnnouncement toEntity(ClassAnnouncementDTO dto) {
        if (dto == null) return null;
        return dto.toEntity();
    }

    /**
     * Convert ClassAnnouncementDTO to ClassAnnouncement entity and set the provided managed Admin.
     *
     * @param dto  announcement dto
     * @param admin managed Admin entity to link
     * @return ClassAnnouncement entity with admin set
     */
    public static ClassAnnouncement toEntity(ClassAnnouncementDTO dto, Admin admin) {
        if (dto == null) return null;
        return dto.toEntityWithAdmin(admin);
    }

    /**
     * Convert a list of ClassAnnouncement entities to a list of ClassAnnouncementDTO objects.
     *
     * @param entities list of announcement entities
     * @return list of announcement DTOs
     */
    public static List<ClassAnnouncementDTO> toDTOList(List<ClassAnnouncement> entities) {
        return entities.stream().map(ClassAnnouncementMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * Convert a list of ClassAnnouncementDTO objects to a list of ClassAnnouncement entities.
     * Note: Admin relations are not set and must be handled by the service layer.
     *
     * @param dtoList list of announcement DTOs
     * @return list of announcement entities (admin not set)
     */
    public static List<ClassAnnouncement> toEntityList(List<ClassAnnouncementDTO> dtoList) {
        return dtoList.stream().map(ClassAnnouncementMapper::toEntity).collect(Collectors.toList());
    }
}