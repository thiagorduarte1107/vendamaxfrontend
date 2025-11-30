package com.vendamax.repository;

import com.vendamax.entity.MovimentacaoCaixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository: MovimentacaoCaixa
 */
@Repository
public interface MovimentacaoCaixaRepository extends JpaRepository<MovimentacaoCaixa, Long> {

    /**
     * Buscar movimentações por caixa
     */
    List<MovimentacaoCaixa> findByCaixaIdOrderByCreatedAtDesc(Long caixaId);

    /**
     * Buscar movimentações por tipo
     */
    List<MovimentacaoCaixa> findByCaixaIdAndType(Long caixaId, MovimentacaoCaixa.TipoMovimentacao type);

    /**
     * Calcular total de entradas do caixa
     */
    @Query("SELECT COALESCE(SUM(m.amount), 0) FROM MovimentacaoCaixa m WHERE m.caixa.id = :caixaId AND m.type IN ('ENTRADA', 'REFORCO')")
    BigDecimal calcularTotalEntradas(Long caixaId);

    /**
     * Calcular total de saídas do caixa
     */
    @Query("SELECT COALESCE(SUM(m.amount), 0) FROM MovimentacaoCaixa m WHERE m.caixa.id = :caixaId AND m.type IN ('SAIDA', 'SANGRIA')")
    BigDecimal calcularTotalSaidas(Long caixaId);
}
