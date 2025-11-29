package backend.mapper;

import backend.dto.PaymentDTO;
import backend.entity.Payment;
import backend.entity.Parent;

import java.util.List;
import java.util.stream.Collectors;

public final class PaymentMapper {

    private PaymentMapper() {}

    public static PaymentDTO toDTO(Payment payment) {
        return PaymentDTO.fromEntity(payment);
    }

    public static Payment toEntity(PaymentDTO dto, Parent parent) {
        if (dto == null) return null;
        return dto.toEntity(parent);
    }

    public static List<PaymentDTO> toDTOList(List<Payment> payments) {
        return payments.stream()
                .map(PaymentMapper::toDTO)
                .collect(Collectors.toList());
    }
}