package com.vendamax.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity: MovimentacaoCaixa
 * Tabela: movimentacoes_caixa
 */
@Entity
@Table(name = "movimentacoes_caixa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoCaixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cash_register_id", nullable = false)
    private Caixa caixa;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TipoMovimentacao type;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(length = 500)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum TipoMovimentacao {
        SALE,
        WITHDRAWAL,
        DEPOSIT
    }
}
