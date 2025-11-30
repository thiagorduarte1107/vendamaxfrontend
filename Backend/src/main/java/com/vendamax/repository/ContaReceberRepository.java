package com.vendamax.repository;

import com.vendamax.entity.ContaReceber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository: ContaReceber
 */
@Repository
public interface ContaReceberRepository extends JpaRepository<ContaReceber, Long> {

    /**
     * Buscar contas por cliente
     */
    List<ContaReceber> findByClienteIdOrderByDueDateDesc(Long clienteId);

    /**
     * Buscar contas por status
     */
    List<ContaReceber> findByStatusOrderByDueDateDesc(ContaReceber.StatusConta status);

    /**
     * Buscar contas vencidas
     */
    @Query("SELECT c FROM ContaReceber c WHERE c.dueDate < CURRENT_DATE AND c.status = 'PENDING' ORDER BY c.dueDate")
    List<ContaReceber> findContasVencidas();

    /**
     * Buscar contas a vencer
     */
    @Query("SELECT c FROM ContaReceber c WHERE c.dueDate BETWEEN CURRENT_DATE AND :dataLimite AND c.status = 'PENDING' ORDER BY c.dueDate")
    List<ContaReceber> findContasAVencer(LocalDate dataLimite);

    /**
     * Calcular total a receber
     */
    @Query("SELECT COALESCE(SUM(c.amount - c.paidAmount), 0) FROM ContaReceber c WHERE c.status = 'PENDING'")
    BigDecimal calcularTotalAReceber();

    /**
     * Calcular total vencido
     */
    @Query("SELECT COALESCE(SUM(c.amount - c.paidAmount), 0) FROM ContaReceber c WHERE c.dueDate < CURRENT_DATE AND c.status = 'PENDING'")
    BigDecimal calcularTotalVencido();

    /**
     * Contar contas por status
     */
    long countByStatus(ContaReceber.StatusConta status);
}
