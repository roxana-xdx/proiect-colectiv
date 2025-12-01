package backend.controller;

import backend.dto.feedback.CreateFeedbackRequest;
import backend.dto.FeedbackDTO;
import backend.entity.Feedback;
import backend.mapper.FeedbackMapper; // Adaugat Mapper
import backend.service.I_FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/feedbacks")
public class FeedbackController {

    private final I_FeedbackService feedbackService;

    @Autowired
    public FeedbackController(I_FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * Create feedback/grade
     */
    @PostMapping
    public ResponseEntity<FeedbackDTO> createFeedback(@RequestBody @Valid CreateFeedbackRequest request) {
        try {
            Feedback created = feedbackService.createFeedback(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(FeedbackMapper.toDTO(created));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found (FK missing)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all feedbacks
     */
    @GetMapping
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks() {
        List<FeedbackDTO> dtos = FeedbackMapper.toDTOList(feedbackService.getAllFeedbacks());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get feedback by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getFeedbackById(@PathVariable Long id) {
        try {
            Feedback feedback = feedbackService.getFeedbackById(id);
            return ResponseEntity.ok(FeedbackMapper.toDTO(feedback));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    /**
     * Get feedbacks by Teacher ID
     */
    @GetMapping("/by-teacher/{teacherId}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksByTeacher(@PathVariable Long teacherId) {
        List<FeedbackDTO> dtos = FeedbackMapper.toDTOList(feedbackService.getFeedbacksByTeacher(teacherId));
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get feedbacks by Pupil ID
     */
    @GetMapping("/by-pupil/{pupilId}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksByPupil(@PathVariable Long pupilId) {
        List<FeedbackDTO> dtos = FeedbackMapper.toDTOList(feedbackService.getFeedbacksByPupil(pupilId));
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get feedbacks by Subject ID
     */
    @GetMapping("/by-subject/{subjectId}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksBySubject(@PathVariable Long subjectId) {
        List<FeedbackDTO> dtos = FeedbackMapper.toDTOList(feedbackService.getFeedbacksBySubject(subjectId));
        return ResponseEntity.ok(dtos);
    }


    /**
     * Get feedbacks by Pupil ID, sorted by grade descending
     */
    @GetMapping("/by-pupil/{pupilId}/sorted")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksByPupilSorted(@PathVariable Long pupilId) {
        List<FeedbackDTO> dtos = FeedbackMapper.toDTOList(feedbackService.getFeedbacksByPupilSortedByGradeDesc(pupilId));
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get feedbacks by Teacher and Subject
     */
    @GetMapping("/by-teacher/{teacherId}/subject/{subjectId}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksByTeacherAndSubject(
            @PathVariable Long teacherId,
            @PathVariable Long subjectId) {
        List<FeedbackDTO> dtos = FeedbackMapper.toDTOList(feedbackService.getFeedbacksByTeacherAndSubject(teacherId, subjectId));
        return ResponseEntity.ok(dtos);
    }


    /**
     * Update feedback
     */
    @PutMapping("/{id}")
    public ResponseEntity<FeedbackDTO> updateFeedback(@PathVariable Long id,
                                                      @RequestBody @Valid CreateFeedbackRequest request) {
        try {
            Feedback updated = feedbackService.updateFeedback(id, request);
            return ResponseEntity.ok(FeedbackMapper.toDTO(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found (Feedback sau FK missing)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete feedback
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        try {
            feedbackService.deleteFeedback(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}