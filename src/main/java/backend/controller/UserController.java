package backend.controller;

import backend.dto.UserDTO;
import backend.dto.auth.LoginRequest;
import backend.dto.auth.RegisterRequest;
import backend.dto.auth.UpdateRequest;
import backend.entity.User;
import backend.mapper.UserMapper;
import backend.service.I_UserService;
import backend.service.impl.UserRegistrationOrchestrator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final I_UserService userService;
    private final UserRegistrationOrchestrator registrationOrchestrator;

    @Autowired
    public UserController(I_UserService userService,
                          UserRegistrationOrchestrator orchestrator) {
        this.userService = userService;
        this.registrationOrchestrator = orchestrator;
    }


    /**
     * Login endpoint - returns user data on successful authentication
     */
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

            return ResponseEntity.ok(UserMapper.toDTO(user));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Register endpoint - returns created user data
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegisterRequest req) {
        try {
            User u = new User(req.getEmail(), req.getPassword(), req.getName(), req.getType());
            User registered = registrationOrchestrator.registerUserWithProfile(u);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UserMapper.toDTO(registered));

        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * Get all users
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> dtos = UserMapper.toDTOList(userService.getAllUsers());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get user by email
     */
    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(UserMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Get user type
     */
    @GetMapping("/{email}/type")
    public ResponseEntity<String> getUserType(@PathVariable String email) {
        return userService.getUserType(email)
                .map(type -> ResponseEntity.ok(type.toString()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    }

    /**
     * Update user - returns updated user data
     */
    @PutMapping("/{email}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable String email,
            @RequestBody @Valid UpdateRequest req) {
        try {
            User existing = userService.getUserByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            User updated = new User();
            updated.setEmail(existing.getEmail());
            updated.setType(existing.getType());
            updated.setName(req.getName() != null ? req.getName() : existing.getName());
            updated.setPassword(req.getPassword() != null ? req.getPassword() : existing.getPassword());

            User savedUser = userService.updateUser(email, updated);
            return ResponseEntity.ok(UserMapper.toDTO(savedUser));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete user - returns 204 No Content on success
     */
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        try {
            userService.deleteUser(email);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}