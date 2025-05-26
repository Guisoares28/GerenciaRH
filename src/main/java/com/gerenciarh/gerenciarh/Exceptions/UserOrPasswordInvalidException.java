package com.gerenciarh.gerenciarh.Exceptions;

public class UserOrPasswordInvalidException extends RuntimeException {

    public UserOrPasswordInvalidException() {
        super("Usuário ou senha inválidos");
    }
}
