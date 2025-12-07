package backend.entity.validation;

import backend.repository.I_ParentRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class PaymentValidator {

    private PaymentValidator() {}

    public static void validateCreate(
            Long parentId,
            BigDecimal amount,
            LocalDateTime dueDate,
            I_ParentRepository parentRepo) {

        if (parentId == null) {
            throw new IllegalArgumentException("Parent ID cannot be null");
        }
        if (!parentRepo.existsById(parentId)) {
            throw new IllegalStateException("Parent not found with ID: " + parentId);
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }
    }
}