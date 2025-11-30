# 📁 SECURITY - SEGURANÇA E AUTENTICAÇÃO

Esta pasta contém componentes de segurança JWT.

---

## 📊 COMPONENTES CRIADOS

### **1. JwtAuthenticationFilter**
**Responsabilidade:** Interceptar requisições e validar token JWT

**Funcionamento:**
1. Extrai token do header `Authorization`
2. Valida o token JWT
3. Extrai email do usuário
4. Carrega dados do usuário
5. Autentica no Spring Security

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(...) {
        // Validação e autenticação
    }
}
```

---

### **2. UserDetailsServiceImpl**
**Responsabilidade:** Carregar dados do usuário para autenticação

**Funcionamento:**
1. Busca usuário por email
2. Verifica se está ativo
3. Carrega roles e permissões
4. Retorna UserDetails

```java
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String email) {
        // Carregar usuário
    }
}
```

---

## 🔐 FLUXO DE AUTENTICAÇÃO

### **1. Login:**
```
POST /api/auth/login
{
  "email": "admin@vendamax.com",
  "password": "admin123"
}

↓

AuthService valida credenciais
↓
Gera token JWT
↓
Retorna token + dados do usuário
```

### **2. Requisição Autenticada:**
```
GET /api/produtos
Authorization: Bearer {token}

↓

JwtAuthenticationFilter intercepta
↓
Valida token
↓
Autentica usuário
↓
Processa requisição
```

---

## 🎯 ESTRUTURA DO TOKEN JWT

```json
{
  "sub": "admin@vendamax.com",
  "userId": 1,
  "role": "ADMIN",
  "iat": 1701091200,
  "exp": 1701177600
}
```

**Claims:**
- `sub` - Email do usuário (subject)
- `userId` - ID do usuário
- `role` - Role do usuário
- `iat` - Data de emissão (issued at)
- `exp` - Data de expiração (expiration)

---

## 📝 CONFIGURAÇÃO

### **application.yml:**
```yaml
jwt:
  secret: VendaMaxSecretKey2024MuitoSeguroEComplexo
  expiration: 86400000 # 24 horas
```

### **SecurityConfig:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, 
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
```

---

## 🔒 ENDPOINTS PÚBLICOS

Não requerem autenticação:
- `/auth/**` - Login e validação
- `/swagger-ui/**` - Documentação
- `/v3/api-docs/**` - API Docs

---

## 🔑 ENDPOINTS PROTEGIDOS

Requerem token JWT:
- `/categorias/**`
- `/produtos/**`
- `/clientes/**`
- `/vendas/**`
- `/dashboard/**`

---

## 📚 ROLES E PERMISSÕES

### **Roles:**
- `ADMIN` - Administrador
- `MANAGER` - Gerente
- `CASHIER` - Caixa
- `USER` - Usuário

### **Permissões:**
- `PRODUTO_CREATE`
- `PRODUTO_READ`
- `PRODUTO_UPDATE`
- `PRODUTO_DELETE`
- `VENDA_CREATE`
- `VENDA_READ`
- `VENDA_CANCEL`
- `CAIXA_OPEN`
- `CAIXA_CLOSE`
- `RELATORIO_VIEW`
- `USUARIO_MANAGE`
- `CONFIGURACAO_MANAGE`

---

## 🛡️ SEGURANÇA

### **Boas Práticas Implementadas:**
1. ✅ Senhas criptografadas (BCrypt)
2. ✅ Tokens JWT com expiração
3. ✅ Sessão stateless
4. ✅ CORS configurado
5. ✅ CSRF desabilitado (API REST)
6. ✅ Validação de token em cada requisição
7. ✅ Verificação de usuário ativo

### **Melhorias Futuras:**
- Refresh token
- Blacklist de tokens
- Rate limiting
- IP whitelist
- 2FA (Two-Factor Authentication)
- Auditoria de acessos

---

**✅ SEGURANÇA JWT COMPLETA!**
