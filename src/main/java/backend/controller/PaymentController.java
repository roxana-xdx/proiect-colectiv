package backend.controller;

import backend.dto.PaymentDTO;
import backend.entity.User;
import backend.mapper.PaymentMapper;
import backend.service.I_PaymentService;
import backend.repository.I_UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private I_PaymentService paymentService;

    @Autowired
    private I_UserRepository userRepository;

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        User parent = userRepository.findByEmail(paymentDTO.getParentEmail())
                .orElseThrow(() -> new RuntimeException("Parent not found with email: " + paymentDTO.getParentEmail()));
        PaymentDTO createdPayment = PaymentMapper.toDTO(
                paymentService.createPayment(PaymentMapper.toEntity(paymentDTO, parent), parent)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> payments = PaymentMapper.toDTOList(paymentService.getAllPayments());
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        PaymentDTO payment = paymentService.getPaymentById(id)
                .map(PaymentMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Long id, @Valid @RequestBody PaymentDTO paymentDTO) {
        User parent = null;
        if (paymentDTO.getParentEmail() != null) {
            parent = userRepository.findByEmail(paymentDTO.getParentEmail())
                    .orElseThrow(() -> new RuntimeException("Parent not found with email: " + paymentDTO.getParentEmail()));
        }

        PaymentDTO updatedPayment = PaymentMapper.toDTO(
                paymentService.updatePayment(id, PaymentMapper.toEntity(paymentDTO, parent), parent)
        );
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok("Payment deleted with id: " + id);
    }

    @GetMapping("/parent/{email}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByParent(@PathVariable String email) {
        List<PaymentDTO> payments = PaymentMapper.toDTOList(paymentService.getPaymentsByParent(email));
        return ResponseEntity.ok(payments);
    }
}