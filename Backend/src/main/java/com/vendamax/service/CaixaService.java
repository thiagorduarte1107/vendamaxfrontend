package com.vendamax.service;

import com.vendamax.dto.CaixaDTO;
import com.vendamax.entity.Caixa;
import com.vendamax.entity.Usuario;
import com.vendamax.repository.CaixaRepository;
import com.vendamax.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Service: Caixa
 */
@Service
@RequiredArgsConstructor
public class CaixaService {

    private final CaixaRepository caixaRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Buscar caixa aberto do usuário
     */
    public CaixaDTO findOpenByUser(Long usuarioId) {
        Caixa caixa = caixaRepository.findByUsuarioIdAndStatus(usuarioId, Caixa.CaixaStatus.OPEN)
                .orElse(null);
        return caixa != null ? CaixaDTO.fromEntity(caixa) : null;
    }

    /**
     * Abrir caixa
     */
    @Transactional
    public CaixaDTO openCashRegister(Long usuarioId, BigDecimal saldoInicial) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verificar se já existe caixa aberto
        caixaRepository.findByUsuarioIdAndStatus(usuarioId, Caixa.CaixaStatus.OPEN)
                .ifPresent(c -> {
                    throw new RuntimeException("Já existe um caixa aberto para este usuário");
                });

        Caixa caixa = new Caixa();
        caixa.setUsuario(usuario);
        caixa.setOpeningBalance(saldoInicial);
        caixa.setStatus(Caixa.CaixaStatus.OPEN);
        caixa.setOpenedAt(LocalDateTime.now());

        caixa = caixaRepository.save(caixa);
        return CaixaDTO.fromEntity(caixa);
    }

    /**
     * Fechar caixa
     */
    @Transactional
    public CaixaDTO closeCashRegister(Long caixaId, String observacoes) {
        Caixa caixa = caixaRepository.findById(caixaId)
                .orElseThrow(() -> new RuntimeException("Caixa não encontrado"));

        if (caixa.getStatus() != Caixa.CaixaStatus.OPEN) {
            throw new RuntimeException("Caixa já está fechado");
        }

        // Calcular saldo final (saldo inicial + vendas - sangrias + suprimentos)
        BigDecimal saldoFinal = calcularSaldoFinal(caixa);

        caixa.setStatus(Caixa.CaixaStatus.CLOSED);
        caixa.setClosedAt(LocalDateTime.now());
        caixa.setClosingBalance(saldoFinal);
        caixa.setNotes(observacoes);

        caixa = caixaRepository.save(caixa);
        return CaixaDTO.fromEntity(caixa);
    }

    /**
     * Buscar caixa por ID
     */
    public CaixaDTO findById(Long id) {
        Caixa caixa = caixaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Caixa não encontrado"));
        return CaixaDTO.fromEntity(caixa);
    }

    /**
     * Calcular saldo final do caixa
     */
    private BigDecimal calcularSaldoFinal(Caixa caixa) {
        // TODO: Implementar cálculo real com vendas e movimentações
        // Por enquanto, retorna apenas o saldo inicial
        return caixa.getOpeningBalance();
    }
}
