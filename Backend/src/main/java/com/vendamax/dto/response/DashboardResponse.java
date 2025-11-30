package com.vendamax.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Response: Dashboard
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private BigDecimal vendasDia;
    private Long quantidadeVendasDia;
    private BigDecimal contasReceber;
    private BigDecimal contasPagar;
    private Long produtosEstoqueBaixo;
    private Long clientesAtivos;
    private Long produtosAtivos;
}
