package backend.controller;

import backend.dto.ParentDTO;
import backend.dto.admin.CreateParentRequest;
import backend.dto.auth.UpdateRequest;
import backend.entity.Parent;
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

    @Autowired
    private I_UserService userService;

    @PostMapping
    public ResponseEntity<?> createParent(@RequestBody @Valid CreateParentRequest req) {
        try {
            Parent parent = parentService.createParent(req.getEmail());
            ParentDTO dto = ParentMapper.toDTO(parent);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
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

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteParent(@PathVariable Long id) {
        try {
            parentService.deleteParent(id);
            return ResponseEntity.ok("Parent deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
