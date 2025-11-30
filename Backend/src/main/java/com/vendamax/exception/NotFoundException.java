package com.vendamax.exception;

/**
 * Exception: Recurso não encontrado
 */
public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String message) {
        super(message);
    }
    
    public NotFoundException(String entity, Long id) {
        super(String.format("%s com ID %d não encontrado", entity, id));
    }
}
