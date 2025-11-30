package com.vendamax.resource;

import com.vendamax.dto.VendaDTO;
import com.vendamax.dto.request.CriarVendaRequest;
import com.vendamax.dto.response.ApiResponse;
import com.vendamax.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Resource: Venda
 */
@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
@Tag(name = "Vendas", description = "Gerenciamento de vendas")
public class VendaResource {

    private final VendaService vendaService;

    /**
     * Listar todas as vendas
     */
    @GetMapping
    @Operation(summary = "Listar Vendas", description = "Listar todas as vendas")
    public ResponseEntity<ApiResponse<List<VendaDTO>>> findAll() {
        List<VendaDTO> vendas = vendaService.findAll();
        return ResponseEntity.ok(ApiResponse.success(vendas));
    }

    /**
     * Buscar venda por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar Venda", description = "Buscar venda por ID")
    public ResponseEntity<ApiResponse<VendaDTO>> findById(@PathVariable Long id) {
        VendaDTO venda = vendaService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(venda));
    }

    /**
     * Vendas do dia
     */
    @GetMapping("/dia")
    @Operation(summary = "Vendas do Dia", description = "Listar vendas do dia atual")
    public ResponseEntity<ApiResponse<List<VendaDTO>>> findVendasDoDia() {
        List<VendaDTO> vendas = vendaService.findVendasDoDia();
        return ResponseEntity.ok(ApiResponse.success(vendas));
    }

    /**
     * Vendas por cliente
     */
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Vendas por Cliente", description = "Listar vendas de um cliente")
    public ResponseEntity<ApiResponse<List<VendaDTO>>> findByCliente(@PathVariable Long clienteId) {
        List<VendaDTO> vendas = vendaService.findByCliente(clienteId);
        return ResponseEntity.ok(ApiResponse.success(vendas));
    }

    /**
     * Vendas por período
     */
    @GetMapping("/periodo")
    @Operation(summary = "Vendas por Período", description = "Listar vendas em um período")
    public ResponseEntity<ApiResponse<List<VendaDTO>>> findByPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        List<VendaDTO> vendas = vendaService.findByPeriodo(inicio, fim);
        return ResponseEntity.ok(ApiResponse.success(vendas));
    }

    /**
     * Criar venda
     */
    @PostMapping
    @Operation(summary = "Criar Venda", description = "Criar nova venda")
    public ResponseEntity<ApiResponse<VendaDTO>> create(
            @Valid @RequestBody CriarVendaRequest request,
            @RequestHeader("User-Id") Long usuarioId) {
        VendaDTO venda = vendaService.create(request, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Venda criada com sucesso", venda));
    }

    /**
     * Cancelar venda
     */
    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar Venda", description = "Cancelar venda existente")
    public ResponseEntity<ApiResponse<VendaDTO>> cancelar(@PathVariable Long id) {
        VendaDTO venda = vendaService.cancelar(id);
        return ResponseEntity.ok(ApiResponse.success("Venda cancelada com sucesso", venda));
    }

    /**
     * Total de vendas do dia
     */
    @GetMapping("/total-dia")
    @Operation(summary = "Total do Dia", description = "Calcular total de vendas do dia")
    public ResponseEntity<ApiResponse<BigDecimal>> calcularTotalVendasDia() {
        BigDecimal total = vendaService.calcularTotalVendasDia();
        return ResponseEntity.ok(ApiResponse.success(total));
    }

    /**
     * Quantidade de vendas do dia
     */
    @GetMapping("/count-dia")
    @Operation(summary = "Quantidade do Dia", description = "Contar vendas do dia")
    public ResponseEntity<ApiResponse<Long>> countVendasDia() {
        long count = vendaService.countVendasDia();
        return ResponseEntity.ok(ApiResponse.success(count));
    }
}
