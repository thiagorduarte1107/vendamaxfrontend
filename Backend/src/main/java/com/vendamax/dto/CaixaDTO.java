package com.vendamax.dto;

import com.vendamax.entity.Caixa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO: Caixa
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaixaDTO {

    private Long id;
    private Long usuarioId;
    private String usuarioNome;
    private BigDecimal openingBalance;
    private BigDecimal closingBalance;
    private String status;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Converter Entity para DTO
     */
    public static CaixaDTO fromEntity(Caixa caixa) {
        CaixaDTO dto = new CaixaDTO();
        dto.setId(caixa.getId());
        dto.setUsuarioId(caixa.getUsuario().getId());
        dto.setUsuarioNome(caixa.getUsuario().getName());
        dto.setOpeningBalance(caixa.getOpeningBalance());
        dto.setClosingBalance(caixa.getClosingBalance());
        dto.setStatus(caixa.getStatus().name());
        dto.setOpenedAt(caixa.getOpenedAt());
        dto.setClosedAt(caixa.getClosedAt());
        dto.setNotes(caixa.getNotes());
        dto.setCreatedAt(caixa.getCreatedAt());
        dto.setUpdatedAt(caixa.getUpdatedAt());
        return dto;
    }
}
