# 📁 ENTITY - ENTIDADES JPA

Esta pasta contém todas as entidades JPA mapeadas para as tabelas do banco de dados.

---

## 📊 ENTIDADES CRIADAS (17)

### **1. Usuario.java**
- Tabela: `usuarios`
- Usuários do sistema
- Relacionamento: ManyToMany com Permissao
- Enum: UserRole (ADMIN, MANAGER, CASHIER, USER)

### **2. Permissao.java**
- Tabela: `permissoes`
- Permissões do sistema
- Relacionamento: ManyToMany com Usuario

### **3. Categoria.java**
- Tabela: `categorias`
- Categorias de produtos
- Relacionamento: OneToMany com Produto

### **4. Produto.java**
- Tabela: `produtos`
- Produtos do sistema
- Relacionamento: ManyToOne com Categoria

### **5. Cliente.java**
- Tabela: `clientes`
- Clientes do sistema
- Campos: CPF, CNPJ, endereço completo

### **6. Caixa.java**
- Tabela: `caixas`
- Caixas (abertura/fechamento)
- Relacionamento: ManyToOne com Usuario
- Enum: CaixaStatus (OPEN, CLOSED)

### **7. MovimentacaoCaixa.java**
- Tabela: `movimentacoes_caixa`
- Movimentações de caixa
- Relacionamento: ManyToOne com Caixa
- Enum: TipoMovimentacao (ENTRADA, SAIDA, SANGRIA, REFORCO)

### **8. Venda.java**
- Tabela: `vendas`
- Vendas realizadas
- Relacionamentos:
  - ManyToOne: Cliente, Usuario, Caixa
  - OneToMany: ItemVenda, Pagamento
- Enum: VendaStatus (PENDING, COMPLETED, CANCELLED)

### **9. ItemVenda.java**
- Tabela: `itens_venda`
- Itens de cada venda
- Relacionamento: ManyToOne com Venda e Produto

### **10. Pagamento.java**
- Tabela: `pagamentos`
- Pagamentos das vendas
- Relacionamento: ManyToOne com Venda
- Enum: MetodoPagamento (DINHEIRO, CARTAO_CREDITO, CARTAO_DEBITO, PIX, BOLETO, TRANSFERENCIA)

### **11. MovimentacaoEstoque.java**
- Tabela: `movimentacoes_estoque`
- Movimentações de estoque
- Relacionamento: ManyToOne com Produto e Usuario
- Enum: TipoMovimentacao (ENTRADA, SAIDA, AJUSTE, VENDA, DEVOLUCAO)

### **12. ContaReceber.java**
- Tabela: `contas_receber`
- Contas a receber
- Relacionamento: ManyToOne com Cliente e Venda
- Enum: StatusConta (PENDING, PAID, OVERDUE, CANCELLED)

### **13. ContaPagar.java**
- Tabela: `contas_pagar`
- Contas a pagar
- Enum: StatusConta (PENDING, PAID, OVERDUE, CANCELLED)

### **14. LogAtividade.java**
- Tabela: `logs_atividade`
- Logs de atividades do sistema
- Relacionamento: ManyToOne com Usuario

### **15. Notificacao.java**
- Tabela: `notificacoes`
- Notificações do sistema
- Relacionamento: ManyToOne com Usuario
- Enum: TipoNotificacao (INFO, WARNING, ERROR, SUCCESS)

### **16. Backup.java**
- Tabela: `backups`
- Controle de backups
- Enum: BackupStatus (SUCCESS, FAILED, IN_PROGRESS)

---

## 🔧 ANOTAÇÕES UTILIZADAS

### **JPA:**
- `@Entity` - Define a classe como entidade
- `@Table` - Mapeia para tabela do banco
- `@Id` - Define chave primária
- `@GeneratedValue` - Auto incremento
- `@Column` - Mapeia coluna
- `@ManyToOne` - Relacionamento N:1
- `@OneToMany` - Relacionamento 1:N
- `@ManyToMany` - Relacionamento N:N
- `@JoinColumn` - Define FK
- `@JoinTable` - Tabela intermediária
- `@Enumerated` - Mapeia enum
- `@PrePersist` - Antes de inserir
- `@PreUpdate` - Antes de atualizar

### **Lombok:**
- `@Data` - Getters, setters, toString, equals, hashCode
- `@NoArgsConstructor` - Construtor vazio
- `@AllArgsConstructor` - Construtor com todos os campos

---

## 📝 PADRÕES UTILIZADOS

### **Nomenclatura:**
- Classes em PascalCase (ex: Usuario, Produto)
- Atributos em camelCase (ex: createdAt, totalAmount)
- Tabelas em snake_case (ex: usuarios, contas_receber)

### **Timestamps:**
- `createdAt` - Data de criação (não atualiza)
- `updatedAt` - Data de atualização (atualiza sempre)
- `@PrePersist` - Define createdAt e updatedAt
- `@PreUpdate` - Atualiza updatedAt

### **Soft Delete:**
- Campo `active` (Boolean) em várias entidades
- Não deleta fisicamente, apenas marca como inativo

---

## 🎯 PRÓXIMOS PASSOS

1. ✅ Entities criadas
2. ⏳ Criar Repositories
3. ⏳ Criar DTOs
4. ⏳ Criar Services
5. ⏳ Criar Controllers

---

**✅ 17 ENTIDADES JPA CRIADAS COM SUCESSO!**
