package com.vendamax.service;

import com.vendamax.dto.ProdutoDTO;
import com.vendamax.entity.Categoria;
import com.vendamax.entity.Produto;
import com.vendamax.repository.CategoriaRepository;
import com.vendamax.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service: Produto
 */
@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    /**
     * Listar todos os produtos
     */
    public List<ProdutoDTO> findAll() {
        return produtoRepository.findAll().stream()
                .map(ProdutoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Listar produtos ativos
     */
    public List<ProdutoDTO> findAllActive() {
        return produtoRepository.findByActiveTrue().stream()
                .map(ProdutoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Buscar produto por ID
     */
    public ProdutoDTO findById(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return ProdutoDTO.fromEntity(produto);
    }

    /**
     * Buscar por SKU
     */
    public ProdutoDTO findBySku(String sku) {
        Produto produto = produtoRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return ProdutoDTO.fromEntity(produto);
    }

    /**
     * Buscar por código de barras
     */
    public ProdutoDTO findByBarcode(String barcode) {
        Produto produto = produtoRepository.findByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return ProdutoDTO.fromEntity(produto);
    }

    /**
     * Buscar produtos por categoria
     */
    public List<ProdutoDTO> findByCategoria(Long categoriaId) {
        return produtoRepository.findByCategoriaIdAndActiveTrue(categoriaId).stream()
                .map(ProdutoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Buscar produtos com estoque baixo
     */
    public List<ProdutoDTO> findProdutosEstoqueBaixo() {
        return produtoRepository.findProdutosEstoqueBaixo().stream()
                .map(ProdutoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Buscar produtos sem estoque
     */
    public List<ProdutoDTO> findProdutosSemEstoque() {
        return produtoRepository.findProdutosSemEstoque().stream()
                .map(ProdutoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Buscar por nome
     */
    public List<ProdutoDTO> searchByName(String name) {
        return produtoRepository.findByNameContainingIgnoreCaseAndActiveTrue(name).stream()
                .map(ProdutoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Criar produto
     */
    @Transactional
    public ProdutoDTO create(ProdutoDTO dto) {
        // Verificar categoria
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        // Verificar SKU duplicado
        if (dto.getSku() != null && produtoRepository.existsBySku(dto.getSku())) {
            throw new RuntimeException("Já existe um produto com este SKU");
        }

        // Verificar código de barras duplicado
        if (dto.getBarcode() != null && produtoRepository.existsByBarcode(dto.getBarcode())) {
            throw new RuntimeException("Já existe um produto com este código de barras");
        }

        Produto produto = new Produto();
        produto.setCategoria(categoria);
        produto.setName(dto.getName());
        produto.setDescription(dto.getDescription());
        produto.setSku(dto.getSku());
        produto.setBarcode(dto.getBarcode());
        produto.setPrice(dto.getPrice());
        produto.setCostPrice(dto.getCostPrice());
        produto.setSalePrice(dto.getSalePrice() != null ? dto.getSalePrice() : dto.getPrice());
        produto.setStock(dto.getStock() != null ? dto.getStock() : 0);
        produto.setMinStock(dto.getMinStock() != null ? dto.getMinStock() : 0);
        produto.setMaxStock(dto.getMaxStock());
        produto.setUnit(dto.getUnit());
        produto.setImageUrl(dto.getImageUrl());
        produto.setActive(dto.getActive() != null ? dto.getActive() : true);

        produto = produtoRepository.save(produto);
        return ProdutoDTO.fromEntity(produto);
    }

    /**
     * Atualizar produto
     */
    @Transactional
    public ProdutoDTO update(Long id, ProdutoDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verificar categoria
        if (!produto.getCategoria().getId().equals(dto.getCategoriaId())) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            produto.setCategoria(categoria);
        }

        // Verificar SKU duplicado
        if (dto.getSku() != null && !dto.getSku().equals(produto.getSku()) && 
            produtoRepository.existsBySku(dto.getSku())) {
            throw new RuntimeException("Já existe um produto com este SKU");
        }

        // Verificar código de barras duplicado
        if (dto.getBarcode() != null && !dto.getBarcode().equals(produto.getBarcode()) && 
            produtoRepository.existsByBarcode(dto.getBarcode())) {
            throw new RuntimeException("Já existe um produto com este código de barras");
        }

        produto.setName(dto.getName());
        produto.setDescription(dto.getDescription());
        produto.setSku(dto.getSku());
        produto.setBarcode(dto.getBarcode());
        produto.setPrice(dto.getPrice());
        produto.setCostPrice(dto.getCostPrice());
        produto.setSalePrice(dto.getSalePrice() != null ? dto.getSalePrice() : dto.getPrice());
        produto.setMinStock(dto.getMinStock());
        produto.setMaxStock(dto.getMaxStock());
        produto.setUnit(dto.getUnit());
        produto.setImageUrl(dto.getImageUrl());
        produto.setActive(dto.getActive());

        produto = produtoRepository.save(produto);
        return ProdutoDTO.fromEntity(produto);
    }

    /**
     * Atualizar estoque
     */
    @Transactional
    public ProdutoDTO updateStock(Long id, Integer quantity) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setStock(quantity);
        produto = produtoRepository.save(produto);
        return ProdutoDTO.fromEntity(produto);
    }

    /**
     * Deletar produto (soft delete)
     */
    @Transactional
    public void delete(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setActive(false);
        produtoRepository.save(produto);
    }

    /**
     * Contar produtos ativos
     */
    public long countActive() {
        return produtoRepository.countActiveProdutos();
    }
}
