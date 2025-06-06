package com.gerenciarh.gerenciarh.Exceptions;

public class RepeatDataException extends RuntimeException {

    public RepeatDataException() {
        super("Registro já cadastrado");
    }

    public RepeatDataException(String msg) {
    	super(msg);
    }
}
