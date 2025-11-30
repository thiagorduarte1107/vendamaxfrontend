# 📁 CONFIG - CONFIGURAÇÕES DO SPRING BOOT

Esta pasta contém todas as configurações do projeto.

---

## 📋 ARQUIVOS DE CONFIGURAÇÃO

### **1. CorsConfig.java**
- Configuração de CORS (Cross-Origin Resource Sharing)
- Permite requisições do frontend Angular (localhost:4200)
- Define métodos HTTP permitidos (GET, POST, PUT, DELETE, etc)
- Configura headers permitidos

### **2. SecurityConfig.java**
- Configuração do Spring Security
- Define endpoints públicos (/auth, /swagger)
- Configura autenticação JWT
- Sessão stateless (sem cookies)
- BCrypt para criptografia de senhas

### **3. JwtConfig.java**
- Propriedades do JWT
- Chave secreta
- Tempo de expiração
- Prefixo do token ("Bearer ")

### **4. SwaggerConfig.java**
- Configuração do Swagger/OpenAPI
- Documentação interativa da API
- Configuração de autenticação JWT no Swagger

### **5. JacksonConfig.java**
- Configuração do Jackson (JSON)
- Formato de datas (ISO 8601)
- Timezone (America/Sao_Paulo)
- Pretty print (indentação)

---

## 🔧 PRÓXIMAS CONFIGURAÇÕES

- **WebMvcConfig** - Configurações do Spring MVC
- **AsyncConfig** - Processamento assíncrono
- **CacheConfig** - Configuração de cache
- **SchedulingConfig** - Tarefas agendadas

---

## 📝 OBSERVAÇÕES

- Todas as configurações usam `@Configuration`
- Propriedades vêm do `application.yml`
- Beans são criados com `@Bean`
- Configurações são carregadas no startup

---

**✅ CONFIGURAÇÕES BÁSICAS COMPLETAS!**
