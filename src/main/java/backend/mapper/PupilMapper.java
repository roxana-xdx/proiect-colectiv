package backend.mapper;

import backend.dto.PupilDTO;
import backend.entity.Pupil;
import backend.entity.User;

import java.util.List;

public final class PupilMapper {
    private PupilMapper() {}

    public static PupilDTO toDTO(Pupil pupil) {
        return PupilDTO.toDTO(pupil);
    }

    public static Pupil toEntity(PupilDTO pupilDTO) {
        if(pupilDTO == null) return null;
        return pupilDTO.toEntity();
    }

    public static Pupil toEntity(PupilDTO pupilDTO, User user) {
        if(pupilDTO == null) return null;
        return pupilDTO.toEntityWithUser(user);
    }

    public static List<PupilDTO> toDTOList(List<Pupil> pupilList) {
        return pupilList.stream().map(PupilMapper::toDTO).toList();
    }

    public static List<Pupil> toEntityList(List<PupilDTO> pupilDTOList) {
        return pupilDTOList.stream().map(PupilMapper::toEntity).toList();
    }
}
