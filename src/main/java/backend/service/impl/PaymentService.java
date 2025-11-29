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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaymentService implements I_PaymentService {

    @Autowired
    private I_PaymentRepository paymentRepository;

    @Autowired
    private I_ParentRepository parentRepository;

    @Override
    @Transactional
    public Payment createPayment(Payment payment, Parent parent) {
        validatePaymentData(payment);
        validateParentExists(parent);
        payment.setParent(parent);
        return paymentRepository.save(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Payment> getPaymentById(Long id) {
        validatePaymentId(id);
        return paymentRepository.findById(id);
    }

    @Override
    @Transactional
    public Payment updatePayment(Long id, Payment paymentDetails, Parent parent) {
        validatePaymentId(id);
        validatePaymentUpdateData(paymentDetails);

        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Payment not found with id: " + id));

        updatePaymentFields(existingPayment, paymentDetails, parent);
        return paymentRepository.save(existingPayment);
    }

    @Override
    @Transactional
    public void deletePayment(Long id) {
        validatePaymentId(id);
        if (!paymentRepository.existsById(id)) {
            throw new IllegalStateException("Payment not found with id: " + id);
        }
        paymentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByParent(String parentEmail) {
        validateEmail(parentEmail);
        Parent parent = parentRepository.findByEmail(parentEmail.trim())
                .orElseThrow(() -> new IllegalStateException("Parent not found with email: " + parentEmail));
        return paymentRepository.findByParent(parent);
    }

    private void validatePaymentData(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null");
        }
        if (payment.getAmount() == null || payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (payment.getDueDate() == null) {
            throw new IllegalArgumentException("Due date is required");
        }
        if (payment.getDueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date must be today or in the future");
        }
    }

    private void validateParentExists(Parent parent) {
        if (parent == null) {
            throw new IllegalArgumentException("Parent cannot be null");
        }
        if (!parentRepository.existsById(parent.getId())) {
            throw new IllegalStateException("Parent does not exist in database");
        }
    }

    private void validatePaymentId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Payment ID cannot be null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("Payment ID must be positive");
        }
    }

    private void validatePaymentUpdateData(Payment paymentDetails) {
        if (paymentDetails == null) {
            throw new IllegalArgumentException("Payment details cannot be null");
        }
        if (paymentDetails.getAmount() != null && paymentDetails.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (paymentDetails.getDueDate() != null && paymentDetails.getDueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date must be today or in the future");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private void updatePaymentFields(Payment existingPayment, Payment paymentDetails, Parent parent) {
        if (paymentDetails.getAmount() != null) {
            existingPayment.setAmount(paymentDetails.getAmount());
        }
        if (paymentDetails.getDueDate() != null) {
            existingPayment.setDueDate(paymentDetails.getDueDate());
        }
        if (paymentDetails.getMessage() != null) {
            existingPayment.setMessage(paymentDetails.getMessage());
        }
        if (parent != null) {
            existingPayment.setParent(parent);
        }
    }
}