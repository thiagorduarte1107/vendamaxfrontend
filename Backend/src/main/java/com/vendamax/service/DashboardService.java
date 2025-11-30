package com.vendamax.service;

import com.vendamax.dto.response.DashboardResponse;
import com.vendamax.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service: Dashboard
 */
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final VendaRepository vendaRepository;
    private final ContaReceberRepository contaReceberRepository;
    private final ContaPagarRepository contaPagarRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    /**
     * Obter métricas do dashboard
     */
    public DashboardResponse getMetricas() {
        DashboardResponse response = new DashboardResponse();

        // Vendas do dia
        response.setVendasDia(vendaRepository.calcularTotalVendasDia());
        response.setQuantidadeVendasDia(vendaRepository.countVendasDia());

        // Contas a receber
        response.setContasReceber(contaReceberRepository.calcularTotalAReceber());

        // Contas a pagar
        response.setContasPagar(contaPagarRepository.calcularTotalAPagar());

        // Produtos com estoque baixo
        response.setProdutosEstoqueBaixo((long) produtoRepository.findProdutosEstoqueBaixo().size());

        // Clientes ativos
        response.setClientesAtivos(clienteRepository.countActiveClientes());

        // Produtos ativos
        response.setProdutosAtivos(produtoRepository.countActiveProdutos());

        return response;
    }
}
