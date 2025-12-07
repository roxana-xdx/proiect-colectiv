package backend.repository;
import backend.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface I_FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByTeacher_Id(Long teacherId);
    List<Feedback> findByPupil_Id(Long pupilId);
    List<Feedback> findByPupil_IdOrderByGradeDesc(Long pupilId);

    // Adaugat metode pentru Subject
    List<Feedback> findBySubject_Id(Long subjectId);
    List<Feedback> findByTeacher_IdAndSubject_Id(Long teacherId, Long subjectId);
}