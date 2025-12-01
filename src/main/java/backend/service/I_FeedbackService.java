package backend.service;

import backend.dto.feedback.CreateFeedbackRequest;
import backend.entity.Feedback;

import java.util.List;

public interface I_FeedbackService {

    Feedback createFeedback(CreateFeedbackRequest request);
    Feedback getFeedbackById(Long id);
    List<Feedback> getAllFeedbacks();
    List<Feedback> getFeedbacksByTeacher(Long teacherId);
    List<Feedback> getFeedbacksByPupil(Long pupilId);
    List<Feedback> getFeedbacksByPupilSortedByGradeDesc(Long pupilId);

    // Metode noi
    List<Feedback> getFeedbacksBySubject(Long subjectId);
    List<Feedback> getFeedbacksByTeacherAndSubject(Long teacherId, Long subjectId);

    Feedback updateFeedback(Long id, CreateFeedbackRequest request);
    void deleteFeedback(Long id);
}