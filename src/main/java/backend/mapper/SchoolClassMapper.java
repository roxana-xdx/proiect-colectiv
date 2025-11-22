package backend.mapper;

import backend.dto.SchoolClassDTO;
import backend.entity.SchoolClass;

import java.util.List;

public final class SchoolClassMapper {
    private SchoolClassMapper() {}

    public static SchoolClassDTO toDTO(SchoolClass clasa){
        return SchoolClassDTO.fromEntity(clasa);
    }

    public static SchoolClass toEntity(SchoolClassDTO dto){
        if(dto == null) return null;
        return dto.toEntity();
    }

    public static List<SchoolClassDTO> toDTOList(List<SchoolClass> classes){
        return classes.stream().map(SchoolClassMapper::toDTO).toList();
    }

    public static List<SchoolClass> toEntityList(List<SchoolClassDTO> dtos){
        return dtos.stream().map(SchoolClassMapper::toEntity).toList();
    }
}