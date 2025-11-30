package com.vendamax.repository;

import com.vendamax.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository: Cliente
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Buscar cliente por CPF
     */
    Optional<Cliente> findByCpf(String cpf);

    /**
     * Buscar cliente por CNPJ
     */
    Optional<Cliente> findByCnpj(String cnpj);

    /**
     * Buscar cliente por email
     */
    Optional<Cliente> findByEmail(String email);

    /**
     * Buscar clientes ativos
     */
    List<Cliente> findByActiveTrue();

    /**
     * Buscar clientes por nome (contém)
     */
    List<Cliente> findByNameContainingIgnoreCaseAndActiveTrue(String name);

    /**
     * Buscar clientes por cidade
     */
    List<Cliente> findByCityAndActiveTrue(String city);

    /**
     * Verificar se CPF já existe
     */
    boolean existsByCpf(String cpf);

    /**
     * Verificar se CNPJ já existe
     */
    boolean existsByCnpj(String cnpj);

    /**
     * Contar clientes ativos
     */
    @Query("SELECT COUNT(c) FROM Cliente c WHERE c.active = true")
    long countActiveClientes();
}
