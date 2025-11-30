package com.vendamax.repository;

import com.vendamax.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository: Categoria
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    /**
     * Buscar categoria por nome
     */
    Optional<Categoria> findByName(String name);

    /**
     * Buscar categorias ativas
     */
    List<Categoria> findByActiveTrue();

    /**
     * Buscar categorias por nome (contém)
     */
    List<Categoria> findByNameContainingIgnoreCase(String name);

    /**
     * Verificar se categoria existe por nome
     */
    boolean existsByName(String name);

    /**
     * Contar produtos por categoria
     */
    @Query("SELECT COUNT(p) FROM Produto p WHERE p.categoria.id = :categoriaId AND p.active = true")
    long countProdutosByCategoria(Long categoriaId);
}
