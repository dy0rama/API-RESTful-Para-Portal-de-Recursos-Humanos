package com.portal.recursos.humanos.exceptions;

public class OperacaoNaoAutorizadaException extends RuntimeException {
    public OperacaoNaoAutorizadaException(String message) {
        super(message);
    }
}
