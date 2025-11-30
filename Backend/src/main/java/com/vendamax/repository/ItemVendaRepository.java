package com.vendamax.repository;

import com.vendamax.entity.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository: ItemVenda
 */
@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

    /**
     * Buscar itens por venda
     */
    List<ItemVenda> findByVendaId(Long vendaId);

    /**
     * Buscar itens por produto
     */
    List<ItemVenda> findByProdutoId(Long produtoId);

    /**
     * Buscar produtos mais vendidos
     */
    @Query("SELECT i.produto.id, i.produto.name, SUM(i.quantity) as total " +
           "FROM ItemVenda i " +
           "GROUP BY i.produto.id, i.produto.name " +
           "ORDER BY total DESC")
    List<Object[]> findProdutosMaisVendidos();

    /**
     * Contar itens por venda
     */
    long countByVendaId(Long vendaId);
}
