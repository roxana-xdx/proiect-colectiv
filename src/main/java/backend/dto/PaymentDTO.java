package backend.dto;

import backend.entity.Payment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentDTO {

    private Long id;
    private BigDecimal amount;

    @JsonProperty("payment_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

    @JsonProperty("due_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dueDate;

    private String status;
    private String description;

    @JsonProperty("parent_id")
    private Long parentId;

    @JsonProperty("parent_email")
    private String parentEmail;

    public PaymentDTO() {}

    public PaymentDTO(Long id, BigDecimal amount, LocalDateTime paymentDate, LocalDateTime dueDate,
                      String status, String description, Long parentId, String parentEmail) {
        this.id = id;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.dueDate = dueDate;
        this.status = status;
        this.description = description;
        this.parentId = parentId;
        this.parentEmail = parentEmail;
    }

    public static PaymentDTO fromEntity(Payment payment) {
        if (payment == null) return null;

        return new PaymentDTO(
                payment.getId(),
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getDueDate(),
                payment.getStatus(),
                payment.getDescription(),
                payment.getParent() != null ? payment.getParent().getId() : null,
                payment.getParent() != null && payment.getParent().getUser() != null ?
                        payment.getParent().getUser().getEmail() : null
        );
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public String getParentEmail() { return parentEmail; }
    public void setParentEmail(String parentEmail) { this.parentEmail = parentEmail; }
}