package backend.repository;
import backend.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface I_FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByTeacher_Id(Long teacherId);
    List<Feedback> findByPupil_Id(Long pupilId);
    List<Feedback> findByPupil_IdOrderByGradeDesc(Long pupilId);
}
