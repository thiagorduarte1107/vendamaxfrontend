# 🚀 VENDAMAX API - BACKEND

Sistema de Gestão Comercial - API REST com Spring Boot

---

## 📋 TECNOLOGIAS

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security + JWT**
- **SQL Server 2022**
- **Swagger/OpenAPI**
- **Lombok**
- **Maven**

---

## 🗄️ BANCO DE DADOS

### **Tabelas (17):**
- categorias
- produtos
- clientes
- usuarios
- permissoes
- usuario_permissoes
- caixas
- movimentacoes_caixa
- vendas
- itens_venda
- pagamentos
- movimentacoes_estoque
- contas_receber
- contas_pagar
- logs_atividade
- notificacoes
- backups

### **Views (4):**
- vw_produtos_estoque_baixo
- vw_vendas_dia
- vw_contas_vencidas
- vw_metricas_dashboard

---

## 🚀 COMO EXECUTAR

### **1. Pré-requisitos:**
- Java 17 instalado
- Maven instalado
- SQL Server rodando
- Banco `vendamax` criado

### **2. Configurar application.yml:**
```yaml
spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=vendamax;integratedSecurity=true
```

### **3. Executar:**
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### **4. Acessar:**
- **API:** http://localhost:8080/api
- **Swagger:** http://localhost:8080/api/swagger-ui.html

---

## 📁 ESTRUTURA DO PROJETO

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/vendamax/
│   │   │   ├── VendaMaxApplication.java
│   │   │   ├── config/          # Configurações
│   │   │   ├── controller/      # Controllers REST
│   │   │   ├── dto/              # DTOs
│   │   │   ├── entity/           # Entities JPA
│   │   │   ├── repository/       # Repositories
│   │   │   ├── service/          # Services
│   │   │   ├── security/         # Security & JWT
│   │   │   └── exception/        # Exception Handlers
│   │   └── resources/
│   │       ├── application.yml
│   │       └── application-dev.yml
│   └── test/                     # Testes
├── pom.xml
└── README.md
```

---

## 🔐 AUTENTICAÇÃO

### **Login:**
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@vendamax.com",
  "password": "admin123"
}
```

### **Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "expiresIn": 86400000
}
```

---

## 📚 ENDPOINTS PRINCIPAIS

### **Autenticação:**
- `POST /api/auth/login` - Login
- `POST /api/auth/refresh` - Refresh token

### **Produtos:**
- `GET /api/produtos` - Listar todos
- `GET /api/produtos/{id}` - Buscar por ID
- `POST /api/produtos` - Criar
- `PUT /api/produtos/{id}` - Atualizar
- `DELETE /api/produtos/{id}` - Excluir

### **Categorias:**
- `GET /api/categorias` - Listar todas
- `POST /api/categorias` - Criar
- `PUT /api/categorias/{id}` - Atualizar
- `DELETE /api/categorias/{id}` - Excluir

### **Clientes:**
- `GET /api/clientes` - Listar todos
- `POST /api/clientes` - Criar
- `PUT /api/clientes/{id}` - Atualizar
- `DELETE /api/clientes/{id}` - Excluir

### **Vendas:**
- `GET /api/vendas` - Listar todas
- `POST /api/vendas` - Criar venda
- `GET /api/vendas/{id}` - Buscar por ID
- `PUT /api/vendas/{id}/cancelar` - Cancelar venda

### **Dashboard:**
- `GET /api/dashboard/metricas` - Métricas principais
- `GET /api/dashboard/vendas-dia` - Vendas do dia
- `GET /api/dashboard/estoque-baixo` - Produtos com estoque baixo

---

## 🧪 TESTES

```bash
mvn test
```

---

## 📦 BUILD

```bash
mvn clean package
```

O JAR será gerado em: `target/vendamax-api-1.0.0.jar`

---

## 🐳 DOCKER (Futuro)

```bash
docker build -t vendamax-api .
docker run -p 8080:8080 vendamax-api
```

---

## 📝 PRÓXIMOS PASSOS

1. ✅ Projeto Spring Boot criado
2. ⏳ Criar entities JPA
3. ⏳ Criar repositories
4. ⏳ Criar services
5. ⏳ Criar controllers REST
6. ⏳ Implementar autenticação JWT
7. ⏳ Documentar com Swagger
8. ⏳ Criar testes unitários
9. ⏳ Integrar com frontend Angular

---

**🎉 VENDAMAX API - PRONTO PARA DESENVOLVIMENTO!**
