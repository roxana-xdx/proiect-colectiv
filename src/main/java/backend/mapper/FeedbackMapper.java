package backend.mapper;

import backend.dto.FeedbackDTO;
import backend.entity.Feedback;
import backend.entity.Pupil;
import backend.entity.Teacher;

import java.util.List;
import java.util.stream.Collectors;

public final class FeedbackMapper {

    private FeedbackMapper() { /* utility class */ }

    public static FeedbackDTO toDTO(Feedback feedback) {
        return FeedbackDTO.fromEntity(feedback);
    }

    public static Feedback toEntity(FeedbackDTO dto) {
        if (dto == null) return null;
        return dto.toEntity();
    }

    public static Feedback toEntityWithRefs(FeedbackDTO dto, Teacher teacher, Pupil pupil /*, Subject subject */) {
        if (dto == null) return null;
        return dto.toEntityWithRefs(teacher, pupil /*, subject */);
    }

    public static List<FeedbackDTO> toDTOList(List<Feedback> feedbacks) {
        return feedbacks.stream()
                .map(FeedbackMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Feedback> toEntityList(List<FeedbackDTO> dtoList) {
        return dtoList.stream()
                .map(FeedbackMapper::toEntity)
                .collect(Collectors.toList());
    }
}
