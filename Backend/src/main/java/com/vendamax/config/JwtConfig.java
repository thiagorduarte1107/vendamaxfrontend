package com.vendamax.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do JWT
 * Propriedades do application.yml
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {

    /**
     * Chave secreta para assinar o token
     */
    private String secret;

    /**
     * Tempo de expiração do token (em millisegundos)
     * Padrão: 24 horas (86400000 ms)
     */
    private Long expiration;

    /**
     * Prefixo do token no header Authorization
     */
    private String tokenPrefix = "Bearer ";

    /**
     * Nome do header que contém o token
     */
    private String headerString = "Authorization";
}
