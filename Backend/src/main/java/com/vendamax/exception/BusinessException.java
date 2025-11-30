package com.vendamax.exception;

/**
 * Exception: Regra de negócio violada
 */
public class BusinessException extends RuntimeException {
    
    public BusinessException(String message) {
        super(message);
    }
}
