package backend.entity;

import backend.entity.enums.PaymentMethod;
import backend.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "payments")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    // FOLOSIM ENUM CU MAPPING CA STRING ÎN DB
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private PaymentStatus status;

    @Column(length = 500)
    private String description;

    // FOLOSIM ENUM CU MAPPING CA STRING ÎN DB
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 50)
    private PaymentMethod paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    public Payment() {
        this.paymentDate = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }

    public Payment(BigDecimal amount, LocalDateTime dueDate, String description, Parent parent) {
        this();
        this.amount = amount;
        this.dueDate = dueDate;
        this.description = description;
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public boolean canBePaid() {
        return status == PaymentStatus.PENDING || status == PaymentStatus.OVERDUE;
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
        return "Payment{" +
                "amount=" + amount +
                ", id=" + id +
                ", paymentDate=" + paymentDate +
                ", dueDate=" + dueDate +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
