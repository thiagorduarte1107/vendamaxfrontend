# 🗄️ Scripts SQL - VendaMax

Scripts SQL para configuração e manutenção do banco de dados SQL Server.

---

## 📋 **Índice**

### **🏗️ Criação e Configuração**
- [**create-database-sqlserver.sql**](create-database-sqlserver.sql) - Criar banco de dados e tabelas
- [**rename-tables-to-portuguese.sql**](rename-tables-to-portuguese.sql) - Renomear tabelas para português

### **👤 Usuários e Permissões**
- [**criar-usuario-admin.sql**](criar-usuario-admin.sql) - Criar usuário administrador
- [**criar-usuario-teste.sql**](criar-usuario-teste.sql) - Criar usuário de teste
- [**habilitar-autenticacao-sql.sql**](habilitar-autenticacao-sql.sql) - Habilitar autenticação SQL Server
- [**limpar-permissoes.sql**](limpar-permissoes.sql) - Limpar permissões de usuários

### **📊 Dados de Teste**
- [**insert-test-data.sql**](insert-test-data.sql) - Inserir dados de teste (produtos, categorias, clientes)
- [**insert-caixa.sql**](insert-caixa.sql) - Inserir dados de caixa
- [**insert-vendas-hoje.sql**](insert-vendas-hoje.sql) - Inserir vendas de hoje para testes

### **🔧 Manutenção**
- [**fix-trigger-produtos.sql**](fix-trigger-produtos.sql) - Corrigir trigger de produtos

---

## 🚀 **Ordem de Execução (Setup Inicial)**

Para configurar o banco de dados do zero:

```sql
-- 1. Criar banco e tabelas
execute: create-database-sqlserver.sql

-- 2. Criar usuário admin
execute: criar-usuario-admin.sql

-- 3. Habilitar autenticação SQL
execute: habilitar-autenticacao-sql.sql

-- 4. Inserir dados de teste
execute: insert-test-data.sql
execute: insert-caixa.sql

-- 5. (Opcional) Inserir vendas de teste
execute: insert-vendas-hoje.sql
```

---

## 📝 **Credenciais Padrão**

### **Usuário Admin**
- **Username:** `admin`
- **Password:** `Admin@123`
- **Perfil:** ADMIN

### **Usuário Teste**
- **Username:** `teste`
- **Password:** `Teste@123`
- **Perfil:** VENDEDOR

### **SQL Server**
- **Username:** `vendamax_user`
- **Password:** `VendaMax2024`
- **Database:** `vendamax`

---

## ⚠️ **IMPORTANTE**

- ⚠️ **NÃO use essas credenciais em produção!**
- ⚠️ Altere as senhas antes de fazer deploy
- ⚠️ Use variáveis de ambiente para credenciais sensíveis

---

## 🔄 **Última Atualização**

**Data:** 30/11/2025  
**Versão:** 1.0.0
