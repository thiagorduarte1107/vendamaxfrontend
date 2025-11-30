# 🔗 Guia de Conexão Frontend + Backend

## ✅ Configurações Realizadas

### **Backend (Spring Boot)**
- ✅ API rodando em `http://localhost:8080/api`
- ✅ CORS configurado para aceitar requisições de `http://localhost:4200`
- ✅ Endpoints REST prontos
- ✅ Swagger UI disponível em `http://localhost:8080/api/swagger-ui.html`

### **Frontend (Angular)**
- ✅ Environment atualizado para `http://localhost:8080/api`
- ✅ AuthService conectado ao backend
- ✅ Interceptor HTTP criado para adicionar token JWT
- ✅ Mapeamento de perfis (ADMIN, GERENTE, VENDEDOR, CAIXA)

---

## 🚀 Como Iniciar

### **1. Iniciar o Backend**
```bash
cd f:\projetos\anota-z\backend
mvnw spring-boot:run
```

**Aguarde até ver:**
```
🚀 VENDAMAX API INICIADA! 🚀
```

### **2. Iniciar o Frontend**
```bash
cd f:\projetos\anota-z\Frontend
npm install  # Apenas na primeira vez
npm start
```

**O Angular vai abrir em:** `http://localhost:4200`

---

## 🔐 Credenciais de Acesso

### **Usuário de Teste Pré-Configurado**

Use as seguintes credenciais para fazer login:

| Campo | Valor |
|-------|-------|
| **Email** | `teste@vendamax.com` |
| **Senha** | `password` |
| **Perfil** | ADMIN (acesso total) |

### **Fazer Login no Frontend**

1. Acesse `http://localhost:4200`
2. Faça login com:
   - **Email**: `teste@vendamax.com`
   - **Senha**: `password`

### **Criar Novo Usuário (Opcional)**

Se precisar criar um novo usuário, execute o script SQL:

```bash
cd f:\projetos\anota-z\backend
sqlcmd -S localhost -U vendamax_user -P "VendaMax2024" -d vendamax -i criar-usuario-teste.sql
```

---

## 📡 Endpoints Principais

### **Autenticação**
- `POST /auth/register` - Registrar usuário
- `POST /auth/login` - Fazer login
- `GET /auth/me` - Obter usuário atual

### **Produtos**
- `GET /produtos` - Listar produtos
- `POST /produtos` - Criar produto
- `PUT /produtos/{id}` - Atualizar produto
- `DELETE /produtos/{id}` - Deletar produto

### **Clientes**
- `GET /clientes` - Listar clientes
- `POST /clientes` - Criar cliente
- `PUT /clientes/{id}` - Atualizar cliente
- `DELETE /clientes/{id}` - Deletar cliente

### **Vendas**
- `GET /vendas` - Listar vendas
- `POST /vendas` - Criar venda
- `GET /vendas/{id}` - Obter venda

### **Dashboard**
- `GET /dashboard/metricas` - Obter métricas do dashboard

---

## 🔧 Arquivos Modificados

### **Frontend**
- ✅ `src/environments/environment.ts` - URL da API atualizada
- ✅ `src/environments/environment.prod.ts` - Nome da aplicação atualizado
- ✅ `src/app/services/auth.service.ts` - Conectado ao backend real
- ✅ `src/app/interceptors/auth.interceptor.ts` - Criado para adicionar JWT
- ✅ `src/app/app.module.ts` - HttpClient e interceptor configurados

### **Backend**
- ✅ `src/main/java/com/vendamax/config/CorsConfig.java` - Já configurado
- ✅ `src/main/resources/application.yml` - CORS habilitado

---

## 🐛 Troubleshooting

### **Erro de CORS**
Se aparecer erro de CORS no console do navegador:
1. Verifique se o backend está rodando
2. Confirme que o frontend está em `http://localhost:4200`
3. Limpe o cache do navegador (Ctrl + Shift + Del)

### **Erro 401 Unauthorized**
- Verifique se o token está sendo enviado (veja no Network do DevTools)
- Faça logout e login novamente
- Limpe o localStorage: `localStorage.clear()`

### **Backend não conecta**
- Verifique se o SQL Server está rodando
- Teste a conexão: `sqlcmd -S localhost -U vendamax_user -P "VendaMax2024" -d vendamax`

---

## 📝 Próximos Passos

1. ✅ **Testar Login** - Criar usuário e fazer login
2. ⏳ **Atualizar outros serviços** - Conectar ProductService, ClientService, etc.
3. ⏳ **Adicionar tratamento de erros** - Interceptor para erros HTTP
4. ⏳ **Implementar refresh token** - Para renovar token expirado

---

## 🎯 Status da Integração

| Serviço | Status | Observações |
|---------|--------|-------------|
| AuthService | ✅ Conectado | Login funcionando |
| ProductService | ⏳ Pendente | Precisa atualizar |
| ClientService | ⏳ Pendente | Precisa atualizar |
| SaleService | ⏳ Pendente | Precisa atualizar |
| DashboardService | ⏳ Pendente | Precisa atualizar |
| CategoryService | ⏳ Pendente | Precisa atualizar |

---

**🎉 Pronto! Seu frontend está conectado ao backend!**
