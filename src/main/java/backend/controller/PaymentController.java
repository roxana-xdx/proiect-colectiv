package backend.controller;

import backend.dto.payment.CreatePaymentRequest;
import backend.dto.PaymentDTO;
import backend.entity.Payment;
import backend.entity.Parent;
import backend.mapper.PaymentMapper;
import backend.service.I_PaymentService;
// Importul pentru I_ParentService nu mai este necesar aici, deoarece PaymentService se ocupă de Parent lookup.
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final I_PaymentService paymentService;

    @Autowired
    public PaymentController(I_PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // --- CRUD ---

    /**
     * Create a new payment record (typically initiated by Admin/System, linked to a Parent)
     */
    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody @Valid CreatePaymentRequest request) {
        try {
            Parent parentMock = new Parent();
            parentMock.setId(request.getParentId());

            Payment payment = new Payment();
            payment.setAmount(request.getAmount());
            payment.setDueDate(request.getDueDate());
            payment.setDescription(request.getDescription());
            payment.setParent(parentMock);
            payment.setPaymentMethod(request.getPaymentMethod());

            Payment created = paymentService.createPayment(payment);
            return ResponseEntity.status(HttpStatus.CREATED).body(PaymentMapper.toDTO(created));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all payments (Admin view)
     */
    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> dtos = PaymentMapper.toDTOList(paymentService.getAllPayments());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Update payment details (e.g., amount, due date, description)
     */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Long id,
                                                    @RequestBody @Valid CreatePaymentRequest request) {
        try {
            Payment paymentDetails = new Payment();
            paymentDetails.setAmount(request.getAmount());
            paymentDetails.setDueDate(request.getDueDate());
            paymentDetails.setDescription(request.getDescription());

            Payment updated = paymentService.updatePayment(id, paymentDetails);
            return ResponseEntity.ok(PaymentMapper.toDTO(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found (Payment not found)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete a payment record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        try {
            paymentService.deletePayment(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // --- PAYMENT STATUS & LOOKUP ---

    /**
     * Get all payments for a specific parent
     */
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<PaymentDTO>> getMyPayments(@PathVariable Long parentId) {
        try {
            List<PaymentDTO> dtos = PaymentMapper.toDTOList(paymentService.getPaymentsByParent(parentId));
            return ResponseEntity.ok(dtos);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    /**
     * Get payment history for a specific parent (ordered by payment date desc)
     */
    @GetMapping("/parent/{parentId}/history")
    public ResponseEntity<List<PaymentDTO>> getMyPaymentHistory(@PathVariable Long parentId) {
        try {
            List<PaymentDTO> dtos = PaymentMapper.toDTOList(paymentService.getPaymentHistoryByParent(parentId));
            return ResponseEntity.ok(dtos);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    /**
     * Get pending payments for a specific parent
     */
    @GetMapping("/parent/{parentId}/pending")
    public ResponseEntity<List<PaymentDTO>> getMyPendingPayments(@PathVariable Long parentId) {
        try {
            List<PaymentDTO> dtos = PaymentMapper.toDTOList(paymentService.getPendingPaymentsByParent(parentId));
            return ResponseEntity.ok(dtos);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    /**
     * Mark a payment as PAID
     */
    @PostMapping("/{id}/pay")
    public ResponseEntity<Void> markPaymentAsPaid(@PathVariable Long id) {
        try {
            boolean success = paymentService.markAsPaid(id);
            return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found (Payment not found)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // --- AGGREGATES & REPORTS ---

    /**
     * Get total amount paid by a specific parent
     */
    @GetMapping("/parent/{parentId}/total-paid")
    public ResponseEntity<BigDecimal> getTotalPaidByParent(@PathVariable Long parentId) {
        try {
            BigDecimal total = paymentService.getTotalPaidByParent(parentId);
            return ResponseEntity.ok(total);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found (Parent not found)
        }
    }

    /**
     * Get total amount pending by a specific parent
     */
    @GetMapping("/parent/{parentId}/total-pending")
    public ResponseEntity<BigDecimal> getTotalPendingByParent(@PathVariable Long parentId) {
        try {
            BigDecimal total = paymentService.getTotalPendingByParent(parentId);
            return ResponseEntity.ok(total);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found (Parent not found)
        }
    }

    /**
     * Get all overdue payments (Admin view)
     */
    @GetMapping("/overdue")
    public ResponseEntity<List<PaymentDTO>> getOverduePayments() {
        List<PaymentDTO> dtos = PaymentMapper.toDTOList(paymentService.getOverduePayments());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get recent payments (Admin/Dashboard view)
     */
    @GetMapping("/recent")
    public ResponseEntity<List<PaymentDTO>> getRecentPayments() {
        List<PaymentDTO> dtos = PaymentMapper.toDTOList(paymentService.getRecentPayments());
        return ResponseEntity.ok(dtos);
    }
}