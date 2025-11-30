package com.vendamax.service;

import com.vendamax.config.JwtConfig;
import com.vendamax.dto.request.LoginRequest;
import com.vendamax.dto.response.LoginResponse;
import com.vendamax.entity.Usuario;
import com.vendamax.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Service: Autenticação
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    /**
     * Login do usuário
     */
    @Transactional
    public LoginResponse login(LoginRequest request) {
        // Buscar usuário por email
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário ou senha inválidos"));

        // Verificar se está ativo
        if (!usuario.getActive()) {
            throw new RuntimeException("Usuário inativo");
        }

        // Verificar senha
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }

        // Atualizar último login
        usuario.setLastLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);

        // Gerar token JWT
        String token = generateToken(usuario);

        // Criar resposta
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
                usuario.getId(),
                usuario.getName(),
                usuario.getEmail(),
                usuario.getRole().name()
        );

        return new LoginResponse(token, jwtConfig.getExpiration(), userInfo);
    }

    /**
     * Gerar token JWT
     */
    private String generateToken(Usuario usuario) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getExpiration());
        SecretKey key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("userId", usuario.getId())
                .claim("role", usuario.getRole().name())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    /**
     * Validar token JWT
     */
    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extrair email do token
     */
    public String getEmailFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
