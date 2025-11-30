package com.vendamax.resource;

import com.vendamax.dto.ProdutoDTO;
import com.vendamax.dto.response.ApiResponse;
import com.vendamax.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Resource: Produto
 */
@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Gerenciamento de produtos")
public class ProdutoResource {

    private final ProdutoService produtoService;

    /**
     * Listar todos os produtos
     */
    @GetMapping
    @Operation(summary = "Listar Produtos", description = "Listar todos os produtos")
    public ResponseEntity<ApiResponse<List<ProdutoDTO>>> findAll() {
        List<ProdutoDTO> produtos = produtoService.findAll();
        return ResponseEntity.ok(ApiResponse.success(produtos));
    }

    /**
     * Listar produtos ativos
     */
    @GetMapping("/ativos")
    @Operation(summary = "Listar Produtos Ativos", description = "Listar apenas produtos ativos")
    public ResponseEntity<ApiResponse<List<ProdutoDTO>>> findAllActive() {
        List<ProdutoDTO> produtos = produtoService.findAllActive();
        return ResponseEntity.ok(ApiResponse.success(produtos));
    }

    /**
     * Buscar produto por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar Produto", description = "Buscar produto por ID")
    public ResponseEntity<ApiResponse<ProdutoDTO>> findById(@PathVariable Long id) {
        ProdutoDTO produto = produtoService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(produto));
    }

    /**
     * Buscar por SKU
     */
    @GetMapping("/sku/{sku}")
    @Operation(summary = "Buscar por SKU", description = "Buscar produto por SKU")
    public ResponseEntity<ApiResponse<ProdutoDTO>> findBySku(@PathVariable String sku) {
        ProdutoDTO produto = produtoService.findBySku(sku);
        return ResponseEntity.ok(ApiResponse.success(produto));
    }

    /**
     * Buscar por código de barras
     */
    @GetMapping("/barcode/{barcode}")
    @Operation(summary = "Buscar por Código de Barras", description = "Buscar produto por código de barras")
    public ResponseEntity<ApiResponse<ProdutoDTO>> findByBarcode(@PathVariable String barcode) {
        ProdutoDTO produto = produtoService.findByBarcode(barcode);
        return ResponseEntity.ok(ApiResponse.success(produto));
    }

    /**
     * Buscar por categoria
     */
    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Buscar por Categoria", description = "Buscar produtos por categoria")
    public ResponseEntity<ApiResponse<List<ProdutoDTO>>> findByCategoria(@PathVariable Long categoriaId) {
        List<ProdutoDTO> produtos = produtoService.findByCategoria(categoriaId);
        return ResponseEntity.ok(ApiResponse.success(produtos));
    }

    /**
     * Produtos com estoque baixo
     */
    @GetMapping("/estoque-baixo")
    @Operation(summary = "Estoque Baixo", description = "Listar produtos com estoque baixo")
    public ResponseEntity<ApiResponse<List<ProdutoDTO>>> findProdutosEstoqueBaixo() {
        List<ProdutoDTO> produtos = produtoService.findProdutosEstoqueBaixo();
        return ResponseEntity.ok(ApiResponse.success(produtos));
    }

    /**
     * Produtos sem estoque
     */
    @GetMapping("/sem-estoque")
    @Operation(summary = "Sem Estoque", description = "Listar produtos sem estoque")
    public ResponseEntity<ApiResponse<List<ProdutoDTO>>> findProdutosSemEstoque() {
        List<ProdutoDTO> produtos = produtoService.findProdutosSemEstoque();
        return ResponseEntity.ok(ApiResponse.success(produtos));
    }

    /**
     * Buscar por nome
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar por Nome", description = "Buscar produtos por nome")
    public ResponseEntity<ApiResponse<List<ProdutoDTO>>> searchByName(@RequestParam String nome) {
        List<ProdutoDTO> produtos = produtoService.searchByName(nome);
        return ResponseEntity.ok(ApiResponse.success(produtos));
    }

    /**
     * Criar produto
     */
    @PostMapping
    @Operation(summary = "Criar Produto", description = "Criar novo produto")
    public ResponseEntity<ApiResponse<ProdutoDTO>> create(@Valid @RequestBody ProdutoDTO dto) {
        ProdutoDTO produto = produtoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Produto criado com sucesso", produto));
    }

    /**
     * Atualizar produto
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Produto", description = "Atualizar produto existente")
    public ResponseEntity<ApiResponse<ProdutoDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoDTO dto) {
        ProdutoDTO produto = produtoService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Produto atualizado com sucesso", produto));
    }

    /**
     * Atualizar estoque
     */
    @PatchMapping("/{id}/estoque")
    @Operation(summary = "Atualizar Estoque", description = "Atualizar quantidade em estoque")
    public ResponseEntity<ApiResponse<ProdutoDTO>> updateStock(
            @PathVariable Long id,
            @RequestParam Integer quantidade) {
        ProdutoDTO produto = produtoService.updateStock(id, quantidade);
        return ResponseEntity.ok(ApiResponse.success("Estoque atualizado com sucesso", produto));
    }

    /**
     * Deletar produto
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Produto", description = "Deletar produto (soft delete)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Produto deletado com sucesso", null));
    }

    /**
     * Contar produtos ativos
     */
    @GetMapping("/count")
    @Operation(summary = "Contar Produtos", description = "Contar produtos ativos")
    public ResponseEntity<ApiResponse<Long>> countActive() {
        long count = produtoService.countActive();
        return ResponseEntity.ok(ApiResponse.success(count));
    }
}
