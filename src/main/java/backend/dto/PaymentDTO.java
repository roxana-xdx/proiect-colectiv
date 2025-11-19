package backend.dto;

import backend.entity.Payment;
import backend.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object for Payment entity.
 * Only exposes parent's email instead of full User entity.
 */
public class PaymentDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String parentEmail;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDate dueDate;

    @Size(max = 255)
    private String message;

    public PaymentDTO() {}

    public PaymentDTO(Long id, String parentEmail, BigDecimal amount, LocalDate dueDate, String message) {
        this.id = id;
        this.parentEmail = parentEmail;
        this.amount = amount;
        this.dueDate = dueDate;
        this.message = message;
    }

    /**
     * Create DTO from entity.
     *
     * @param payment entity
     * @return dto
     */
    public static PaymentDTO fromEntity(Payment payment) {
        if (payment == null) return null;
        return new PaymentDTO(
                payment.getId(),
                payment.getParent() != null ? payment.getParent().getEmail() : null,
                payment.getAmount(),
                payment.getDueDate(),
                payment.getMessage()
        );
    }

    /**
     * Convert DTO to entity.
     *
     * @param parent User entity representing parent
     * @return Payment entity
     */
    public Payment toEntity(User parent) {
        Payment payment = new Payment();
        payment.setId(this.id);
        payment.setParent(parent);
        payment.setAmount(this.amount);
        payment.setDueDate(this.dueDate);
        payment.setMessage(this.message);
        return payment;
    }

    // --- Getters / Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getParentEmail() { return parentEmail; }
    public void setParentEmail(String parentEmail) { this.parentEmail = parentEmail; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
