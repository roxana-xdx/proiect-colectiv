package backend.dto;

import backend.entity.Payment;
import backend.entity.User;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentDTO {

    private Long id;

    @NotBlank(message = "Parent email is required")
    @Email(message = "Parent email must be valid")
    @Size(max = 255, message = "Parent email must not exceed 255 characters")
    private String parentEmail;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @Digits(integer = 13, fraction = 2, message = "Amount must have max 13 integer and 2 fraction digits")
    private BigDecimal amount;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate dueDate;

    @Size(max = 255, message = "Message must not exceed 255 characters")
    private String message;

    public PaymentDTO() {}

    public PaymentDTO(Long id, String parentEmail, BigDecimal amount, LocalDate dueDate, String message) {
        this.id = id;
        this.parentEmail = parentEmail;
        this.amount = amount;
        this.dueDate = dueDate;
        this.message = message;
    }

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

    public Payment toEntity(User parent) {
        Payment payment = new Payment();
        payment.setId(this.id);
        payment.setParent(parent);
        payment.setAmount(this.amount);
        payment.setDueDate(this.dueDate);
        payment.setMessage(this.message);
        return payment;
    }

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