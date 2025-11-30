package com.vendamax.repository;

import com.vendamax.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository: Venda
 */
@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    /**
     * Buscar vendas por cliente
     */
    List<Venda> findByClienteIdOrderByCreatedAtDesc(Long clienteId);

    /**
     * Buscar vendas por usuário
     */
    List<Venda> findByUsuarioIdOrderByCreatedAtDesc(Long usuarioId);

    /**
     * Buscar vendas por caixa
     */
    List<Venda> findByCaixaIdOrderByCreatedAtDesc(Long caixaId);

    /**
     * Buscar vendas por status
     */
    List<Venda> findByStatusOrderByCreatedAtDesc(Venda.VendaStatus status);

    /**
     * Buscar vendas por período
     */
    @Query("SELECT v FROM Venda v WHERE v.createdAt BETWEEN :inicio AND :fim ORDER BY v.createdAt DESC")
    List<Venda> findByPeriodo(LocalDateTime inicio, LocalDateTime fim);

    /**
     * Buscar vendas do dia
     */
    @Query("SELECT v FROM Venda v WHERE CAST(v.createdAt AS date) = CURRENT_DATE ORDER BY v.createdAt DESC")
    List<Venda> findVendasDoDia();

    /**
     * Calcular total de vendas por período
     */
    @Query("SELECT COALESCE(SUM(v.finalAmount), 0) FROM Venda v WHERE v.createdAt BETWEEN :inicio AND :fim AND v.status = 'COMPLETED'")
    BigDecimal calcularTotalVendasPeriodo(LocalDateTime inicio, LocalDateTime fim);

    /**
     * Calcular total de vendas do dia
     */
    @Query("SELECT COALESCE(SUM(v.finalAmount), 0) FROM Venda v WHERE CAST(v.createdAt AS date) = CURRENT_DATE AND v.status = 'COMPLETED'")
    BigDecimal calcularTotalVendasDia();

    /**
     * Contar vendas por status
     */
    long countByStatus(Venda.VendaStatus status);

    /**
     * Contar vendas do dia
     */
    @Query("SELECT COUNT(v) FROM Venda v WHERE CAST(v.createdAt AS date) = CURRENT_DATE")
    long countVendasDia();
}
