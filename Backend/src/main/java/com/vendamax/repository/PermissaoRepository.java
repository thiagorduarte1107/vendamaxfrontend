package com.vendamax.repository;

import com.vendamax.entity.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository: Permissao
 */
@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    /**
     * Buscar permissão por nome
     */
    Optional<Permissao> findByName(String name);

    /**
     * Buscar permissões ativas
     */
    List<Permissao> findByActiveTrue();

    /**
     * Verificar se permissão existe por nome
     */
    boolean existsByName(String name);
}
