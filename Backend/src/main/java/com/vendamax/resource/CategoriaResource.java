package com.vendamax.resource;

import com.vendamax.dto.CategoriaDTO;
import com.vendamax.dto.response.ApiResponse;
import com.vendamax.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Resource: Categoria
 */
@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorias", description = "Gerenciamento de categorias de produtos")
public class CategoriaResource {

    private final CategoriaService categoriaService;

    /**
     * Listar todas as categorias
     */
    @GetMapping
    @Operation(summary = "Listar Categorias", description = "Listar todas as categorias")
    public ResponseEntity<ApiResponse<List<CategoriaDTO>>> findAll() {
        List<CategoriaDTO> categorias = categoriaService.findAll();
        return ResponseEntity.ok(ApiResponse.success(categorias));
    }

    /**
     * Listar categorias ativas
     */
    @GetMapping("/ativas")
    @Operation(summary = "Listar Categorias Ativas", description = "Listar apenas categorias ativas")
    public ResponseEntity<ApiResponse<List<CategoriaDTO>>> findAllActive() {
        List<CategoriaDTO> categorias = categoriaService.findAllActive();
        return ResponseEntity.ok(ApiResponse.success(categorias));
    }

    /**
     * Buscar categoria por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar Categoria", description = "Buscar categoria por ID")
    public ResponseEntity<ApiResponse<CategoriaDTO>> findById(@PathVariable Long id) {
        CategoriaDTO categoria = categoriaService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(categoria));
    }

    /**
     * Buscar por nome
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar por Nome", description = "Buscar categorias por nome")
    public ResponseEntity<ApiResponse<List<CategoriaDTO>>> searchByName(@RequestParam String nome) {
        List<CategoriaDTO> categorias = categoriaService.searchByName(nome);
        return ResponseEntity.ok(ApiResponse.success(categorias));
    }

    /**
     * Criar categoria
     */
    @PostMapping
    @Operation(summary = "Criar Categoria", description = "Criar nova categoria")
    public ResponseEntity<ApiResponse<CategoriaDTO>> create(@Valid @RequestBody CategoriaDTO dto) {
        CategoriaDTO categoria = categoriaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Categoria criada com sucesso", categoria));
    }

    /**
     * Atualizar categoria
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Categoria", description = "Atualizar categoria existente")
    public ResponseEntity<ApiResponse<CategoriaDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaDTO dto) {
        CategoriaDTO categoria = categoriaService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Categoria atualizada com sucesso", categoria));
    }

    /**
     * Deletar categoria
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Categoria", description = "Deletar categoria (soft delete)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Categoria deletada com sucesso", null));
    }
}
