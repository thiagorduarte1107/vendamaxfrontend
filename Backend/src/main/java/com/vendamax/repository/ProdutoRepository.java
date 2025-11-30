package com.vendamax.repository;

import com.vendamax.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository: Produto
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /**
     * Buscar produto por SKU
     */
    Optional<Produto> findBySku(String sku);

    /**
     * Buscar produto por código de barras
     */
    Optional<Produto> findByBarcode(String barcode);

    /**
     * Buscar produtos ativos
     */
    List<Produto> findByActiveTrue();

    /**
     * Buscar produtos por categoria
     */
    List<Produto> findByCategoriaIdAndActiveTrue(Long categoriaId);

    /**
     * Buscar produtos por nome (contém)
     */
    List<Produto> findByNameContainingIgnoreCaseAndActiveTrue(String name);

    /**
     * Buscar produtos com estoque baixo
     */
    @Query("SELECT p FROM Produto p WHERE p.stock <= p.minStock AND p.active = true")
    List<Produto> findProdutosEstoqueBaixo();

    /**
     * Buscar produtos sem estoque
     */
    @Query("SELECT p FROM Produto p WHERE p.stock = 0 AND p.active = true")
    List<Produto> findProdutosSemEstoque();

    /**
     * Verificar se SKU já existe
     */
    boolean existsBySku(String sku);

    /**
     * Verificar se código de barras já existe
     */
    boolean existsByBarcode(String barcode);

    /**
     * Contar produtos ativos
     */
    @Query("SELECT COUNT(p) FROM Produto p WHERE p.active = true")
    long countActiveProdutos();
}
