package backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_email", referencedColumnName = "email", nullable = false)
    private User parent;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    @Digits(integer = 13, fraction = 2, message = "Amount must have max 13 integer and 2 fraction digits")
    @Column(precision = 15, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull(message = "Due date cannot be null")
    @FutureOrPresent(message = "Due date must be today or in the future")
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(length = 255)
    private String message;

    public Payment() {}

    public Payment(User parent, BigDecimal amount, LocalDate dueDate, String message) {
        this.parent = parent;
        this.amount = amount;
        this.dueDate = dueDate;
        this.message = message;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getParent() { return parent; }
    public void setParent(User parent) { this.parent = parent; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}