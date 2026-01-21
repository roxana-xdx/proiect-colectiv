package backend.service;

import backend.entity.Admin;

import java.util.List;
import java.util.Optional;

public interface I_AdminService {
    Admin createAdminByEmail(String email);
    List<Admin> getAllAdmins();
    Optional<Admin> getAdminById(Long id);
    Optional<Admin> getAdminByEmail(String email);
    void deleteAdmin(Long id);
}