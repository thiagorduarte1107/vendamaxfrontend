package com.vendamax.repository;

import com.vendamax.entity.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository: MovimentacaoEstoque
 */
@Repository
public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {

    /**
     * Buscar movimentações por produto
     */
    List<MovimentacaoEstoque> findByProdutoIdOrderByCreatedAtDesc(Long produtoId);

    /**
     * Buscar movimentações por usuário
     */
    List<MovimentacaoEstoque> findByUsuarioIdOrderByCreatedAtDesc(Long usuarioId);

    /**
     * Buscar movimentações por tipo
     */
    List<MovimentacaoEstoque> findByTypeOrderByCreatedAtDesc(MovimentacaoEstoque.TipoMovimentacao type);

    /**
     * Buscar movimentações por período
     */
    @Query("SELECT m FROM MovimentacaoEstoque m WHERE m.createdAt BETWEEN :inicio AND :fim ORDER BY m.createdAt DESC")
    List<MovimentacaoEstoque> findByPeriodo(LocalDateTime inicio, LocalDateTime fim);

    /**
     * Buscar últimas movimentações
     */
    List<MovimentacaoEstoque> findTop10ByOrderByCreatedAtDesc();
}
