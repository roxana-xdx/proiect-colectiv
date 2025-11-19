package backend.controller;

import backend.dto.PaymentDTO;
import backend.entity.User;
import backend.mapper.PaymentMapper;
import backend.service.I_PaymentService;
import backend.repository.I_UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    // --- CREATE Payment ---
    @PostMapping
    public PaymentDTO createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        User parent = userRepository.findById(paymentDTO.getParentEmail())
                .orElseThrow(() -> new RuntimeException("Parent not found with email: " + paymentDTO.getParentEmail()));
        return PaymentMapper.toDTO(paymentService.createPayment(PaymentMapper.toEntity(paymentDTO, parent), parent));
    }

    // --- READ ALL Payments ---
    @GetMapping
    public List<PaymentDTO> getAllPayments() {
        return PaymentMapper.toDTOList(paymentService.getAllPayments());
    }

    // --- READ Payment BY ID ---
    @GetMapping("/{id}")
    public PaymentDTO getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id)
                .map(PaymentMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }

    // --- UPDATE Payment ---
    @PutMapping("/{id}")
    public PaymentDTO updatePayment(@PathVariable Long id, @Valid @RequestBody PaymentDTO paymentDTO) {
        User parent = null;
        if (paymentDTO.getParentEmail() != null) {
            parent = userRepository.findById(paymentDTO.getParentEmail())
                    .orElseThrow(() -> new RuntimeException("Parent not found with email: " + paymentDTO.getParentEmail()));
        }

        return PaymentMapper.toDTO(
                paymentService.updatePayment(id, PaymentMapper.toEntity(paymentDTO, parent), parent)
        );
    }

    // --- DELETE Payment ---
    @DeleteMapping("/{id}")
    public String deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return "Payment deleted with id: " + id;
    }

    // --- Get all payments for a specific parent ---
    @GetMapping("/parent/{email}")
    public List<PaymentDTO> getPaymentsByParent(@PathVariable String email) {
        return PaymentMapper.toDTOList(paymentService.getPaymentsByParent(email));
    }
}
