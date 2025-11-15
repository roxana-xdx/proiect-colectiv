package backend.mapper;

import backend.dto.AdminDTO;
import backend.entity.Admin;
import backend.entity.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper utility class for converting between {@link Admin} and {@link AdminDTO}.
 * All methods are static and the class should not be instantiated.
 *
 * Important: service layer is responsible for fetching the User entity and passing it to
 * toEntity(dto, user) when creating/updating Admin entities.
 */
public final class AdminMapper {

    private AdminMapper() {}

    /**
     * Convert Admin entity to AdminDTO.
     *
     * @param admin entity
     * @return dto
     */
    public static AdminDTO toDTO(Admin admin) {
        return AdminDTO.fromEntity(admin);
    }

    /**
     * Convert AdminDTO to Admin entity without setting the User relation.
     * Service must set the managed User on the returned Admin before persisting.
     *
     * @param dto admin dto
     * @return Admin entity (user not set)
     */
    public static Admin toEntity(AdminDTO dto) {
        if (dto == null) return null;
        return dto.toEntity();
    }

    /**
     * Convert AdminDTO to Admin entity and set the provided managed User.
     *
     * @param dto  admin dto
     * @param user managed User entity to link
     * @return Admin entity with user set
     */
    public static Admin toEntity(AdminDTO dto, User user) {
        if (dto == null) return null;
        return dto.toEntityWithUser(user);
    }

    public static List<AdminDTO> toDTOList(List<Admin> entities) {
        return entities.stream().map(AdminMapper::toDTO).collect(Collectors.toList());
    }

    public static List<Admin> toEntityList(List<AdminDTO> dtoList) {
        return dtoList.stream().map(AdminMapper::toEntity).collect(Collectors.toList());
    }
}