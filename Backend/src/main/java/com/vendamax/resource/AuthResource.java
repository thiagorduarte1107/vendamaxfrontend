package com.vendamax.resource;

import com.vendamax.dto.request.LoginRequest;
import com.vendamax.dto.response.ApiResponse;
import com.vendamax.dto.response.LoginResponse;
import com.vendamax.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Resource: Autenticação
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints de autenticação e autorização")
public class AuthResource {

    private final AuthService authService;

    /**
     * Login
     */
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autenticar usuário e obter token JWT")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login realizado com sucesso", response));
    }

    /**
     * Validar token
     */
    @GetMapping("/validate")
    @Operation(summary = "Validar Token", description = "Validar se o token JWT é válido")
    public ResponseEntity<ApiResponse<Boolean>> validateToken(@RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", "");
        boolean isValid = authService.validateToken(jwtToken);
        return ResponseEntity.ok(ApiResponse.success(isValid));
    }
}
