package backend.mapper;

import backend.dto.ClassDTO;
import backend.entity.Clasa;

import java.util.List;

public final class ClassMapper {
    private ClassMapper() {}

    public static ClassDTO toDTO(Clasa clasa){
        return ClassDTO.fromEntity(clasa);
    }

    public static Clasa toEntity(ClassDTO dto){
        if(dto == null) return null;
        return dto.toEntity();
    }

    public static List<ClassDTO> toDTOList(List<Clasa> classes){
        return classes.stream().map(ClassMapper::toDTO).toList();
    }

    public static List<Clasa> toEntityList(List<ClassDTO> dtos){
        return dtos.stream().map(ClassMapper::toEntity).toList();
    }
}
