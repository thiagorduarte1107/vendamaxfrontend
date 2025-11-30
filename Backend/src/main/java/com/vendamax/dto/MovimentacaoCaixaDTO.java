package com.vendamax.dto;

import com.vendamax.entity.MovimentacaoCaixa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoCaixaDTO {
    private Long id;
    private Long caixaId;
    private String type;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;

    public static MovimentacaoCaixaDTO fromEntity(MovimentacaoCaixa entity) {
        return new MovimentacaoCaixaDTO(
                entity.getId(),
                entity.getCaixa().getId(),
                entity.getType().name(),
                entity.getAmount(),
                entity.getDescription(),
                entity.getCreatedAt()
        );
    }
}
