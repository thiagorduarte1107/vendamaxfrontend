# 🚀 VendaMax - Sistema de Gestão Comercial

Sistema completo de gestão comercial com Angular 17 + Spring Boot 3.2 + SQL Server 2022.

---

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Instalação e Configuração](#instalação-e-configuração)
- [Credenciais de Acesso](#credenciais-de-acesso)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Funcionalidades](#funcionalidades)
- [Documentação da API](#documentação-da-api)

---

## 📖 Sobre o Projeto

**VendaMax** é um sistema completo de gestão comercial que oferece:

- 📊 Dashboard com métricas em tempo real
- 🛍️ Gestão de produtos e categorias
- 👥 Cadastro de clientes
- 💰 Controle de vendas e PDV
- 📦 Gestão de estoque
- 💳 Controle financeiro
- 👤 Gerenciamento de usuários e permissões
- 📈 Relatórios e análises
- 🔐 Autenticação JWT com controle de acesso

---

## 🛠️ Tecnologias

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** (JWT)
- **Spring Data JPA** (Hibernate)
- **SQL Server 2022**
- **Maven**
- **Swagger/OpenAPI 3.0**

### Frontend
- **Angular 17**
- **TypeScript**
- **Angular Material**
- **RxJS**
- **Chart.js**
- **jsPDF**

---

## ✅ Pré-requisitos

- **Java JDK 17+**
- **Node.js 18+** e **npm**
- **SQL Server 2022** (ou SQL Server Express)
- **Maven 3.8+**
- **Git**

---

## 🚀 Instalação e Configuração

### 1️⃣ Clonar o Repositório

```bash
git clone <url-do-repositorio>
cd anota-z
```

### 2️⃣ Configurar o Banco de Dados

#### Criar o banco de dados:

```bash
cd database
sqlcmd -S localhost -U sa -P <sua-senha> -i create-database-sqlserver.sql
```

#### Configurar credenciais no backend:

Edite o arquivo `Backend/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=vendamax;encrypt=false
    username: vendamax_user
    password: VendaMax2024
```

### 3️⃣ Iniciar o Backend

```bash
cd Backend
mvnw clean install
mvnw spring-boot:run
```

O backend estará disponível em: **http://localhost:8080/api**

### 4️⃣ Iniciar o Frontend

```bash
cd Frontend
npm install
npm start
```

O frontend estará disponível em: **http://localhost:4200**

---

## 🔐 Credenciais de Acesso

### 👤 Usuário de Teste

Após iniciar a aplicação, use as seguintes credenciais para fazer login:

| Campo | Valor |
|-------|-------|
| **Email** | `teste@vendamax.com` |
| **Senha** | `password` |
| **Perfil** | ADMIN (acesso total) |

### 🔧 Criar Novo Usuário Admin

Para criar um novo usuário administrador, execute:

```bash
cd Backend
sqlcmd -S localhost -U vendamax_user -P VendaMax2024 -d vendamax -i criar-usuario-teste.sql
```

---

## 📁 Estrutura do Projeto

```
anota-z/
├── Backend/                    # API Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/vendamax/
│   │   │   │   ├── config/           # Configurações (Security, CORS, JWT)
│   │   │   │   ├── controller/       # Endpoints REST (Resources)
│   │   │   │   ├── dto/              # DTOs (Request/Response)
│   │   │   │   ├── entity/           # Entidades JPA
│   │   │   │   ├── exception/        # Tratamento de exceções
│   │   │   │   ├── repository/       # Repositórios JPA
│   │   │   │   ├── security/         # Filtros e configurações de segurança
│   │   │   │   └── service/          # Lógica de negócio
│   │   │   └── resources/
│   │   │       └── application.yml   # Configurações da aplicação
│   │   └── test/                     # Testes unitários
│   ├── pom.xml                       # Dependências Maven
│   └── README.md
│
├── Frontend/                   # Aplicação Angular
│   ├── src/
│   │   ├── app/
│   │   │   ├── components/           # Componentes da UI
│   │   │   ├── guards/               # Guards de rota
│   │   │   ├── interceptors/         # HTTP Interceptors
│   │   │   ├── models/               # Interfaces TypeScript
│   │   │   ├── pages/                # Páginas principais
│   │   │   └── services/             # Serviços Angular
│   │   ├── assets/                   # Recursos estáticos
│   │   └── environments/             # Configurações de ambiente
│   ├── angular.json
│   ├── package.json
│   └── README.md
│
├── database/                   # Scripts SQL
│   ├── create-database-sqlserver.sql
│   └── rename-tables-to-portuguese.sql
│
├── CONECTAR-FRONTEND-BACKEND.md      # Guia de conexão
└── README.md                         # Este arquivo
```

---

## 🎯 Funcionalidades

### 📊 Dashboard
- Visão geral de vendas, produtos e clientes
- Gráficos de desempenho
- Métricas em tempo real

### 🛍️ Produtos
- Cadastro completo de produtos
- Gestão de categorias
- Controle de estoque
- Preços e margens

### 👥 Clientes
- Cadastro de clientes (PF/PJ)
- Histórico de compras
- Limite de crédito
- Endereços múltiplos

### 💰 Vendas
- PDV (Ponto de Venda) completo
- Múltiplas formas de pagamento
- Desconto e acréscimo
- Impressão de cupom

### 📦 Estoque
- Controle de entrada/saída
- Movimentações
- Inventário
- Alertas de estoque baixo

### 💳 Financeiro
- Contas a pagar/receber
- Fluxo de caixa
- Relatórios financeiros

### 👤 Usuários
- Gestão de usuários
- Perfis de acesso (ADMIN, MANAGER, SELLER, CASHIER)
- Permissões granulares
- Logs de atividade

### 📈 Relatórios
- Relatórios de vendas
- Relatórios de produtos
- Relatórios financeiros
- Exportação em PDF

---

## 📚 Documentação da API

### Swagger UI

Acesse a documentação interativa da API em:

**http://localhost:8080/api/swagger-ui.html**

### Endpoints Principais

#### Autenticação
```
POST /api/auth/login          # Login
GET  /api/auth/validate       # Validar token
```

#### Produtos
```
GET    /api/produtos          # Listar produtos
POST   /api/produtos          # Criar produto
GET    /api/produtos/{id}     # Buscar produto
PUT    /api/produtos/{id}     # Atualizar produto
DELETE /api/produtos/{id}     # Deletar produto
```

#### Clientes
```
GET    /api/clientes          # Listar clientes
POST   /api/clientes          # Criar cliente
GET    /api/clientes/{id}     # Buscar cliente
PUT    /api/clientes/{id}     # Atualizar cliente
DELETE /api/clientes/{id}     # Deletar cliente
```

#### Vendas
```
GET    /api/vendas            # Listar vendas
POST   /api/vendas            # Criar venda
GET    /api/vendas/{id}       # Buscar venda
PUT    /api/vendas/{id}       # Atualizar venda
DELETE /api/vendas/{id}       # Cancelar venda
```

#### Dashboard
```
GET /api/dashboard/metricas   # Métricas gerais
```

---

## 🔒 Segurança

- **Autenticação JWT** com tokens de 24 horas
- **CORS** configurado para desenvolvimento
- **Senhas criptografadas** com BCrypt
- **Controle de acesso** por perfil e permissões
- **Validação de dados** em todas as requisições

---

## 🌐 URLs da Aplicação

| Serviço | URL | Descrição |
|---------|-----|-----------|
| **Frontend** | http://localhost:4200 | Interface do usuário |
| **Backend API** | http://localhost:8080/api | API REST |
| **Swagger UI** | http://localhost:8080/api/swagger-ui.html | Documentação interativa |

---

## 📝 Scripts Úteis

### Backend

```bash
# Compilar
mvnw clean install

# Executar
mvnw spring-boot:run

# Executar testes
mvnw test

# Criar JAR
mvnw package
```

### Frontend

```bash
# Instalar dependências
npm install

# Executar em desenvolvimento
npm start

# Build para produção
npm run build

# Executar testes
npm test

# Lint
npm run lint
```

### Banco de Dados

```bash
# Criar banco de dados
sqlcmd -S localhost -U vendamax_user -P VendaMax2024 -i database/create-database-sqlserver.sql

# Criar usuário de teste
sqlcmd -S localhost -U vendamax_user -P VendaMax2024 -d vendamax -i Backend/criar-usuario-teste.sql

# Limpar tabela de permissões
sqlcmd -S localhost -U vendamax_user -P VendaMax2024 -d vendamax -i Backend/limpar-permissoes.sql
```

---

## 🐛 Solução de Problemas

### Backend não inicia

1. Verifique se o SQL Server está rodando
2. Confirme as credenciais no `application.yml`
3. Verifique se a porta 8080 está livre

### Frontend não conecta ao backend

1. Verifique se o backend está rodando
2. Confirme a URL da API em `src/environments/environment.ts`
3. Verifique o console do navegador para erros de CORS

### Erro de login

1. Use as credenciais corretas: `teste@vendamax.com` / `password`
2. Verifique se o usuário existe no banco de dados
3. Confirme que o backend está processando a requisição

---

## 📄 Licença

Este projeto é proprietário e confidencial.

---

## 👨‍💻 Desenvolvido por

**VendaMax Team**

---

## 📞 Suporte

Para dúvidas ou problemas, consulte a documentação ou entre em contato com a equipe de desenvolvimento.

---

**🎉 Bem-vindo ao VendaMax! Sistema completo de gestão comercial.**
