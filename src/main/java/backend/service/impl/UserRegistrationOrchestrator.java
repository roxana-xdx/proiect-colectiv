package backend.service.impl;

import backend.entity.User;
import backend.entity.validation.UserValidator;
import backend.repository.I_UserRepository;
import backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Orchestrator service that handles user registration with automatic profile creation.
 * This service coordinates between UserRepository and type-specific services (Admin, Teacher, Parent, Pupil).
 *
 * All operations are wrapped in a single transaction to ensure data consistency.
 */
@Service
public class UserRegistrationOrchestrator {

    private final I_UserRepository userRepository;
    private final I_AdminService adminService;
    private final I_TeacherService teacherService;
    private final I_ParentService parentService;
    private final I_PupilService pupilService;

    @Autowired
    public UserRegistrationOrchestrator(
            I_UserRepository userRepository,
            I_AdminService adminService,
            I_TeacherService teacherService,
            I_ParentService parentService,
            I_PupilService pupilService) {
        this.userRepository = userRepository;
        this.adminService = adminService;
        this.teacherService = teacherService;
        this.parentService = parentService;
        this.pupilService = pupilService;
    }

    /**
     * Registers a new user and automatically creates their type-specific profile.
     *
     * @param user the user to register (must have valid email, password, name, and type)
     * @return the saved user entity
     * @throws IllegalArgumentException if validation fails
     * @throws IllegalStateException if email is already registered
     */
    @Transactional
    public User registerUserWithProfile(User user) {
        UserValidator.validateRegister(user, userRepository);

        User saved = userRepository.save(user);

        switch (saved.getType()) {
            case ADMIN:
                adminService.createAdminByEmail(saved.getEmail());
                break;
            case TEACHER:
                teacherService.createTeacherByEmail(saved.getEmail());
                break;
            case PARENT:
                parentService.createParent(saved.getEmail());
                break;
            case PUPIL:
                pupilService.createPupilByEmail(saved.getEmail());
                break;
            default:
                throw new IllegalStateException("Unknown user type: " + saved.getType());
        }

        return saved;
    }
}