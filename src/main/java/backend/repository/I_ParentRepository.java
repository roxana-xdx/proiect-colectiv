package backend.repository;

import backend.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface I_ParentRepository extends JpaRepository<Parent, Long> {
    Optional<Parent> findByUser_Email(String email);
    boolean existsByUser_Email(String email);
}