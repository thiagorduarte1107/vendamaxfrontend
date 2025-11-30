package com.vendamax.dto;

import com.vendamax.entity.ItemVenda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO: ItemVenda
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVendaDTO {

    private Long id;
    private Long vendaId;
    private Long produtoId;
    private String produtoNome;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;

    /**
     * Converter Entity para DTO
     */
    public static ItemVendaDTO fromEntity(ItemVenda item) {
        ItemVendaDTO dto = new ItemVendaDTO();
        dto.setId(item.getId());
        dto.setVendaId(item.getVenda().getId());
        dto.setProdutoId(item.getProduto().getId());
        dto.setProdutoNome(item.getProduto().getName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setDiscount(item.getDiscount());
        dto.setTotalPrice(item.getTotalPrice());
        dto.setCreatedAt(item.getCreatedAt());
        return dto;
    }
}
