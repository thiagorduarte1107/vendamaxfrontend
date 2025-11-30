package com.vendamax.security;

import com.vendamax.entity.Usuario;
import com.vendamax.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * UserDetailsService Implementation
 * Carrega dados do usuário para autenticação
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        if (!usuario.getActive()) {
            throw new UsernameNotFoundException("Usuário inativo: " + email);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name()));

        // Adicionar permissões
        usuario.getPermissoes().forEach(permissao -> {
            authorities.add(new SimpleGrantedAuthority(permissao.getName()));
        });

        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getActive(),
                true,
                true,
                true,
                authorities
        );
    }
}
