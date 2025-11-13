package backend.controller;

import backend.dto.ParentDTO;
import backend.dto.admin.CreateParentRequest;
import backend.dto.auth.UpdateRequest;
import backend.entity.Parent;
import backend.entity.User;
import backend.mapper.ParentMapper;
import backend.service.I_ParentService;
import backend.service.I_UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parents")
public class ParentController {

    @Autowired
    private I_ParentService parentService;
    private I_UserService userService;

    @PostMapping
    public ResponseEntity<?> createParent(@RequestBody ParentDTO parentDTO) {
        try {
            User user = userService.getUserByEmail(parentDTO.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found for email: " + parentDTO.getEmail()));
            Parent parent = new Parent();
            parent.setUser(user);
            parentService.createParent(parent);
            return ResponseEntity.status(HttpStatus.CREATED).body(parent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating parent: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ParentDTO>>  getAllParents() {
        List<ParentDTO> dtos = ParentMapper.toDTOList(parentService.getAllParents());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/by-email.{email}")
    public ResponseEntity<?> getParentByEmail(@PathVariable String email) {
        Optional<Parent> opt =  parentService.getParentByEmail(email);
        if (opt.isPresent()) {
            ParentDTO dto = ParentMapper.toDTO(opt.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parent not found for email: " + email);
        }
    }

    @DeleteMapping("/{email}")
    public  ResponseEntity<?> deleteParent(@PathVariable String email) {
        try {
            parentService.deleteParent(email);
            return ResponseEntity.ok("Parent deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<String> updateParent(@PathVariable String email, @RequestBody @Valid UpdateRequest req) {
        try {
            Parent p = new Parent();
            p.setEmail(email);
            parentService.updateParent(email, p);
            return ResponseEntity.ok("Parent updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
