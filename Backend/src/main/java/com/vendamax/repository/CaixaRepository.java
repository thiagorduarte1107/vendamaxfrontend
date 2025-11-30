package com.vendamax.repository;

import com.vendamax.entity.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository: Caixa
 */
@Repository
public interface CaixaRepository extends JpaRepository<Caixa, Long> {

    /**
     * Buscar caixa aberto do usuário
     */
    Optional<Caixa> findByUsuarioIdAndStatus(Long usuarioId, Caixa.CaixaStatus status);

    /**
     * Buscar caixas por status
     */
    List<Caixa> findByStatus(Caixa.CaixaStatus status);

    /**
     * Buscar caixas do usuário
     */
    List<Caixa> findByUsuarioIdOrderByOpenedAtDesc(Long usuarioId);

    /**
     * Buscar caixas por período
     */
    @Query("SELECT c FROM Caixa c WHERE c.openedAt BETWEEN :inicio AND :fim ORDER BY c.openedAt DESC")
    List<Caixa> findByPeriodo(LocalDateTime inicio, LocalDateTime fim);

    /**
     * Verificar se usuário tem caixa aberto
     */
    @Query("SELECT COUNT(c) > 0 FROM Caixa c WHERE c.usuario.id = :usuarioId AND c.status = 'OPEN'")
    boolean hasOpenCaixa(Long usuarioId);

    /**
     * Buscar último caixa do usuário
     */
    Optional<Caixa> findFirstByUsuarioIdOrderByOpenedAtDesc(Long usuarioId);
}
