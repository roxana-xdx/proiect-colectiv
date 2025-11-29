package backend.service.impl;

import backend.entity.Payment;
import backend.entity.Parent;
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
public class PaymentService implements I_PaymentService {

    private final I_PaymentRepository paymentRepository;
    private final I_ParentRepository parentRepository;

    @Autowired
    public PaymentService(I_PaymentRepository paymentRepository,
                          I_ParentRepository parentRepository) {
        this.paymentRepository = paymentRepository;
        this.parentRepository = parentRepository;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    @Transactional
    public Payment createPayment(Payment payment) {
        payment.setPaymentDate(LocalDateTime.now());
        if (payment.getStatus() == null) {
            payment.setStatus("PENDING");
        }
        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public Payment updatePayment(Long id, Payment paymentDetails) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Payment not found with id: " + id));

        payment.setAmount(paymentDetails.getAmount());
        payment.setDueDate(paymentDetails.getDueDate());
        payment.setDescription(paymentDetails.getDescription());

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

    @Override
    public List<Payment> getPaymentsByParent(Long parentId) {
        Parent parent = getParentById(parentId);
        return paymentRepository.findByParent(parent);
    }

    @Override
    public List<Payment> getPaymentHistoryByParent(Long parentId) {
        Parent parent = getParentById(parentId);
        return paymentRepository.findByParentOrderByPaymentDateDesc(parent);
    }

    @Override
    public List<Payment> getPendingPaymentsByParent(Long parentId) {
        Parent parent = getParentById(parentId);
        return paymentRepository.findByParentAndStatus(parent, "PENDING");
    }

    @Override
    public List<Payment> getOverduePaymentsByParent(Long parentId) {
        Parent parent = getParentById(parentId);
        return paymentRepository.findOverduePaymentsByParent(parent);
    }

    @Override
    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status);
    }

    @Override
    public List<Payment> getOverduePayments() {
        return paymentRepository.findOverduePayments();
    }

    @Override
    @Transactional
    public boolean markAsPaid(Long paymentId) {
        Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            if (payment.canBePaid()) {
                payment.setStatus("PAID");
                payment.setPaymentDate(LocalDateTime.now());
                paymentRepository.save(payment);
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean markAsCancelled(Long paymentId) {
        Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            payment.setStatus("CANCELLED");
            paymentRepository.save(payment);
            return true;
        }
        return false;
    }

    @Override
    public BigDecimal getTotalPaidByParent(Long parentId) {
        Parent parent = getParentById(parentId);
        BigDecimal total = paymentRepository.sumPaidAmountByParent(parent);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getTotalPendingByParent(Long parentId) {
        Parent parent = getParentById(parentId);
        BigDecimal total = paymentRepository.sumPendingAmountByParent(parent);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public Long getPaidPaymentsCountByParent(Long parentId) {
        Parent parent = getParentById(parentId);
        return paymentRepository.countPaidPaymentsByParent(parent);
    }

    @Override
    public boolean canParentAccessPayment(Long paymentId, Long parentId) {
        return paymentRepository.existsByIdAndParentId(paymentId, parentId);
    }

    @Override
    public List<Payment> getRecentPayments() {
        return paymentRepository.findTop5ByOrderByPaymentDateDesc();
    }

    private Parent getParentById(Long parentId) {
        return parentRepository.findById(parentId)
                .orElseThrow(() -> new IllegalStateException("Parent not found with id: " + parentId));
    }
}