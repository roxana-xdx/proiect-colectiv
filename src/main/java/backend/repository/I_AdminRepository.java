package backend.repository;

import backend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface I_AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUser_Email(String email);
    boolean existsByUser_Email(String email);
}