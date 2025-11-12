package backend.controller;

import backend.dto.AdminDTO;
import backend.dto.admin.CreateAdminRequest;
import backend.entity.Admin;
import backend.mapper.AdminMapper;
import backend.service.I_AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private I_AdminService adminService;

    @PostMapping
    public ResponseEntity<?> createAdmin(@RequestBody @Valid CreateAdminRequest req) {
        try {
            Admin created = adminService.createAdminByEmail(req.getEmail());
            AdminDTO dto = AdminMapper.toDTO(created);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> dtos = AdminMapper.toDTOList(adminService.getAllAdmins());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable Long id) {
        Optional<Admin> opt = adminService.getAdminById(id);
        if (opt.isPresent()) {
            AdminDTO dto = AdminMapper.toDTO(opt.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
        }
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<?> getAdminByEmail(@PathVariable String email) {
        Optional<Admin> opt = adminService.getAdminByEmail(email);
        if (opt.isPresent()) {
            AdminDTO dto = AdminMapper.toDTO(opt.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found for email: " + email);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id) {
        try {
            adminService.deleteAdmin(id);
            return ResponseEntity.ok("Admin deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}