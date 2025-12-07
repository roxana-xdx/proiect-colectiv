package backend.service;

import backend.entity.Payment;
import backend.entity.enums.PaymentStatus; // Adăugat ENUM
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface I_PaymentService {
    List<Payment> getAllPayments();
    Optional<Payment> getPaymentById(Long id);
    Payment createPayment(Payment payment);
    Payment updatePayment(Long id, Payment paymentDetails);
    void deletePayment(Long id);

    List<Payment> getPaymentsByParent(Long parentId);
    List<Payment> getPaymentHistoryByParent(Long parentId);
    List<Payment> getPendingPaymentsByParent(Long parentId);
    List<Payment> getOverduePaymentsByParent(Long parentId);

    List<Payment> getPaymentsByStatus(PaymentStatus status); // Folosește ENUM
    List<Payment> getOverduePayments();

    boolean markAsPaid(Long paymentId);
    boolean markAsCancelled(Long paymentId);

    BigDecimal getTotalPaidByParent(Long parentId);
    BigDecimal getTotalPendingByParent(Long parentId);
    Long getPaidPaymentsCountByParent(Long parentId);

    boolean canParentAccessPayment(Long paymentId, Long parentId);

    List<Payment> getRecentPayments();
}