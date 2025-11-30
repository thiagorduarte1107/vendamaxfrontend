package com.vendamax.repository;

import com.vendamax.entity.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository: Notificacao
 */
@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    /**
     * Buscar notificações por usuário
     */
    List<Notificacao> findByUsuarioIdOrderByCreatedAtDesc(Long usuarioId);

    /**
     * Buscar notificações não lidas do usuário
     */
    List<Notificacao> findByUsuarioIdAndIsReadFalseOrderByCreatedAtDesc(Long usuarioId);

    /**
     * Buscar notificações por tipo
     */
    List<Notificacao> findByTypeOrderByCreatedAtDesc(Notificacao.TipoNotificacao type);

    /**
     * Buscar notificações não lidas por tipo
     */
    List<Notificacao> findByUsuarioIdAndTypeAndIsReadFalseOrderByCreatedAtDesc(Long usuarioId, Notificacao.TipoNotificacao type);

    /**
     * Contar notificações não lidas do usuário
     */
    long countByUsuarioIdAndIsReadFalse(Long usuarioId);

    /**
     * Buscar últimas notificações do usuário
     */
    List<Notificacao> findTop10ByUsuarioIdOrderByCreatedAtDesc(Long usuarioId);
}
