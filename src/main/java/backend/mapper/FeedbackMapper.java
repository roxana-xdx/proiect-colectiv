package backend.mapper;

import backend.dto.FeedbackDTO;
import backend.entity.Feedback;
import backend.entity.Pupil;
import backend.entity.Teacher;
import backend.entity.Subject; // Activat

import java.util.List;
import java.util.stream.Collectors;

public final class FeedbackMapper {

    private FeedbackMapper() { /* utility class */ }

    public static FeedbackDTO toDTO(Feedback feedback) {
        return FeedbackDTO.fromEntity(feedback);
    }

    public static List<FeedbackDTO> toDTOList(List<Feedback> feedbacks) {
        return feedbacks.stream()
                .map(FeedbackMapper::toDTO)
                .collect(Collectors.toList());
    }
}