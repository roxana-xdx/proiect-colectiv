package backend.service.impl;

import backend.dto.feedback.CreateFeedbackRequest;
import backend.entity.Feedback;
import backend.entity.Pupil;
import backend.entity.Subject;
import backend.entity.Teacher;
import backend.repository.I_FeedbackRepository;
import backend.repository.I_PupilRepository;
import backend.repository.I_SubjectRepository;
import backend.repository.I_TeacherRepository;
import backend.entity.validation.FeedbackValidator;
import backend.service.I_FeedbackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class FeedbackService implements I_FeedbackService {

    private final I_FeedbackRepository feedbackRepo;
    private final I_TeacherRepository teacherRepo;
    private final I_PupilRepository pupilRepo;
    private final I_SubjectRepository subjectRepo;

    public FeedbackService(I_FeedbackRepository feedbackRepo,
                           I_TeacherRepository teacherRepo,
                           I_PupilRepository pupilRepo,
                           I_SubjectRepository subjectRepo) {
        this.feedbackRepo = feedbackRepo;
        this.teacherRepo = teacherRepo;
        this.pupilRepo = pupilRepo;
        this.subjectRepo = subjectRepo;
    }

    @Override
    @Transactional
    public Feedback createFeedback(CreateFeedbackRequest request) {
        FeedbackValidator.validateCreate(
                request.getTeacherId(),
                request.getPupilId(),
                request.getSubjectId(),
                request.getMessage(),
                request.getGrade(),
                teacherRepo,
                pupilRepo,
                subjectRepo
        );

        Teacher teacher = teacherRepo.getReferenceById(request.getTeacherId());
        Pupil pupil = pupilRepo.getReferenceById(request.getPupilId());
        Subject subject = subjectRepo.getReferenceById(request.getSubjectId());

        Feedback feedback = new Feedback(teacher, pupil, subject, request.getMessage(), Instant.now(), request.getGrade());

        return feedbackRepo.save(feedback);
    }

    @Override
    public Feedback getFeedbackById(Long id) {
        return feedbackRepo.findById(id)
                .orElseThrow(() -> new IllegalStateException("Feedback not found with id: " + id));
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepo.findAll();
    }

    @Override
    public List<Feedback> getFeedbacksByTeacher(Long teacherId) {
        return feedbackRepo.findByTeacher_Id(teacherId);
    }

    @Override
    public List<Feedback> getFeedbacksByPupil(Long pupilId) {
        return feedbackRepo.findByPupil_Id(pupilId);
    }

    @Override
    public List<Feedback> getFeedbacksBySubject(Long subjectId) {
        return feedbackRepo.findBySubject_Id(subjectId);
    }

    @Override
    public List<Feedback> getFeedbacksByTeacherAndSubject(Long teacherId, Long subjectId) {
        return feedbackRepo.findByTeacher_IdAndSubject_Id(teacherId, subjectId);
    }

    @Override
    public List<Feedback> getFeedbacksByPupilSortedByGradeDesc(Long pupilId) {
        return feedbackRepo.findByPupil_IdOrderByGradeDesc(pupilId);
    }


    @Override
    @Transactional
    public Feedback updateFeedback(Long id, CreateFeedbackRequest request) {
        Feedback existing = feedbackRepo.findById(id)
                .orElseThrow(() -> new IllegalStateException("Feedback not found with id: " + id));

        FeedbackValidator.validateCreate(
                request.getTeacherId(),
                request.getPupilId(),
                request.getSubjectId(),
                request.getMessage(),
                request.getGrade(),
                teacherRepo,
                pupilRepo,
                subjectRepo
        );

        Teacher teacher = teacherRepo.getReferenceById(request.getTeacherId());
        Pupil pupil = pupilRepo.getReferenceById(request.getPupilId());
        Subject subject = subjectRepo.getReferenceById(request.getSubjectId());

        existing.setTeacher(teacher);
        existing.setPupil(pupil);
        existing.setSubject(subject);
        existing.setMessage(request.getMessage());
        existing.setGrade(request.getGrade());
        existing.setDate(Instant.now());

        return feedbackRepo.save(existing);
    }


    @Override
    @Transactional
    public void deleteFeedback(Long id) {
        if (!feedbackRepo.existsById(id)) {
            throw new IllegalStateException("Feedback not found with id: " + id);
        }
        feedbackRepo.deleteById(id);
    }
}