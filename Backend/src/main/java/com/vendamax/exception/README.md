# 📁 EXCEPTION - TRATAMENTO DE EXCEÇÕES

Esta pasta contém exceções customizadas e o handler global.

---

## 📊 EXCEÇÕES CRIADAS

### **1. NotFoundException**
**Uso:** Recurso não encontrado  
**HTTP Status:** 404 Not Found

```java
throw new NotFoundException("Produto não encontrado");
throw new NotFoundException("Produto", 123L);
```

---

### **2. BusinessException**
**Uso:** Regra de negócio violada  
**HTTP Status:** 400 Bad Request

```java
throw new BusinessException("Estoque insuficiente");
throw new BusinessException("Caixa não está aberto");
```

---

### **3. DuplicateException**
**Uso:** Recurso duplicado  
**HTTP Status:** 409 Conflict

```java
throw new DuplicateException("SKU já existe");
throw new DuplicateException("Produto", "SKU", "ABC123");
```

---

### **4. UnauthorizedException**
**Uso:** Não autorizado  
**HTTP Status:** 401 Unauthorized

```java
throw new UnauthorizedException("Token inválido");
throw new UnauthorizedException("Usuário inativo");
```

---

## 🔧 GLOBAL EXCEPTION HANDLER

### **Tratamento Centralizado:**

O `GlobalExceptionHandler` intercepta todas as exceções e retorna respostas padronizadas:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage()));
    }
}
```

---

## 📝 CÓDIGOS HTTP

| Exceção | Status | Código |
|---------|--------|--------|
| NotFoundException | Not Found | 404 |
| BusinessException | Bad Request | 400 |
| DuplicateException | Conflict | 409 |
| UnauthorizedException | Unauthorized | 401 |
| ValidationException | Bad Request | 400 |
| RuntimeException | Internal Server Error | 500 |

---

## 🎯 EXEMPLO DE RESPOSTA

```json
{
  "success": false,
  "message": "Produto não encontrado",
  "data": null,
  "timestamp": "2025-11-27T08:30:00"
}
```

---

## 📚 BOAS PRÁTICAS

1. **Usar exceções específicas**
2. **Mensagens descritivas**
3. **Não expor stack traces em produção**
4. **Logar erros internos**
5. **Retornar códigos HTTP corretos**

---

**✅ TRATAMENTO DE EXCEÇÕES COMPLETO!**
