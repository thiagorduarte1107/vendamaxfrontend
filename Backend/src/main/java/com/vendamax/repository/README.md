# 📁 REPOSITORY - REPOSITÓRIOS JPA

Esta pasta contém todos os repositórios Spring Data JPA para acesso aos dados.

---

## 📊 REPOSITORIES CRIADOS (17)

### **1. UsuarioRepository**
- Buscar por email
- Verificar se email existe
- Buscar usuários ativos
- Buscar por role
- Contar usuários ativos

### **2. PermissaoRepository**
- Buscar por nome
- Buscar permissões ativas
- Verificar se existe

### **3. CategoriaRepository**
- Buscar por nome
- Buscar categorias ativas
- Contar produtos por categoria

### **4. ProdutoRepository**
- Buscar por SKU/Barcode
- Buscar produtos ativos
- Buscar por categoria
- Buscar com estoque baixo
- Buscar sem estoque
- Contar produtos ativos

### **5. ClienteRepository**
- Buscar por CPF/CNPJ/Email
- Buscar clientes ativos
- Buscar por cidade
- Verificar duplicados
- Contar clientes ativos

### **6. CaixaRepository**
- Buscar caixa aberto do usuário
- Buscar por status
- Buscar por período
- Verificar se tem caixa aberto
- Buscar último caixa

### **7. MovimentacaoCaixaRepository**
- Buscar por caixa
- Buscar por tipo
- Calcular total de entradas
- Calcular total de saídas

### **8. VendaRepository**
- Buscar por cliente/usuário/caixa
- Buscar por status
- Buscar por período
- Buscar vendas do dia
- Calcular totais
- Contar vendas

### **9. ItemVendaRepository**
- Buscar por venda
- Buscar por produto
- Buscar produtos mais vendidos
- Contar itens

### **10. PagamentoRepository**
- Buscar por venda
- Buscar por método
- Buscar por período
- Calcular total por método
- Contar pagamentos

### **11. MovimentacaoEstoqueRepository**
- Buscar por produto/usuário
- Buscar por tipo
- Buscar por período
- Buscar últimas movimentações

### **12. ContaReceberRepository**
- Buscar por cliente/status
- Buscar contas vencidas
- Buscar contas a vencer
- Calcular totais
- Contar por status

### **13. ContaPagarRepository**
- Buscar por fornecedor/status
- Buscar contas vencidas
- Buscar contas a vencer
- Calcular totais
- Contar por status

### **14. LogAtividadeRepository**
- Buscar por usuário/ação/entidade
- Buscar por período
- Buscar logs do dia
- Buscar últimos logs

### **15. NotificacaoRepository**
- Buscar por usuário
- Buscar não lidas
- Buscar por tipo
- Contar não lidas
- Buscar últimas notificações

### **16. BackupRepository**
- Buscar por status
- Buscar por período
- Buscar último backup
- Contar por status

---

## 🔧 MÉTODOS PADRÃO (JpaRepository)

Todos os repositories herdam de `JpaRepository` e possuem:

### **CRUD Básico:**
- `save(entity)` - Salvar/Atualizar
- `findById(id)` - Buscar por ID
- `findAll()` - Buscar todos
- `deleteById(id)` - Deletar por ID
- `delete(entity)` - Deletar entidade
- `existsById(id)` - Verificar se existe
- `count()` - Contar registros

### **Paginação:**
- `findAll(Pageable)` - Buscar com paginação
- `findAll(Sort)` - Buscar com ordenação

---

## 📝 CONVENÇÕES DE NOMENCLATURA

### **Query Methods:**
- `findBy...` - Buscar
- `countBy...` - Contar
- `existsBy...` - Verificar existência
- `deleteBy...` - Deletar

### **Operadores:**
- `And` - E lógico
- `Or` - OU lógico
- `Between` - Entre valores
- `LessThan` - Menor que
- `GreaterThan` - Maior que
- `Like` - Contém
- `IgnoreCase` - Ignora maiúsculas/minúsculas
- `OrderBy...Desc` - Ordenar descendente
- `OrderBy...Asc` - Ordenar ascendente

### **Exemplos:**
```java
// Buscar por nome contendo (case insensitive)
findByNameContainingIgnoreCase(String name)

// Buscar ativos ordenados por data
findByActiveTrueOrderByCreatedAtDesc()

// Buscar entre datas
findByCreatedAtBetween(LocalDateTime inicio, LocalDateTime fim)
```

---

## 🎯 QUERIES CUSTOMIZADAS

### **@Query JPQL:**
```java
@Query("SELECT u FROM Usuario u WHERE u.active = true")
List<Usuario> findActiveUsers();
```

### **@Query SQL Nativo:**
```java
@Query(value = "SELECT * FROM usuarios WHERE active = 1", nativeQuery = true)
List<Usuario> findActiveUsersNative();
```

### **Agregações:**
```java
@Query("SELECT COUNT(u) FROM Usuario u WHERE u.active = true")
long countActiveUsers();

@Query("SELECT COALESCE(SUM(v.finalAmount), 0) FROM Venda v")
BigDecimal calcularTotalVendas();
```

---

## 🚀 PRÓXIMOS PASSOS

1. ✅ Repositories criados
2. ⏳ Criar DTOs
3. ⏳ Criar Services
4. ⏳ Criar Controllers

---

**✅ 17 REPOSITORIES CRIADOS COM SUCESSO!**
