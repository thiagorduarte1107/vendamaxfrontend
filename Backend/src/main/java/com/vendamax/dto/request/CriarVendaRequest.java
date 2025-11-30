package com.vendamax.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Request: Criar Venda
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarVendaRequest {

    private Long clienteId;

    @NotNull(message = "Caixa é obrigatório")
    private Long caixaId;

    @NotEmpty(message = "Itens são obrigatórios")
    private List<ItemVendaRequest> itens;

    @NotEmpty(message = "Pagamentos são obrigatórios")
    private List<PagamentoRequest> pagamentos;

    private BigDecimal discount;
    private String notes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemVendaRequest {
        @NotNull(message = "Produto é obrigatório")
        private Long produtoId;

        @NotNull(message = "Quantidade é obrigatória")
        private Integer quantity;

        @NotNull(message = "Preço unitário é obrigatório")
        private BigDecimal unitPrice;

        private BigDecimal discount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PagamentoRequest {
        @NotNull(message = "Método de pagamento é obrigatório")
        private String paymentMethod;

        @NotNull(message = "Valor é obrigatório")
        private BigDecimal amount;
    }
}
