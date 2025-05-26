package com.gerenciarh.gerenciarh.Exceptions;

public class RepeatDataException extends RuntimeException {

    public RepeatDataException() {
        super("Registro já cadastrado");
    }
}
