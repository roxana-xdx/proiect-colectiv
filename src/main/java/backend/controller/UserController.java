package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private I_UserService userService;

    @PostMapping("/login")
        try {
            User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword())
                    .orElseThrow(() -> new RuntimeException("Invalid credentials"));
            return ResponseEntity.ok("Logged in as " + user.getType());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/register")
        try {
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{email}/type")
    public ResponseEntity<String> getUserType(@PathVariable String email) {
        return userService.getUserType(email)
                .map(type -> ResponseEntity.ok(type.toString()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    }

    @GetMapping
    }

    @GetMapping("/{email}")
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{email}")
        try {
            return ResponseEntity.ok("User updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        try {
            userService.deleteUser(email);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}