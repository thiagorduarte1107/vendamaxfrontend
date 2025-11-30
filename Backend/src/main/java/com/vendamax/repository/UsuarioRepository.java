package com.vendamax.repository;

import com.vendamax.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository: Usuario
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Buscar usuário por email
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verificar se email já existe
     */
    boolean existsByEmail(String email);

    /**
     * Buscar usuários ativos
     */
    List<Usuario> findByActiveTrue();

    /**
     * Buscar usuários por role
     */
    List<Usuario> findByRole(Usuario.UserRole role);

    /**
     * Buscar usuários ativos por role
     */
    List<Usuario> findByActiveTrueAndRole(Usuario.UserRole role);

    /**
     * Buscar usuários por nome (contém)
     */
    List<Usuario> findByNameContainingIgnoreCase(String name);

    /**
     * Contar usuários ativos
     */
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.active = true")
    long countActiveUsers();
}
