package com.gerenciarh.gerenciarh.Exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(){
        super("Usuário sem  autorização");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
