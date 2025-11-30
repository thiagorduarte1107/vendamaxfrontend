# 📁 SERVICE - CAMADA DE NEGÓCIO

Esta pasta contém todos os services com a lógica de negócio da aplicação.

---

## 📊 SERVICES CRIADOS (6)

### **1. AuthService**
**Responsabilidade:** Autenticação e autorização
- Login de usuários
- Geração de token JWT
- Validação de token
- Extração de dados do token
- Atualização de último login

**Métodos:**
- `login(LoginRequest)` - Autenticar usuário
- `generateToken(Usuario)` - Gerar JWT
- `validateToken(String)` - Validar JWT
- `getEmailFromToken(String)` - Extrair email do token

---

### **2. CategoriaService**
**Responsabilidade:** Gerenciamento de categorias
- CRUD completo de categorias
- Validação de duplicados
- Soft delete
- Busca por nome

**Métodos:**
- `findAll()` - Listar todas
- `findAllActive()` - Listar ativas
- `findById(Long)` - Buscar por ID
- `create(CategoriaDTO)` - Criar
- `update(Long, CategoriaDTO)` - Atualizar
- `delete(Long)` - Deletar (soft)
- `searchByName(String)` - Buscar por nome

---

### **3. ProdutoService**
**Responsabilidade:** Gerenciamento de produtos
- CRUD completo de produtos
- Controle de estoque
- Validação de SKU/Barcode
- Busca por categoria
- Produtos com estoque baixo

**Métodos:**
- `findAll()` - Listar todos
- `findAllActive()` - Listar ativos
- `findById(Long)` - Buscar por ID
- `findBySku(String)` - Buscar por SKU
- `findByBarcode(String)` - Buscar por código de barras
- `findByCategoria(Long)` - Buscar por categoria
- `findProdutosEstoqueBaixo()` - Produtos com estoque baixo
- `findProdutosSemEstoque()` - Produtos sem estoque
- `searchByName(String)` - Buscar por nome
- `create(ProdutoDTO)` - Criar
- `update(Long, ProdutoDTO)` - Atualizar
- `updateStock(Long, Integer)` - Atualizar estoque
- `delete(Long)` - Deletar (soft)
- `countActive()` - Contar ativos

---

### **4. ClienteService**
**Responsabilidade:** Gerenciamento de clientes
- CRUD completo de clientes
- Validação de CPF/CNPJ
- Busca por documento
- Soft delete

**Métodos:**
- `findAll()` - Listar todos
- `findAllActive()` - Listar ativos
- `findById(Long)` - Buscar por ID
- `findByCpf(String)` - Buscar por CPF
- `findByCnpj(String)` - Buscar por CNPJ
- `searchByName(String)` - Buscar por nome
- `create(ClienteDTO)` - Criar
- `update(Long, ClienteDTO)` - Atualizar
- `delete(Long)` - Deletar (soft)
- `countActive()` - Contar ativos

---

### **5. VendaService**
**Responsabilidade:** Gerenciamento de vendas
- Criar vendas com itens e pagamentos
- Controle de estoque automático
- Validação de caixa aberto
- Cancelamento de vendas
- Relatórios de vendas

**Métodos:**
- `findAll()` - Listar todas
- `findById(Long)` - Buscar por ID
- `findVendasDoDia()` - Vendas do dia
- `findByCliente(Long)` - Vendas por cliente
- `findByPeriodo(LocalDateTime, LocalDateTime)` - Vendas por período
- `create(CriarVendaRequest, Long)` - Criar venda
- `cancelar(Long)` - Cancelar venda
- `calcularTotalVendasDia()` - Total de vendas do dia
- `countVendasDia()` - Quantidade de vendas do dia

---

### **6. DashboardService**
**Responsabilidade:** Métricas e estatísticas
- Consolidar dados do dashboard
- Cálculos de totais
- Contadores de registros

**Métodos:**
- `getMetricas()` - Obter todas as métricas

---

## 🔧 PADRÕES UTILIZADOS

### **1. Injeção de Dependências:**
```java
@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
}
```

### **2. Transações:**
```java
@Transactional
public ProdutoDTO create(ProdutoDTO dto) {
    // Operações transacionais
}
```

### **3. Validações:**
```java
// Verificar duplicados
if (repository.existsBySku(sku)) {
    throw new RuntimeException("SKU já existe");
}

// Verificar existência
Entity entity = repository.findById(id)
    .orElseThrow(() -> new RuntimeException("Não encontrado"));
```

### **4. Conversão DTO ↔ Entity:**
```java
// Entity para DTO
return ProdutoDTO.fromEntity(produto);

// DTO para Entity
Produto produto = dto.toEntity();
```

---

## 📝 REGRAS DE NEGÓCIO IMPLEMENTADAS

### **Produtos:**
- ✅ SKU e Barcode únicos
- ✅ Categoria obrigatória
- ✅ Estoque não pode ser negativo
- ✅ Soft delete (não deleta fisicamente)

### **Clientes:**
- ✅ CPF e CNPJ únicos
- ✅ Validação de documentos
- ✅ Soft delete

### **Vendas:**
- ✅ Caixa deve estar aberto
- ✅ Verificação de estoque
- ✅ Atualização automática de estoque
- ✅ Cálculo automático de totais
- ✅ Validação de pagamento completo
- ✅ Devolução de estoque ao cancelar

### **Autenticação:**
- ✅ Senha criptografada (BCrypt)
- ✅ Token JWT com expiração
- ✅ Usuário deve estar ativo
- ✅ Atualização de último login

---

## 🚀 PRÓXIMOS SERVICES

- **CaixaService** - Abertura/fechamento de caixa
- **ContaReceberService** - Contas a receber
- **ContaPagarService** - Contas a pagar
- **MovimentacaoEstoqueService** - Movimentações de estoque
- **NotificacaoService** - Notificações
- **RelatorioService** - Relatórios

---

## 📚 BOAS PRÁTICAS

1. **Services devem ser stateless**
2. **Usar @Transactional para operações de escrita**
3. **Validar dados antes de persistir**
4. **Lançar exceções descritivas**
5. **Não expor entities diretamente**
6. **Usar DTOs para entrada e saída**
7. **Manter métodos pequenos e focados**
8. **Documentar regras de negócio complexas**

---

## 🎯 TRATAMENTO DE ERROS

### **Exceções Comuns:**
```java
// Não encontrado
throw new RuntimeException("Produto não encontrado");

// Duplicado
throw new RuntimeException("SKU já existe");

// Regra de negócio
throw new RuntimeException("Estoque insuficiente");

// Validação
throw new RuntimeException("Caixa não está aberto");
```

**Nota:** Posteriormente, criar exceções customizadas:
- `NotFoundException`
- `DuplicateException`
- `BusinessException`
- `ValidationException`

---

**✅ 6 SERVICES CRIADOS COM SUCESSO!**
