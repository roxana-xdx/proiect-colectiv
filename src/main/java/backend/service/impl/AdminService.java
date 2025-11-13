package backend.service.impl;

import backend.entity.Admin;
import backend.entity.validation.AdminValidator;
import backend.repository.I_AdminRepository;
import backend.repository.I_UserRepository;
import backend.entity.User;
import backend.service.I_AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements I_AdminService {

    @Autowired
    private I_AdminRepository adminRepository;

    @Autowired
    private I_UserRepository userRepository;

    @Override
    @Transactional
    public Admin createAdminByEmail(String email) {
        AdminValidator.validateCreate(email, userRepository, adminRepository);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found after validation"));
        Admin admin = new Admin();
        admin.setUser(user);
        return adminRepository.save(admin);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public Optional<Admin> getAdminByEmail(String email) {
        return adminRepository.findByUser_Email(email);
    }

    @Override
    @Transactional
    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new IllegalStateException("Admin with id " + id + " not found");
        }
        adminRepository.deleteById(id);
    }
}