package com.vendamax.repository;

import com.vendamax.entity.ContaPagar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository: ContaPagar
 */
@Repository
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {

    /**
     * Buscar contas por fornecedor
     */
    List<ContaPagar> findBySupplierContainingIgnoreCaseOrderByDueDateDesc(String supplier);

    /**
     * Buscar contas por status
     */
    List<ContaPagar> findByStatusOrderByDueDateDesc(ContaPagar.StatusConta status);

    /**
     * Buscar contas vencidas
     */
    @Query("SELECT c FROM ContaPagar c WHERE c.dueDate < CURRENT_DATE AND c.status = 'PENDING' ORDER BY c.dueDate")
    List<ContaPagar> findContasVencidas();

    /**
     * Buscar contas a vencer
     */
    @Query("SELECT c FROM ContaPagar c WHERE c.dueDate BETWEEN CURRENT_DATE AND :dataLimite AND c.status = 'PENDING' ORDER BY c.dueDate")
    List<ContaPagar> findContasAVencer(LocalDate dataLimite);

    /**
     * Calcular total a pagar
     */
    @Query("SELECT COALESCE(SUM(c.amount - c.paidAmount), 0) FROM ContaPagar c WHERE c.status = 'PENDING'")
    BigDecimal calcularTotalAPagar();

    /**
     * Calcular total vencido
     */
    @Query("SELECT COALESCE(SUM(c.amount - c.paidAmount), 0) FROM ContaPagar c WHERE c.dueDate < CURRENT_DATE AND c.status = 'PENDING'")
    BigDecimal calcularTotalVencido();

    /**
     * Contar contas por status
     */
    long countByStatus(ContaPagar.StatusConta status);
}
