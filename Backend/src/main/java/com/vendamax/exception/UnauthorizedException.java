package com.vendamax.exception;

/**
 * Exception: Não autorizado
 */
public class UnauthorizedException extends RuntimeException {
    
    public UnauthorizedException(String message) {
        super(message);
    }
}
