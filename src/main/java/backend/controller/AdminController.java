package backend.controller;

import backend.dto.AdminDTO;
import backend.dto.admin.CreateAdminRequest;
import backend.entity.Admin;
import backend.mapper.AdminMapper;
import backend.service.I_AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

    private final I_AdminService adminService;

    @Autowired
    public AdminController(I_AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Create admin from existing user
     */
    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody @Valid CreateAdminRequest req) {
        try {
            Admin created = adminService.createAdminByEmail(req.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(AdminMapper.toDTO(created));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all admins
     */
    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> dtos = AdminMapper.toDTOList(adminService.getAllAdmins());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get admin by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id)
                .map(AdminMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Get admin by email
     */
    @GetMapping("/by-email/{email}")
    public ResponseEntity<AdminDTO> getAdminByEmail(@PathVariable String email) {
        return adminService.getAdminByEmail(email)
                .map(AdminMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Delete admin
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        try {
            adminService.deleteAdmin(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}