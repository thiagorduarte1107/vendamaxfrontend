package com.vendamax.service;

import com.vendamax.dto.ClienteDTO;
import com.vendamax.entity.Cliente;
import com.vendamax.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service: Cliente
 */
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Listar todos os clientes
     */
    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(ClienteDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Listar clientes ativos
     */
    public List<ClienteDTO> findAllActive() {
        return clienteRepository.findByActiveTrue().stream()
                .map(ClienteDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Buscar cliente por ID
     */
    public ClienteDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return ClienteDTO.fromEntity(cliente);
    }

    /**
     * Buscar por CPF
     */
    public ClienteDTO findByCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return ClienteDTO.fromEntity(cliente);
    }

    /**
     * Buscar por CNPJ
     */
    public ClienteDTO findByCnpj(String cnpj) {
        Cliente cliente = clienteRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return ClienteDTO.fromEntity(cliente);
    }

    /**
     * Buscar por nome
     */
    public List<ClienteDTO> searchByName(String name) {
        return clienteRepository.findByNameContainingIgnoreCaseAndActiveTrue(name).stream()
                .map(ClienteDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Criar cliente
     */
    @Transactional
    public ClienteDTO create(ClienteDTO dto) {
        // Verificar CPF duplicado
        if (dto.getCpf() != null && clienteRepository.existsByCpf(dto.getCpf())) {
            throw new RuntimeException("Já existe um cliente com este CPF");
        }

        // Verificar CNPJ duplicado
        if (dto.getCnpj() != null && clienteRepository.existsByCnpj(dto.getCnpj())) {
            throw new RuntimeException("Já existe um cliente com este CNPJ");
        }

        Cliente cliente = dto.toEntity();
        // Garantir que o cliente seja criado como ativo
        if (cliente.getActive() == null) {
            cliente.setActive(true);
        }
        cliente = clienteRepository.save(cliente);
        return ClienteDTO.fromEntity(cliente);
    }

    /**
     * Atualizar cliente
     */
    @Transactional
    public ClienteDTO update(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Verificar CPF duplicado
        if (dto.getCpf() != null && !dto.getCpf().equals(cliente.getCpf()) && 
            clienteRepository.existsByCpf(dto.getCpf())) {
            throw new RuntimeException("Já existe um cliente com este CPF");
        }

        // Verificar CNPJ duplicado
        if (dto.getCnpj() != null && !dto.getCnpj().equals(cliente.getCnpj()) && 
            clienteRepository.existsByCnpj(dto.getCnpj())) {
            throw new RuntimeException("Já existe um cliente com este CNPJ");
        }

        cliente.setName(dto.getName());
        cliente.setEmail(dto.getEmail());
        cliente.setPhone(dto.getPhone());
        cliente.setCpf(dto.getCpf());
        cliente.setCnpj(dto.getCnpj());
        cliente.setAddress(dto.getAddress());
        cliente.setCity(dto.getCity());
        cliente.setState(dto.getState());
        cliente.setZipcode(dto.getZipcode());
        cliente.setNotes(dto.getNotes());
        cliente.setActive(dto.getActive());

        cliente = clienteRepository.save(cliente);
        return ClienteDTO.fromEntity(cliente);
    }

    /**
     * Deletar cliente (soft delete)
     */
    @Transactional
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setActive(false);
        clienteRepository.save(cliente);
    }

    /**
     * Contar clientes ativos
     */
    public long countActive() {
        return clienteRepository.countActiveClientes();
    }
}
