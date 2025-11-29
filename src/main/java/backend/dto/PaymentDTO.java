package backend.dto;

import backend.entity.Payment;
import backend.entity.Parent;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentDTO {

    private Long id;

    @NotNull(message = "Parent ID is required")
    private Long parentId;

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

    public PaymentDTO(Long id, Long parentId, BigDecimal amount, LocalDate dueDate, String message) {
        this.id = id;
        this.parentId = parentId;
        this.amount = amount;
        this.dueDate = dueDate;
        this.message = message;
    }

    public static PaymentDTO fromEntity(Payment payment) {
        if (payment == null) return null;
        return new PaymentDTO(
                payment.getId(),
                payment.getParent() != null ? payment.getParent().getId() : null,
                payment.getAmount(),
                payment.getDueDate(),
                payment.getMessage()
        );
    }

    public Payment toEntity(Parent parent) {
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

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}