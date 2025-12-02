package backend.dto.payment;

import backend.entity.enums.PaymentMethod; // Adăugat ENUM
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreatePaymentRequest {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull
    @JsonProperty("due_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dueDate;

    @Size(max = 500)
    private String description;

    @JsonProperty("payment_method")
    private PaymentMethod paymentMethod; // Folosește ENUM

    @NotNull
    @JsonProperty("parent_id")
    private Long parentId;

    public CreatePaymentRequest() {}

    public CreatePaymentRequest(BigDecimal amount, LocalDateTime dueDate, String description, Long parentId, PaymentMethod paymentMethod) {
        this.amount = amount;
        this.dueDate = dueDate;
        this.description = description;
        this.parentId = parentId;
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
}