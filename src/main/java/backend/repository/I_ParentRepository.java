package backend.repository;

import backend.dto.ParentDTO;
import backend.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface I_ParentRepository extends JpaRepository<Parent, String> {
    ParentDTO findById(Long id);
    ParentDTO findByEmail(String email);
    boolean existsById(Long id);
    boolean existsByEmail(String email);
}
