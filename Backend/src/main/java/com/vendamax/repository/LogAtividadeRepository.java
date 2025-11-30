package com.vendamax.repository;

import com.vendamax.entity.LogAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository: LogAtividade
 */
@Repository
public interface LogAtividadeRepository extends JpaRepository<LogAtividade, Long> {

    /**
     * Buscar logs por usuário
     */
    List<LogAtividade> findByUsuarioIdOrderByCreatedAtDesc(Long usuarioId);

    /**
     * Buscar logs por ação
     */
    List<LogAtividade> findByActionOrderByCreatedAtDesc(String action);

    /**
     * Buscar logs por entidade
     */
    List<LogAtividade> findByEntityOrderByCreatedAtDesc(String entity);

    /**
     * Buscar logs por entidade e ID
     */
    List<LogAtividade> findByEntityAndEntityIdOrderByCreatedAtDesc(String entity, Long entityId);

    /**
     * Buscar logs por período
     */
    @Query("SELECT l FROM LogAtividade l WHERE l.createdAt BETWEEN :inicio AND :fim ORDER BY l.createdAt DESC")
    List<LogAtividade> findByPeriodo(LocalDateTime inicio, LocalDateTime fim);

    /**
     * Buscar últimos logs
     */
    List<LogAtividade> findTop50ByOrderByCreatedAtDesc();

    /**
     * Buscar logs do dia
     */
    @Query("SELECT l FROM LogAtividade l WHERE CAST(l.createdAt AS date) = CURRENT_DATE ORDER BY l.createdAt DESC")
    List<LogAtividade> findLogsDoDia();
}
