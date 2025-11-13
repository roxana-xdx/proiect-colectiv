package backend.mapper;

import backend.dto.ParentDTO;
import backend.dto.UserDTO;
import backend.entity.Parent;
import backend.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class ParentMapper {
    private ParentMapper() {}

    public static ParentDTO toDTO(Parent parent) {
        return ParentDTO.fromEntity(parent);
    }

    public static Parent toEntity(ParentDTO dto) {
        if (dto == null) return null;
        return dto.toEntity();
    }

    public static Parent toEntity(ParentDTO dto, User user) {
        if (dto == null) return null;
        return dto.toEntityWithUser(user);
    }

    public static List<ParentDTO> toDTOList(List<Parent> parents) {
        return parents.stream().map(ParentMapper::toDTO).collect(Collectors.toList());
    }

    public static List<Parent> toEntityList(List<ParentDTO> dtoList) {
        return dtoList.stream().map(ParentMapper::toEntity).collect(Collectors.toList());
    }
}
