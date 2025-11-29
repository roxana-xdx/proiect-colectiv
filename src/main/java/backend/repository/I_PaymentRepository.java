package backend.repository;

import backend.entity.Payment;
import backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface I_PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByParent(User parent);

    List<Payment> findByParent_Email(String email);

    boolean existsById(Long id);

    @Query("SELECT p FROM Payment p WHERE p.parent.email = :parentEmail")
    List<Payment> findByParentEmail(@Param("parentEmail") String parentEmail);

    @Query("SELECT COUNT(p) > 0 FROM Payment p WHERE p.id = :id AND p.parent.email = :parentEmail")
    boolean existsByIdAndParentEmail(@Param("id") Long id, @Param("parentEmail") String parentEmail);
}