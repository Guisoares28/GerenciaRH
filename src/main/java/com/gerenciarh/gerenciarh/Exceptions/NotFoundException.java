package com.gerenciarh.gerenciarh.Exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Registro Solicitado não encontrado");
    }

    public NotFoundException(String message) {
        super(message);
    }

}
