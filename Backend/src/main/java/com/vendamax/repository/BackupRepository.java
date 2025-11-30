package com.vendamax.repository;

import com.vendamax.entity.Backup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository: Backup
 */
@Repository
public interface BackupRepository extends JpaRepository<Backup, Long> {

    /**
     * Buscar backups por status
     */
    List<Backup> findByStatusOrderByCreatedAtDesc(Backup.BackupStatus status);

    /**
     * Buscar backups por período
     */
    @Query("SELECT b FROM Backup b WHERE b.createdAt BETWEEN :inicio AND :fim ORDER BY b.createdAt DESC")
    List<Backup> findByPeriodo(LocalDateTime inicio, LocalDateTime fim);

    /**
     * Buscar último backup bem-sucedido
     */
    @Query(value = "SELECT TOP 1 * FROM backups WHERE status = 'SUCCESS' ORDER BY created_at DESC", nativeQuery = true)
    Backup findUltimoBackupSucesso();

    /**
     * Buscar últimos backups
     */
    List<Backup> findTop10ByOrderByCreatedAtDesc();

    /**
     * Contar backups por status
     */
    long countByStatus(Backup.BackupStatus status);
}
