package com.vendamax.resource;

import com.vendamax.dto.MovimentacaoCaixaDTO;
import com.vendamax.dto.response.ApiResponse;
import com.vendamax.service.MovimentacaoCaixaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Resource: Movimentação de Caixa
 */
@RestController
@RequestMapping("/movimentacoes-caixa")
@RequiredArgsConstructor
@Tag(name = "Movimentação Caixa", description = "Sangrias e Suprimentos")
public class MovimentacaoCaixaResource {

    private final MovimentacaoCaixaService movimentacaoService;

    /**
     * Registrar sangria
     */
    @PostMapping("/sangria")
    @Operation(summary = "Registrar Sangria", description = "Retirar dinheiro do caixa")
    public ResponseEntity<ApiResponse<MovimentacaoCaixaDTO>> registerWithdrawal(
            @RequestParam Long caixaId,
            @RequestParam BigDecimal valor,
            @RequestParam(required = false) String descricao) {
        MovimentacaoCaixaDTO movimentacao = movimentacaoService.registerWithdrawal(caixaId, valor, descricao);
        return ResponseEntity.ok(ApiResponse.success("Sangria registrada com sucesso", movimentacao));
    }

    /**
     * Registrar suprimento
     */
    @PostMapping("/suprimento")
    @Operation(summary = "Registrar Suprimento", description = "Adicionar dinheiro ao caixa")
    public ResponseEntity<ApiResponse<MovimentacaoCaixaDTO>> registerDeposit(
            @RequestParam Long caixaId,
            @RequestParam BigDecimal valor,
            @RequestParam(required = false) String descricao) {
        MovimentacaoCaixaDTO movimentacao = movimentacaoService.registerDeposit(caixaId, valor, descricao);
        return ResponseEntity.ok(ApiResponse.success("Suprimento registrado com sucesso", movimentacao));
    }

    /**
     * Listar movimentações de um caixa
     */
    @GetMapping("/caixa/{caixaId}")
    @Operation(summary = "Listar Movimentações", description = "Listar todas as movimentações de um caixa")
    public ResponseEntity<ApiResponse<List<MovimentacaoCaixaDTO>>> findByCaixa(@PathVariable Long caixaId) {
        List<MovimentacaoCaixaDTO> movimentacoes = movimentacaoService.findByCaixa(caixaId);
        return ResponseEntity.ok(ApiResponse.success(movimentacoes));
    }
}
