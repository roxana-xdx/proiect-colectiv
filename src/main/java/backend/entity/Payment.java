package backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(length = 50, nullable = false)
    private String status; // PENDING, PAID, OVERDUE, CANCELLED

    @Column(length = 500)
    private String description;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod; // CASH, CARD, BANK_TRANSFER

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    public Payment() {
        this.paymentDate = LocalDateTime.now();
        this.status = "PENDING";
    }

    public Payment(BigDecimal amount, LocalDateTime dueDate, String description, Parent parent) {
        this();
        this.amount = amount;
        this.dueDate = dueDate;
        this.description = description;
        this.parent = parent;
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

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public Parent getParent() { return parent; }
    public void setParent(Parent parent) { this.parent = parent; }

    public boolean isOverdue() {
        return "PENDING".equals(status) && dueDate.isBefore(LocalDateTime.now());
    }

    public boolean canBePaid() {
        return "PENDING".equals(status) || "OVERDUE".equals(status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return id != null && id.equals(payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Payment{" + "id=" + id + ", amount=" + amount + ", status='" + status + '\'' + '}';
    }
}