package com.vendamax.repository;

import com.vendamax.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository: Pagamento
 */
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    /**
     * Buscar pagamentos por venda
     */
    List<Pagamento> findByVendaId(Long vendaId);

    /**
     * Buscar pagamentos por método
     */
    List<Pagamento> findByPaymentMethod(Pagamento.MetodoPagamento paymentMethod);

    /**
     * Buscar pagamentos por período
     */
    @Query("SELECT p FROM Pagamento p WHERE p.paidAt BETWEEN :inicio AND :fim ORDER BY p.paidAt DESC")
    List<Pagamento> findByPeriodo(LocalDateTime inicio, LocalDateTime fim);

    /**
     * Calcular total por método de pagamento
     */
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Pagamento p WHERE p.paymentMethod = :metodo AND p.paidAt BETWEEN :inicio AND :fim")
    BigDecimal calcularTotalPorMetodo(Pagamento.MetodoPagamento metodo, LocalDateTime inicio, LocalDateTime fim);

    /**
     * Contar pagamentos por método
     */
    long countByPaymentMethod(Pagamento.MetodoPagamento paymentMethod);
}
