package com.vendamax.service;

import com.vendamax.dto.CategoriaDTO;
import com.vendamax.entity.Categoria;
import com.vendamax.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service: Categoria
 */
@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    /**
     * Listar todas as categorias
     */
    public List<CategoriaDTO> findAll() {
        return categoriaRepository.findAll().stream()
                .map(CategoriaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Listar categorias ativas
     */
    public List<CategoriaDTO> findAllActive() {
        return categoriaRepository.findByActiveTrue().stream()
                .map(CategoriaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Buscar categoria por ID
     */
    public CategoriaDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        return CategoriaDTO.fromEntity(categoria);
    }

    /**
     * Criar categoria
     */
    @Transactional
    public CategoriaDTO create(CategoriaDTO dto) {
        // Verificar se já existe
        if (categoriaRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Já existe uma categoria com este nome");
        }

        Categoria categoria = dto.toEntity();
        categoria = categoriaRepository.save(categoria);
        return CategoriaDTO.fromEntity(categoria);
    }

    /**
     * Atualizar categoria
     */
    @Transactional
    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        // Verificar se nome já existe (exceto para a própria categoria)
        if (!categoria.getName().equals(dto.getName()) && 
            categoriaRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Já existe uma categoria com este nome");
        }

        categoria.setName(dto.getName());
        categoria.setDescription(dto.getDescription());
        categoria.setActive(dto.getActive());

        categoria = categoriaRepository.save(categoria);
        return CategoriaDTO.fromEntity(categoria);
    }

    /**
     * Deletar categoria (soft delete)
     */
    @Transactional
    public void delete(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        // Verificar se tem produtos
        long produtosCount = categoriaRepository.countProdutosByCategoria(id);
        if (produtosCount > 0) {
            throw new RuntimeException("Não é possível excluir categoria com produtos vinculados");
        }

        categoria.setActive(false);
        categoriaRepository.save(categoria);
    }

    /**
     * Buscar por nome
     */
    public List<CategoriaDTO> searchByName(String name) {
        return categoriaRepository.findByNameContainingIgnoreCase(name).stream()
                .map(CategoriaDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
