package com.vendamax.service;

import com.vendamax.dto.MovimentacaoCaixaDTO;
import com.vendamax.entity.Caixa;
import com.vendamax.entity.MovimentacaoCaixa;
import com.vendamax.repository.CaixaRepository;
import com.vendamax.repository.MovimentacaoCaixaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service: Movimentação de Caixa
 */
@Service
@RequiredArgsConstructor
public class MovimentacaoCaixaService {

    private final MovimentacaoCaixaRepository movimentacaoRepository;
    private final CaixaRepository caixaRepository;

    /**
     * Registrar sangria (retirada)
     */
    @Transactional
    public MovimentacaoCaixaDTO registerWithdrawal(Long caixaId, BigDecimal valor, String descricao) {
        Caixa caixa = caixaRepository.findById(caixaId)
                .orElseThrow(() -> new RuntimeException("Caixa não encontrado"));

        if (caixa.getStatus() != Caixa.CaixaStatus.OPEN) {
            throw new RuntimeException("Caixa não está aberto");
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor deve ser maior que zero");
        }

        MovimentacaoCaixa movimentacao = new MovimentacaoCaixa();
        movimentacao.setCaixa(caixa);
        movimentacao.setType(MovimentacaoCaixa.TipoMovimentacao.WITHDRAWAL);
        movimentacao.setAmount(valor);
        movimentacao.setDescription(descricao != null ? descricao : "Sangria");

        movimentacao = movimentacaoRepository.save(movimentacao);
        return MovimentacaoCaixaDTO.fromEntity(movimentacao);
    }

    /**
     * Registrar suprimento (depósito)
     */
    @Transactional
    public MovimentacaoCaixaDTO registerDeposit(Long caixaId, BigDecimal valor, String descricao) {
        Caixa caixa = caixaRepository.findById(caixaId)
                .orElseThrow(() -> new RuntimeException("Caixa não encontrado"));

        if (caixa.getStatus() != Caixa.CaixaStatus.OPEN) {
            throw new RuntimeException("Caixa não está aberto");
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor deve ser maior que zero");
        }

        MovimentacaoCaixa movimentacao = new MovimentacaoCaixa();
        movimentacao.setCaixa(caixa);
        movimentacao.setType(MovimentacaoCaixa.TipoMovimentacao.DEPOSIT);
        movimentacao.setAmount(valor);
        movimentacao.setDescription(descricao != null ? descricao : "Suprimento");

        movimentacao = movimentacaoRepository.save(movimentacao);
        return MovimentacaoCaixaDTO.fromEntity(movimentacao);
    }

    /**
     * Listar movimentações de um caixa
     */
    public List<MovimentacaoCaixaDTO> findByCaixa(Long caixaId) {
        return movimentacaoRepository.findByCaixaIdOrderByCreatedAtDesc(caixaId).stream()
                .map(MovimentacaoCaixaDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
