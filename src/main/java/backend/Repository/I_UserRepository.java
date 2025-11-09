package backend.Repository;
import java.util.Optional;

import backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface I_UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
}
