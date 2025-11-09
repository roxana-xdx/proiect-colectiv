package backend.mapper;

import backend.dto.PupilDTO;
import backend.entity.Pupil;

import java.util.List;
import java.util.stream.Collectors;

public class PupilMapper {
    private PupilMapper(){}

    public static PupilDTO toDTO(Pupil pupil){
        PupilDTO dto = new PupilDTO();
        dto.setId(pupil.getId());
        dto.setClass_id(pupil.getClass_id());
        dto.setParent_id(pupil.getParent_id());
        dto.setEmail(pupil.getEmail());
        return dto;
    }

    public static Pupil toEntity(PupilDTO dto){
        Pupil pupil = new Pupil();
        pupil.setId(dto.getId());
        pupil.setClass_id(dto.getClass_id());
        pupil.setParent_id(dto.getParent_id());
        pupil.setEmail(dto.getEmail());
        return pupil;
    }

    public static List<Pupil> toEntityList(List<PupilDTO> dtos){
        return dtos.stream().map(PupilMapper::toEntity).collect(Collectors.toList());
    }

    public static List<PupilDTO> toDTOList(List<Pupil> pupils){
        return pupils.stream().map(PupilMapper::toDTO).collect(Collectors.toList());
    }
}
