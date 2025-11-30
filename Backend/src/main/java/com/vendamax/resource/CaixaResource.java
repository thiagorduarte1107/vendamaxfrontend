package com.vendamax.resource;

import com.vendamax.dto.CaixaDTO;
import com.vendamax.dto.response.ApiResponse;
import com.vendamax.service.CaixaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Resource: Caixa
 */
@RestController
@RequestMapping("/caixas")
@RequiredArgsConstructor
@Tag(name = "Caixa", description = "Gerenciamento de Caixas")
public class CaixaResource {

    private final CaixaService caixaService;

    /**
     * Buscar caixa aberto do usuário
     */
    @GetMapping("/aberto")
    @Operation(summary = "Buscar Caixa Aberto", description = "Buscar caixa aberto do usuário logado")
    public ResponseEntity<ApiResponse<CaixaDTO>> findOpenCashRegister(@RequestHeader("User-Id") Long usuarioId) {
        CaixaDTO caixa = caixaService.findOpenByUser(usuarioId);
        return ResponseEntity.ok(ApiResponse.success(caixa));
    }

    /**
     * Abrir caixa
     */
    @PostMapping("/abrir")
    @Operation(summary = "Abrir Caixa", description = "Abrir um novo caixa")
    public ResponseEntity<ApiResponse<CaixaDTO>> openCashRegister(
            @RequestHeader("User-Id") Long usuarioId,
            @RequestParam BigDecimal saldoInicial) {
        CaixaDTO caixa = caixaService.openCashRegister(usuarioId, saldoInicial);
        return ResponseEntity.ok(ApiResponse.success("Caixa aberto com sucesso", caixa));
    }

    /**
     * Fechar caixa
     */
    @PostMapping("/{id}/fechar")
    @Operation(summary = "Fechar Caixa", description = "Fechar o caixa atual")
    public ResponseEntity<ApiResponse<CaixaDTO>> closeCashRegister(
            @PathVariable Long id,
            @RequestParam(required = false) String observacoes) {
        CaixaDTO caixa = caixaService.closeCashRegister(id, observacoes);
        return ResponseEntity.ok(ApiResponse.success("Caixa fechado com sucesso", caixa));
    }

    /**
     * Buscar caixa por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar Caixa por ID")
    public ResponseEntity<ApiResponse<CaixaDTO>> findById(@PathVariable Long id) {
        CaixaDTO caixa = caixaService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(caixa));
    }
}
