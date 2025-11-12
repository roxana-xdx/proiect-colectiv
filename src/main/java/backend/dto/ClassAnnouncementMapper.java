package backend.dto;

import backend.domain.Admin;
import backend.domain.ClassAnnouncement;
import backend.domain.ClassEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClassAnnouncementMapper {
    public ClassAnnouncementDTO toDTO(ClassAnnouncement announcement) {
        return new ClassAnnouncementDTO(
                announcement.getId(),
                announcement.getAdmin().getId(),
                announcement.getClassEntity().getId(),
                announcement.getMessage(),
                announcement.getDate()
        );
    }

    public List<ClassAnnouncementDTO> toDTOList(List<ClassAnnouncement> announcements) {
        return announcements.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Nota: necesita entitati Admin si ClassEntity din repository
    public ClassAnnouncement toEntity(ClassAnnouncementDTO dto, Admin admin, ClassEntity classEntity) {
        ClassAnnouncement announcement = new ClassAnnouncement();
        announcement.setId(dto.getId()); // poate fi null pentru creare
        announcement.setAdmin(admin);
        announcement.setClassEntity(classEntity);
        announcement.setMessage(dto.getMessage());
        announcement.setDate(dto.getDate());
        return announcement;
    }

    // Actualizeaza o entitate existenta cu date din DTO
    public void updateEntityFromDTO(ClassAnnouncementDTO dto, ClassAnnouncement entity) {
        // Nu actualizeaza id, admin sau classEntity, doar campurile de date
        if (dto.getMessage() != null) {
            entity.setMessage(dto.getMessage());
        }
        if (dto.getDate() != null) {
            entity.setDate(dto.getDate());
        }
    }
}