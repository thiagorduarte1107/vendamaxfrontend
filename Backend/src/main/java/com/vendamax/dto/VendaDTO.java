package com.vendamax.dto;

import com.vendamax.entity.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO: Venda
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {

    private Long id;
    private Long clienteId;
    private String clienteNome;
    private Long usuarioId;
    private String usuarioNome;
    private Long caixaId;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private BigDecimal finalAmount;
    private String status;
    private String notes;
    private List<ItemVendaDTO> itens;
    private List<PagamentoDTO> pagamentos;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Converter Entity para DTO
     */
    public static VendaDTO fromEntity(Venda venda) {
        VendaDTO dto = new VendaDTO();
        dto.setId(venda.getId());
        
        if (venda.getCliente() != null) {
            dto.setClienteId(venda.getCliente().getId());
            dto.setClienteNome(venda.getCliente().getName());
        }
        
        dto.setUsuarioId(venda.getUsuario().getId());
        dto.setUsuarioNome(venda.getUsuario().getName());
        
        if (venda.getCaixa() != null) {
            dto.setCaixaId(venda.getCaixa().getId());
        }
        
        dto.setTotalAmount(venda.getTotalAmount());
        dto.setDiscount(venda.getDiscount());
        dto.setFinalAmount(venda.getFinalAmount());
        dto.setStatus(venda.getStatus().name());
        dto.setNotes(venda.getNotes());
        
        dto.setItens(venda.getItens().stream()
                .map(ItemVendaDTO::fromEntity)
                .collect(Collectors.toList()));
        
        dto.setPagamentos(venda.getPagamentos().stream()
                .map(PagamentoDTO::fromEntity)
                .collect(Collectors.toList()));
        
        dto.setCreatedAt(venda.getCreatedAt());
        dto.setUpdatedAt(venda.getUpdatedAt());
        return dto;
    }
}
