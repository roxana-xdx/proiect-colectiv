package backend.mapper;

import backend.dto.TeacherDTO;
import backend.entity.Teacher;
import backend.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public final class TeacherMapper {


/**
 * Mapper utility class for converting between {@link Teacher} and {@link TeacherDTO}.
 * All methods are static and the class should not be instantiated.
 *
 * Important: service layer is responsible for fetching the User entity and passing it to
 * toEntity(dto, user) when creating/updating Teacher entities.
 */
    private TeacherMapper() {}

    public static TeacherDTO toDTO(Teacher teacher) {
        return TeacherDTO.fromEntity(teacher);
    }

    public static Teacher toEntity(TeacherDTO dto) {
        if (dto == null) return null;
        return dto.toEntity();
    }

    public static Teacher toEntity(TeacherDTO dto, User user) {
        if (dto == null) return null;
        return dto.toEntityWithUser(user);
    }

    public static List<TeacherDTO> toDTOList(List<Teacher> entities) {
        return entities.stream().map(TeacherMapper::toDTO).collect(Collectors.toList());
    }

    public static List<Teacher> toEntityList(List<TeacherDTO> dtoList) {
        return dtoList.stream().map(TeacherMapper::toEntity).collect(Collectors.toList());
    }
}