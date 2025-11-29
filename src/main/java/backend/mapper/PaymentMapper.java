package backend.mapper;

import backend.dto.PaymentDTO;
import backend.entity.Payment;
import backend.entity.User;

import java.util.List;
import java.util.stream.Collectors;


public final class PaymentMapper {

    private PaymentMapper() {}

    // Entity -> DTO
    public static PaymentDTO toDTO(Payment payment) {
        return PaymentDTO.fromEntity(payment);
    }

    // DTO -> Entity (requires parent User)
    public static Payment toEntity(PaymentDTO dto, User parent) {
        if (dto == null) return null;
        return dto.toEntity(parent);
    }

    // Convert list of entities to list of DTOs
    public static List<PaymentDTO> toDTOList(List<Payment> payments) {
        return payments.stream()
                .map(PaymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Convert list of DTOs to list of entities (requires parent for each)
    public static List<Payment> toEntityList(List<PaymentDTO> dtos, User parent) {
        return dtos.stream()
                .map(dto -> dto.toEntity(parent))
                .collect(Collectors.toList());
    }
}
