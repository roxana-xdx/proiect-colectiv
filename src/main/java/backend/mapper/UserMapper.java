package backend.mapper;

import backend.dto.UserDTO;
import backend.entity.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper utility class for converting between {@link User} and {@link UserDTO}.
 * All methods static — service/controller are responsible for handling password and persistence.
 */
public final class UserMapper {

    private UserMapper() {}

    public static UserDTO toDTO(User user) {
        return UserDTO.fromEntity(user);
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;
        return dto.toEntity();
    }

    public static User toEntity(UserDTO dto, String password) {
        if (dto == null) return null;
        return dto.toEntityWithPassword(password);
    }

    public static List<UserDTO> toDTOList(List<User> users) {
        return users.stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    public static List<User> toEntityList(List<UserDTO> dtos) {
        return dtos.stream().map(UserMapper::toEntity).collect(Collectors.toList());
    }
}