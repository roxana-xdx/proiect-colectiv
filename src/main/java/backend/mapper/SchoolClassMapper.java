package backend.mapper;

import backend.dto.SchoolClassDTO;
import backend.entity.SchoolClass;

import java.util.List;
import java.util.stream.Collectors;

public final class SchoolClassMapper {
    private SchoolClassMapper() {}

    public static SchoolClassDTO toDTO(SchoolClass clasa){
        return SchoolClassDTO.fromEntity(clasa);
    }

    public static List<SchoolClassDTO> toDTOList(List<SchoolClass> classes){
        return classes.stream().map(SchoolClassMapper::toDTO).collect(Collectors.toList());
    }
}