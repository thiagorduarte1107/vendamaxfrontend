# 🔧 CONFIGURAR SQL SERVER - AUTENTICAÇÃO

## ❌ PROBLEMA

O driver JDBC não consegue carregar a DLL para autenticação integrada do Windows.

## ✅ SOLUÇÃO: Habilitar Autenticação SQL Server

### **1. Abrir SQL Server Management Studio (SSMS)**

Ou abra o **SQL Server Configuration Manager**

### **2. Habilitar Modo de Autenticação Mista**

**No SSMS:**
1. Conecte-se ao servidor (com autenticação Windows)
2. Clique com botão direito no servidor → **Properties**
3. Vá em **Security**
4. Selecione **SQL Server and Windows Authentication mode**
5. Clique **OK**

### **3. Habilitar e Configurar Usuário `sa`**

Execute no SSMS:

```sql
-- Habilitar o usuário sa
ALTER LOGIN sa ENABLE;

-- Definir nova senha (use uma senha forte!)
ALTER LOGIN sa WITH PASSWORD = 'SuaSenhaForte123!';

-- Verificar
SELECT name, is_disabled 
FROM sys.sql_logins 
WHERE name = 'sa';
```

### **4. Reiniciar SQL Server**

**Opção 1 - Services:**
1. Pressione `Win + R`
2. Digite `services.msc`
3. Encontre **SQL Server (MSSQLSERVER)**
4. Clique com botão direito → **Restart**

**Opção 2 - PowerShell (Admin):**
```powershell
Restart-Service MSSQLSERVER
```

### **5. Testar Conexão**

```powershell
sqlcmd -S localhost -U sa -P "SuaSenhaForte123!"
```

Se funcionar, você verá:
```
1>
```

### **6. Atualizar application.yml**

```yaml
datasource:
  url: jdbc:sqlserver://localhost:1433;databaseName=vendamax;encrypt=false;trustServerCertificate=true
  username: sa
  password: SuaSenhaForte123!
  driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver
```

---

## 🔐 SENHA RECOMENDADA

Use uma senha forte com:
- Mínimo 8 caracteres
- Letras maiúsculas e minúsculas
- Números
- Caracteres especiais

Exemplo: `VendaMax@2024!`

---

## ⚠️ IMPORTANTE

- **NÃO** comite a senha no Git
- Use variáveis de ambiente em produção
- Mantenha a senha segura

---

## 📝 ALTERNATIVA: Criar Novo Usuário

Se preferir não usar `sa`:

```sql
-- Criar novo usuário
CREATE LOGIN vendamax_user WITH PASSWORD = 'SuaSenha123!';

-- Dar permissões no banco
USE vendamax;
CREATE USER vendamax_user FOR LOGIN vendamax_user;
ALTER ROLE db_owner ADD MEMBER vendamax_user;
```

Depois use no `application.yml`:
```yaml
username: vendamax_user
password: SuaSenha123!
```
