# 📁 DTO - DATA TRANSFER OBJECTS

Esta pasta contém todos os DTOs para transferência de dados entre camadas.

---

## 📊 ESTRUTURA

```
dto/
├── request/           # DTOs de requisição (entrada)
│   ├── LoginRequest
│   └── CriarVendaRequest
├── response/          # DTOs de resposta (saída)
│   ├── LoginResponse
│   ├── DashboardResponse
│   └── ApiResponse
└── [Entity]DTO        # DTOs de entidades
    ├── UsuarioDTO
    ├── CategoriaDTO
    ├── ProdutoDTO
    ├── ClienteDTO
    ├── VendaDTO
    ├── ItemVendaDTO
    ├── PagamentoDTO
    └── CaixaDTO
```

---

## 📋 DTOs CRIADOS

### **DTOs de Entidades:**

1. **UsuarioDTO** - Dados do usuário (sem senha)
2. **CategoriaDTO** - Dados da categoria
3. **ProdutoDTO** - Dados do produto com categoria
4. **ClienteDTO** - Dados do cliente
5. **VendaDTO** - Dados da venda com itens e pagamentos
6. **ItemVendaDTO** - Item da venda
7. **PagamentoDTO** - Pagamento da venda
8. **CaixaDTO** - Dados do caixa

### **DTOs de Request:**

1. **LoginRequest** - Email e senha
2. **CriarVendaRequest** - Dados para criar venda

### **DTOs de Response:**

1. **LoginResponse** - Token JWT e dados do usuário
2. **DashboardResponse** - Métricas do dashboard
3. **ApiResponse<T>** - Resposta genérica da API

---

## 🔧 PADRÕES UTILIZADOS

### **1. Separação de Responsabilidades:**
- **Request** - Dados de entrada (validações)
- **Response** - Dados de saída (formatação)
- **DTO** - Transferência entre camadas

### **2. Validações (Bean Validation):**
```java
@NotBlank(message = "Campo obrigatório")
@Email(message = "Email inválido")
@NotNull(message = "Não pode ser nulo")
@NotEmpty(message = "Não pode ser vazio")
@Size(min = 3, max = 100, message = "Tamanho inválido")
```

### **3. Conversão Entity ↔ DTO:**

**Entity para DTO:**
```java
public static UsuarioDTO fromEntity(Usuario usuario) {
    UsuarioDTO dto = new UsuarioDTO();
    dto.setId(usuario.getId());
    dto.setName(usuario.getName());
    // ...
    return dto;
}
```

**DTO para Entity:**
```java
public Usuario toEntity() {
    Usuario usuario = new Usuario();
    usuario.setId(this.id);
    usuario.setName(this.name);
    // ...
    return usuario;
}
```

---

## 🎯 VANTAGENS DOS DTOs

### **1. Segurança:**
- Não expõe campos sensíveis (senha, tokens internos)
- Controla quais dados são enviados/recebidos

### **2. Desacoplamento:**
- Camada de apresentação independente do modelo de dados
- Mudanças no banco não afetam a API

### **3. Validação:**
- Validações centralizadas nos DTOs
- Mensagens de erro personalizadas

### **4. Performance:**
- Carrega apenas dados necessários
- Evita lazy loading exceptions

### **5. Versionamento:**
- Facilita manter múltiplas versões da API
- DTOs diferentes para cada versão

---

## 📝 EXEMPLO DE USO

### **No Controller:**
```java
@PostMapping("/login")
public ResponseEntity<ApiResponse<LoginResponse>> login(
    @Valid @RequestBody LoginRequest request) {
    
    LoginResponse response = authService.login(request);
    return ResponseEntity.ok(ApiResponse.success(response));
}
```

### **No Service:**
```java
public LoginResponse login(LoginRequest request) {
    Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    
    // Validar senha, gerar token, etc.
    
    return new LoginResponse(token, expiresIn, userInfo);
}
```

---

## 🚀 PRÓXIMOS DTOs A CRIAR

- ContaReceberDTO
- ContaPagarDTO
- MovimentacaoEstoqueDTO
- NotificacaoDTO
- LogAtividadeDTO
- RelatorioVendasResponse
- RelatorioEstoqueResponse

---

## 📚 BOAS PRÁTICAS

1. **Nunca retornar entities diretamente**
2. **Sempre validar requests**
3. **Usar DTOs específicos para cada operação**
4. **Não incluir lógica de negócio nos DTOs**
5. **Manter DTOs simples e focados**
6. **Documentar campos complexos**
7. **Usar nomes descritivos**

---

**✅ DTOs BÁSICOS CRIADOS COM SUCESSO!**
