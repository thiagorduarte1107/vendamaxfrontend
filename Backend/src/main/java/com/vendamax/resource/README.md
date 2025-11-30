# 📁 RESOURCE - CONTROLLERS REST

Esta pasta contém todos os resources (controllers) REST da API.

---

## 📊 RESOURCES CRIADOS (6)

### **1. AuthResource**
**Base URL:** `/api/auth`

**Endpoints:**
- `POST /login` - Login e obtenção de token JWT
- `GET /validate` - Validar token JWT

---

### **2. CategoriaResource**
**Base URL:** `/api/categorias`

**Endpoints:**
- `GET /` - Listar todas
- `GET /ativas` - Listar ativas
- `GET /{id}` - Buscar por ID
- `GET /buscar?nome=` - Buscar por nome
- `POST /` - Criar
- `PUT /{id}` - Atualizar
- `DELETE /{id}` - Deletar (soft)

---

### **3. ProdutoResource**
**Base URL:** `/api/produtos`

**Endpoints:**
- `GET /` - Listar todos
- `GET /ativos` - Listar ativos
- `GET /{id}` - Buscar por ID
- `GET /sku/{sku}` - Buscar por SKU
- `GET /barcode/{barcode}` - Buscar por código de barras
- `GET /categoria/{categoriaId}` - Buscar por categoria
- `GET /estoque-baixo` - Produtos com estoque baixo
- `GET /sem-estoque` - Produtos sem estoque
- `GET /buscar?nome=` - Buscar por nome
- `GET /count` - Contar ativos
- `POST /` - Criar
- `PUT /{id}` - Atualizar
- `PATCH /{id}/estoque?quantidade=` - Atualizar estoque
- `DELETE /{id}` - Deletar (soft)

---

### **4. ClienteResource**
**Base URL:** `/api/clientes`

**Endpoints:**
- `GET /` - Listar todos
- `GET /ativos` - Listar ativos
- `GET /{id}` - Buscar por ID
- `GET /cpf/{cpf}` - Buscar por CPF
- `GET /cnpj/{cnpj}` - Buscar por CNPJ
- `GET /buscar?nome=` - Buscar por nome
- `GET /count` - Contar ativos
- `POST /` - Criar
- `PUT /{id}` - Atualizar
- `DELETE /{id}` - Deletar (soft)

---

### **5. VendaResource**
**Base URL:** `/api/vendas`

**Endpoints:**
- `GET /` - Listar todas
- `GET /{id}` - Buscar por ID
- `GET /dia` - Vendas do dia
- `GET /cliente/{clienteId}` - Vendas por cliente
- `GET /periodo?inicio=&fim=` - Vendas por período
- `GET /total-dia` - Total de vendas do dia
- `GET /count-dia` - Quantidade de vendas do dia
- `POST /` - Criar venda
- `PUT /{id}/cancelar` - Cancelar venda

---

### **6. DashboardResource**
**Base URL:** `/api/dashboard`

**Endpoints:**
- `GET /metricas` - Obter todas as métricas

---

## 🔧 PADRÕES UTILIZADOS

### **1. Anotações Spring:**
```java
@RestController          // Define como REST controller
@RequestMapping("/path") // Base path do resource
@RequiredArgsConstructor // Injeção de dependências (Lombok)
@Tag                     // Documentação Swagger
```

### **2. Métodos HTTP:**
```java
@GetMapping     // Buscar/Listar
@PostMapping    // Criar
@PutMapping     // Atualizar completo
@PatchMapping   // Atualizar parcial
@DeleteMapping  // Deletar
```

### **3. Parâmetros:**
```java
@PathVariable Long id           // Parâmetro na URL
@RequestParam String nome       // Query parameter
@RequestBody DTO dto            // Corpo da requisição
@RequestHeader String header    // Header HTTP
@Valid                          // Validação automática
```

### **4. Respostas:**
```java
// Sucesso
return ResponseEntity.ok(ApiResponse.success(data));

// Criado
return ResponseEntity.status(HttpStatus.CREATED)
    .body(ApiResponse.success("Mensagem", data));

// Erro (tratado por exception handler)
throw new RuntimeException("Erro");
```

---

## 📝 DOCUMENTAÇÃO SWAGGER

### **Anotações:**
```java
@Tag(name = "Nome", description = "Descrição")
@Operation(summary = "Resumo", description = "Descrição detalhada")
```

### **Acesso:**
- **Swagger UI:** http://localhost:8080/api/swagger-ui.html
- **API Docs:** http://localhost:8080/api/v3/api-docs

---

## 🎯 EXEMPLOS DE REQUISIÇÕES

### **1. Login:**
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@vendamax.com",
  "password": "admin123"
}
```

### **2. Criar Produto:**
```http
POST /api/produtos
Authorization: Bearer {token}
Content-Type: application/json

{
  "categoriaId": 1,
  "name": "Produto Teste",
  "price": 10.50,
  "stock": 100
}
```

### **3. Criar Venda:**
```http
POST /api/vendas
Authorization: Bearer {token}
User-Id: 1
Content-Type: application/json

{
  "caixaId": 1,
  "itens": [
    {
      "produtoId": 1,
      "quantity": 2,
      "unitPrice": 10.50
    }
  ],
  "pagamentos": [
    {
      "paymentMethod": "DINHEIRO",
      "amount": 21.00
    }
  ]
}
```

---

## 🔐 SEGURANÇA

### **Endpoints Públicos:**
- `/api/auth/**` - Login e validação

### **Endpoints Protegidos:**
- Todos os outros requerem token JWT
- Header: `Authorization: Bearer {token}`

---

## 📊 CÓDIGOS DE STATUS HTTP

### **Sucesso:**
- `200 OK` - Requisição bem-sucedida
- `201 Created` - Recurso criado
- `204 No Content` - Sem conteúdo (delete)

### **Erro Cliente:**
- `400 Bad Request` - Dados inválidos
- `401 Unauthorized` - Não autenticado
- `403 Forbidden` - Sem permissão
- `404 Not Found` - Não encontrado
- `409 Conflict` - Conflito (duplicado)

### **Erro Servidor:**
- `500 Internal Server Error` - Erro interno

---

## 🚀 PRÓXIMOS RESOURCES

- **CaixaResource** - Gerenciamento de caixa
- **ContaReceberResource** - Contas a receber
- **ContaPagarResource** - Contas a pagar
- **MovimentacaoEstoqueResource** - Movimentações
- **NotificacaoResource** - Notificações
- **RelatorioResource** - Relatórios

---

## 📚 BOAS PRÁTICAS

1. **Usar verbos HTTP corretos**
2. **Retornar códigos de status apropriados**
3. **Validar dados de entrada**
4. **Documentar com Swagger**
5. **Usar DTOs (não expor entities)**
6. **Tratamento de erros centralizado**
7. **Versionamento de API (futuro: /v1/)**
8. **Paginação para listas grandes**
9. **HATEOAS (futuro)**
10. **Rate limiting (futuro)**

---

**✅ 6 RESOURCES REST CRIADOS COM SUCESSO!**
