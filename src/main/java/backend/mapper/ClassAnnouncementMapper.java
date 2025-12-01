package backend.mapper;

import backend.dto.ClassAnnouncementDTO;
import backend.entity.ClassAnnouncement;

import java.util.List;
import java.util.stream.Collectors;

public final class ClassAnnouncementMapper {

    private ClassAnnouncementMapper() {}

    public static ClassAnnouncementDTO toDTO(ClassAnnouncement announcement) {
        return ClassAnnouncementDTO.fromEntity(announcement);
    }

    public static List<ClassAnnouncementDTO> toDTOList(List<ClassAnnouncement> entities) {
        return entities.stream().map(ClassAnnouncementMapper::toDTO).collect(Collectors.toList());
    }
}