package com.vendamax.dto;

import com.vendamax.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO: Produto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;
    private Long categoriaId;
    private String categoriaNome;
    private String name;
    private String description;
    private String sku;
    private String barcode;
    private BigDecimal price;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private Integer stock;
    private Integer minStock;
    private Integer maxStock;
    private String unit;
    private String imageUrl;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Converter Entity para DTO
     */
    public static ProdutoDTO fromEntity(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(produto.getId());
        dto.setCategoriaId(produto.getCategoria().getId());
        dto.setCategoriaNome(produto.getCategoria().getName());
        dto.setName(produto.getName());
        dto.setDescription(produto.getDescription());
        dto.setSku(produto.getSku());
        dto.setBarcode(produto.getBarcode());
        dto.setPrice(produto.getPrice());
        dto.setCostPrice(produto.getCostPrice());
        dto.setSalePrice(produto.getSalePrice());
        dto.setStock(produto.getStock());
        dto.setMinStock(produto.getMinStock());
        dto.setMaxStock(produto.getMaxStock());
        dto.setUnit(produto.getUnit());
        dto.setImageUrl(produto.getImageUrl());
        dto.setActive(produto.getActive());
        dto.setCreatedAt(produto.getCreatedAt());
        dto.setUpdatedAt(produto.getUpdatedAt());
        return dto;
    }
}
