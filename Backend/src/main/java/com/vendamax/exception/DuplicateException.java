package com.vendamax.exception;

/**
 * Exception: Recurso duplicado
 */
public class DuplicateException extends RuntimeException {
    
    public DuplicateException(String message) {
        super(message);
    }
    
    public DuplicateException(String entity, String field, String value) {
        super(String.format("%s com %s '%s' já existe", entity, field, value));
    }
}
