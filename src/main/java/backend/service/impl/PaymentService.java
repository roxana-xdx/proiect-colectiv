package backend.service.impl;

import backend.entity.Payment;
import backend.entity.Parent;
import backend.entity.enums.PaymentStatus;
import backend.entity.validation.PaymentValidator;
import backend.repository.I_PaymentRepository;
import backend.repository.I_ParentRepository;
import backend.service.I_PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PaymentService implements I_PaymentService {

    private final I_PaymentRepository paymentRepository;
    private final I_ParentRepository parentRepository;

    @Autowired
    public PaymentService(I_PaymentRepository paymentRepository,
                          I_ParentRepository parentRepository) {
        this.paymentRepository = paymentRepository;
        this.parentRepository = parentRepository;
    }

    private Parent getParentById(Long parentId) {
        return parentRepository.findById(parentId)
                .orElseThrow(() -> new IllegalStateException("Parent not found with id: " + parentId));
    }

    @Override
    @Transactional
    public Payment createPayment(Payment payment) {
        // Validare Business Logic
        PaymentValidator.validateCreate(payment.getParent().getId(), payment.getAmount(), payment.getDueDate(), parentRepository);

        payment.setPaymentDate(LocalDateTime.now());

        // Setează starea (implicit PENDING) și verifică OVERDUE
        if (payment.getDueDate().isBefore(LocalDateTime.now())) {
            payment.setStatus(PaymentStatus.OVERDUE);
        } else if (payment.getStatus() == null) {
            payment.setStatus(PaymentStatus.PENDING);
        }

        // Dacă PaymentMethod nu este setat, poate fi lăsat null sau setat default
        // Lăsăm null, conform schemei, dar ar putea fi setat default

        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status.toString());
    }

    @Override
    @Transactional
    public boolean markAsPaid(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalStateException("Payment not found with id: " + paymentId));

        if (payment.canBePaid()) {
            payment.setStatus(PaymentStatus.PAID);
            payment.setPaymentDate(LocalDateTime.now());
            paymentRepository.save(payment);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean markAsCancelled(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalStateException("Payment not found with id: " + paymentId));

        payment.setStatus(PaymentStatus.CANCELLED);
        paymentRepository.save(payment);
        return true;
    }

    // ... (Restul metodelor rămân similare, ajustate pentru a folosi getParentById și ENUM-uri)

    @Override public List<Payment> getAllPayments() { return paymentRepository.findAll(); }
    @Override public Optional<Payment> getPaymentById(Long id) { return paymentRepository.findById(id); }
    // ... (Alte metode care apelează direct Repository)

    @Override
    @Transactional
    public Payment updatePayment(Long id, Payment paymentDetails) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Payment not found with id: " + id));

        if (payment.getStatus() == PaymentStatus.PAID || payment.getStatus() == PaymentStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot modify details for a finalized payment.");
        }

        // Validare (amount > 0)
        PaymentValidator.validateCreate(payment.getParent().getId(), paymentDetails.getAmount(), paymentDetails.getDueDate(), parentRepository);

        payment.setAmount(paymentDetails.getAmount());
        payment.setDueDate(paymentDetails.getDueDate());
        payment.setDescription(paymentDetails.getDescription());

        // Re-evaluează starea
        if (payment.getDueDate().isBefore(LocalDateTime.now()) && payment.getStatus() == PaymentStatus.PENDING) {
            payment.setStatus(PaymentStatus.OVERDUE);
        }

        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new IllegalStateException("Payment not found with id: " + id);
        }
        paymentRepository.deleteById(id);
    }

    @Override public List<Payment> getPaymentsByParent(Long parentId) { Parent parent = getParentById(parentId); return paymentRepository.findByParent(parent); }
    @Override public List<Payment> getPaymentHistoryByParent(Long parentId) { Parent parent = getParentById(parentId); return paymentRepository.findByParentOrderByPaymentDateDesc(parent); }
    @Override public List<Payment> getPendingPaymentsByParent(Long parentId) { Parent parent = getParentById(parentId); return paymentRepository.findByParentAndStatus(parent, PaymentStatus.PENDING.toString()); }
    @Override public List<Payment> getOverduePaymentsByParent(Long parentId) { Parent parent = getParentById(parentId); return paymentRepository.findOverduePaymentsByParent(parent); }
    @Override public List<Payment> getOverduePayments() { return paymentRepository.findOverduePayments(); }
    @Override public BigDecimal getTotalPaidByParent(Long parentId) { Parent parent = getParentById(parentId); BigDecimal total = paymentRepository.sumPaidAmountByParent(parent); return total != null ? total : BigDecimal.ZERO; }
    @Override public BigDecimal getTotalPendingByParent(Long parentId) { Parent parent = getParentById(parentId); BigDecimal total = paymentRepository.sumPendingAmountByParent(parent); return total != null ? total : BigDecimal.ZERO; }
    @Override public Long getPaidPaymentsCountByParent(Long parentId) { Parent parent = getParentById(parentId); return paymentRepository.countPaidPaymentsByParent(parent); }
    @Override public boolean canParentAccessPayment(Long paymentId, Long parentId) { return paymentRepository.existsByIdAndParentId(paymentId, parentId); }
    @Override public List<Payment> getRecentPayments() { return paymentRepository.findTop5ByOrderByPaymentDateDesc(); }
}