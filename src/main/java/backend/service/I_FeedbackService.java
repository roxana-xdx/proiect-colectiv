package backend.service;

import backend.dto.feedback.CreateFeedbackRequest;
import backend.entity.Feedback;

import java.util.Date;
import java.util.List;

public interface I_FeedbackService {

    Feedback createFeedback(CreateFeedbackRequest request);
    Feedback getFeedbackById(Long id);
    List<Feedback> getAllFeedbacks();
    List<Feedback> getFeedbacksByTeacher(Long teacherId);
    List<Feedback> getFeedbacksByPupil(Long pupilId);
    List<Feedback> getFeedbacksByPupilSortedByGradeDesc(Long pupilId);
    Feedback updateFeedback(Long id, CreateFeedbackRequest request);
    void deleteFeedback(Long id);
}
