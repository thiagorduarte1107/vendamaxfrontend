package com.vendamax.dto;

import com.vendamax.entity.Pagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO: Pagamento
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoDTO {

    private Long id;
    private Long vendaId;
    private String paymentMethod;
    private BigDecimal amount;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;

    /**
     * Converter Entity para DTO
     */
    public static PagamentoDTO fromEntity(Pagamento pagamento) {
        PagamentoDTO dto = new PagamentoDTO();
        dto.setId(pagamento.getId());
        dto.setVendaId(pagamento.getVenda().getId());
        dto.setPaymentMethod(pagamento.getPaymentMethod().name());
        dto.setAmount(pagamento.getAmount());
        dto.setPaidAt(pagamento.getPaidAt());
        dto.setCreatedAt(pagamento.getCreatedAt());
        return dto;
    }
}
