package com.davidbrito.api_estoque.exception;

public class RegraNegocioException extends RuntimeException{
    public RegraNegocioException(String mensagem){
        super(mensagem);
    }
}
