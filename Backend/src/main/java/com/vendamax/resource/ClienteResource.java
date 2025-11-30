package com.vendamax.resource;

import com.vendamax.dto.ClienteDTO;
import com.vendamax.dto.response.ApiResponse;
import com.vendamax.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Resource: Cliente
 */
@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Gerenciamento de clientes")
public class ClienteResource {

    private final ClienteService clienteService;

    /**
     * Listar todos os clientes
     */
    @GetMapping
    @Operation(summary = "Listar Clientes", description = "Listar todos os clientes")
    public ResponseEntity<ApiResponse<List<ClienteDTO>>> findAll() {
        List<ClienteDTO> clientes = clienteService.findAll();
        return ResponseEntity.ok(ApiResponse.success(clientes));
    }

    /**
     * Listar clientes ativos
     */
    @GetMapping("/ativos")
    @Operation(summary = "Listar Clientes Ativos", description = "Listar apenas clientes ativos")
    public ResponseEntity<ApiResponse<List<ClienteDTO>>> findAllActive() {
        List<ClienteDTO> clientes = clienteService.findAllActive();
        return ResponseEntity.ok(ApiResponse.success(clientes));
    }

    /**
     * Buscar cliente por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar Cliente", description = "Buscar cliente por ID")
    public ResponseEntity<ApiResponse<ClienteDTO>> findById(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(cliente));
    }

    /**
     * Buscar por CPF
     */
    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar por CPF", description = "Buscar cliente por CPF")
    public ResponseEntity<ApiResponse<ClienteDTO>> findByCpf(@PathVariable String cpf) {
        ClienteDTO cliente = clienteService.findByCpf(cpf);
        return ResponseEntity.ok(ApiResponse.success(cliente));
    }

    /**
     * Buscar por CNPJ
     */
    @GetMapping("/cnpj/{cnpj}")
    @Operation(summary = "Buscar por CNPJ", description = "Buscar cliente por CNPJ")
    public ResponseEntity<ApiResponse<ClienteDTO>> findByCnpj(@PathVariable String cnpj) {
        ClienteDTO cliente = clienteService.findByCnpj(cnpj);
        return ResponseEntity.ok(ApiResponse.success(cliente));
    }

    /**
     * Buscar por nome
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar por Nome", description = "Buscar clientes por nome")
    public ResponseEntity<ApiResponse<List<ClienteDTO>>> searchByName(@RequestParam String nome) {
        List<ClienteDTO> clientes = clienteService.searchByName(nome);
        return ResponseEntity.ok(ApiResponse.success(clientes));
    }

    /**
     * Criar cliente
     */
    @PostMapping
    @Operation(summary = "Criar Cliente", description = "Criar novo cliente")
    public ResponseEntity<ApiResponse<ClienteDTO>> create(@Valid @RequestBody ClienteDTO dto) {
        ClienteDTO cliente = clienteService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Cliente criado com sucesso", cliente));
    }

    /**
     * Atualizar cliente
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Cliente", description = "Atualizar cliente existente")
    public ResponseEntity<ApiResponse<ClienteDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody ClienteDTO dto) {
        ClienteDTO cliente = clienteService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Cliente atualizado com sucesso", cliente));
    }

    /**
     * Deletar cliente
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Cliente", description = "Deletar cliente (soft delete)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Cliente deletado com sucesso", null));
    }

    /**
     * Contar clientes ativos
     */
    @GetMapping("/count")
    @Operation(summary = "Contar Clientes", description = "Contar clientes ativos")
    public ResponseEntity<ApiResponse<Long>> countActive() {
        long count = clienteService.countActive();
        return ResponseEntity.ok(ApiResponse.success(count));
    }
}
