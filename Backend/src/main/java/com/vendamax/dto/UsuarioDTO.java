package com.vendamax.dto;

import com.vendamax.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO: Usuario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String name;
    private String email;
    private String role;
    private Boolean active;
    private LocalDateTime lastLogin;
    private Set<String> permissoes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Converter Entity para DTO
     */
    public static UsuarioDTO fromEntity(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setName(usuario.getName());
        dto.setEmail(usuario.getEmail());
        dto.setRole(usuario.getRole().name());
        dto.setActive(usuario.getActive());
        dto.setLastLogin(usuario.getLastLogin());
        dto.setPermissoes(usuario.getPermissoes().stream()
                .map(p -> p.getName())
                .collect(Collectors.toSet()));
        dto.setCreatedAt(usuario.getCreatedAt());
        dto.setUpdatedAt(usuario.getUpdatedAt());
        return dto;
    }
}
