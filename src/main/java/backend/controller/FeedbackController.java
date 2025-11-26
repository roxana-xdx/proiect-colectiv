package backend.controller;

import backend.dto.feedback.CreateFeedbackRequest;
import backend.dto.FeedbackDTO;
import backend.entity.Feedback;
import backend.service.I_FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final I_FeedbackService feedbackService;

    @Autowired
    public FeedbackController(I_FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<FeedbackDTO> createFeedback(@RequestBody @Valid CreateFeedbackRequest request) {
        try {
            Feedback created = feedbackService.createFeedback(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(FeedbackDTO.fromEntity(created));
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks() {
        List<FeedbackDTO> dtos = feedbackService.getAllFeedbacks()
                .stream()
                .map(FeedbackDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getFeedbackById(@PathVariable Long id) {
        try {
            Feedback feedback = feedbackService.getFeedbackById(id);
            return ResponseEntity.ok(FeedbackDTO.fromEntity(feedback));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/by-teacher/{teacherId}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksByTeacher(@PathVariable Long teacherId) {
        List<FeedbackDTO> dtos = feedbackService.getFeedbacksByTeacher(teacherId)
                .stream()
                .map(FeedbackDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/by-pupil/{pupilId}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksByPupil(@PathVariable Long pupilId) {
        List<FeedbackDTO> dtos = feedbackService.getFeedbacksByPupil(pupilId)
                .stream()
                .map(FeedbackDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/by-pupil/{pupilId}/sorted")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksByPupilSorted(@PathVariable Long pupilId) {
        List<FeedbackDTO> dtos = feedbackService.getFeedbacksByPupilSortedByGradeDesc(pupilId)
                .stream()
                .map(FeedbackDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackDTO> updateFeedback(@PathVariable Long id,
                                                      @RequestBody @Valid CreateFeedbackRequest request) {
        try {
            Feedback updated = feedbackService.updateFeedback(id, request);
            return ResponseEntity.ok(FeedbackDTO.fromEntity(updated));
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        try {
            feedbackService.deleteFeedback(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
