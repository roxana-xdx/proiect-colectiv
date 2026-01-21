package backend.repository;

import backend.entity.Payment;
import backend.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface I_PaymentRepository extends JpaRepository<Payment, Long> {

    // Spring Data JPA va converti automat ENUM-ul la String (din setările JPA)
    List<Payment> findByParent(Parent parent);
    List<Payment> findByParentOrderByPaymentDateDesc(Parent parent);
    List<Payment> findByStatus(String status);
    List<Payment> findByParentAndStatus(Parent parent, String status);

    // Queries ajustate pentru a folosi valoarea String a ENUM-ului
    @Query("SELECT p FROM Payment p WHERE p.dueDate < CURRENT_TIMESTAMP AND p.status = 'PENDING'")
    List<Payment> findOverduePayments();

    @Query("SELECT p FROM Payment p WHERE p.parent = :parent AND p.dueDate < CURRENT_TIMESTAMP AND p.status = 'PENDING'")
    List<Payment> findOverduePaymentsByParent(@Param("parent") Parent parent);

    @Query("SELECT COUNT(p) FROM Payment p WHERE p.parent = :parent AND p.status = 'PAID'")
    Long countPaidPaymentsByParent(@Param("parent") Parent parent);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.parent = :parent AND p.status = 'PAID'")
    BigDecimal sumPaidAmountByParent(@Param("parent") Parent parent);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.parent = :parent AND p.status = 'PENDING'")
    BigDecimal sumPendingAmountByParent(@Param("parent") Parent parent);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Payment p WHERE p.id = :paymentId AND p.parent.id = :parentId")
    boolean existsByIdAndParentId(@Param("paymentId") Long paymentId, @Param("parentId") Long parentId);

    List<Payment> findTop5ByOrderByPaymentDateDesc();
}