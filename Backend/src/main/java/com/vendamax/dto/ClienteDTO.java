package com.vendamax.dto;

import com.vendamax.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO: Cliente
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String cpf;
    private String cnpj;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String notes;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Converter Entity para DTO
     */
    public static ClienteDTO fromEntity(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setName(cliente.getName());
        dto.setEmail(cliente.getEmail());
        dto.setPhone(cliente.getPhone());
        dto.setCpf(cliente.getCpf());
        dto.setCnpj(cliente.getCnpj());
        dto.setAddress(cliente.getAddress());
        dto.setCity(cliente.getCity());
        dto.setState(cliente.getState());
        dto.setZipcode(cliente.getZipcode());
        dto.setNotes(cliente.getNotes());
        dto.setActive(cliente.getActive());
        dto.setCreatedAt(cliente.getCreatedAt());
        dto.setUpdatedAt(cliente.getUpdatedAt());
        return dto;
    }

    /**
     * Converter DTO para Entity
     */
    public Cliente toEntity() {
        Cliente cliente = new Cliente();
        cliente.setId(this.id);
        cliente.setName(this.name);
        cliente.setEmail(this.email);
        cliente.setPhone(this.phone);
        cliente.setCpf(this.cpf);
        cliente.setCnpj(this.cnpj);
        cliente.setAddress(this.address);
        cliente.setCity(this.city);
        cliente.setState(this.state);
        cliente.setZipcode(this.zipcode);
        cliente.setNotes(this.notes);
        cliente.setActive(this.active);
        return cliente;
    }
}
