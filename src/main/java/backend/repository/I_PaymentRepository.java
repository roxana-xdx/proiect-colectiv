package backend.repository;

import backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface I_PaymentRepository extends JpaRepository<Payment, Long> {
}
