package backend.controller;

import backend.dto.payment.CreatePaymentRequest;
import backend.dto.PaymentDTO;
import backend.entity.Payment;
import backend.entity.Parent;
import backend.mapper.PaymentMapper;
import backend.service.I_PaymentService;
import backend.service.I_ParentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final I_PaymentService paymentService;
    private final I_ParentService parentService;

    @Autowired
    public PaymentController(I_PaymentService paymentService,
                             I_ParentService parentService) {
        this.paymentService = paymentService;
        this.parentService = parentService;
    }


    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody @Valid CreatePaymentRequest request) {
        try {
            Parent parent = parentService.getParentById(request.getParentId())
                    .orElseThrow(() -> new IllegalStateException("Parent not found"));

            Payment payment = new Payment();
            payment.setAmount(request.getAmount());
            payment.setDueDate(request.getDueDate());
            payment.setDescription(request.getDescription());
            payment.setParent(parent);

            Payment created = paymentService.createPayment(payment);
            return ResponseEntity.status(HttpStatus.CREATED).body(PaymentMapper.toDTO(created));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> dtos = PaymentMapper.toDTOList(paymentService.getAllPayments());
        return ResponseEntity.ok(dtos);
    }

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
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        try {
            paymentService.deletePayment(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<PaymentDTO>> getMyPayments(@PathVariable Long parentId) {
        try {
            List<PaymentDTO> dtos = PaymentMapper.toDTOList(paymentService.getPaymentsByParent(parentId));
            return ResponseEntity.ok(dtos);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/parent/{parentId}/history")
    public ResponseEntity<List<PaymentDTO>> getMyPaymentHistory(@PathVariable Long parentId) {
        try {
            List<PaymentDTO> dtos = PaymentMapper.toDTOList(paymentService.getPaymentHistoryByParent(parentId));
            return ResponseEntity.ok(dtos);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/parent/{parentId}/pending")
    public ResponseEntity<List<PaymentDTO>> getMyPendingPayments(@PathVariable Long parentId) {
        try {
            List<PaymentDTO> dtos = PaymentMapper.toDTOList(paymentService.getPendingPaymentsByParent(parentId));
            return ResponseEntity.ok(dtos);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<Void> markPaymentAsPaid(@PathVariable Long id) {
        try {
            boolean success = paymentService.markAsPaid(id);
            return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/parent/{parentId}/total-paid")
    public ResponseEntity<BigDecimal> getTotalPaidByParent(@PathVariable Long parentId) {
        try {
            BigDecimal total = paymentService.getTotalPaidByParent(parentId);
            return ResponseEntity.ok(total);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/parent/{parentId}/total-pending")
    public ResponseEntity<BigDecimal> getTotalPendingByParent(@PathVariable Long parentId) {
        try {
            BigDecimal total = paymentService.getTotalPendingByParent(parentId);
            return ResponseEntity.ok(total);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<PaymentDTO>> getOverduePayments() {
        List<PaymentDTO> dtos = PaymentMapper.toDTOList(paymentService.getOverduePayments());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<PaymentDTO>> getRecentPayments() {
        List<PaymentDTO> dtos = PaymentMapper.toDTOList(paymentService.getRecentPayments());
        return ResponseEntity.ok(dtos);
    }
}