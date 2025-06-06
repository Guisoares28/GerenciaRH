package com.gerenciarh.gerenciarh.Exceptions;

public class BadRequestException extends RuntimeException{

    public BadRequestException() {
        super("Registro já cadastrado");
    }
	
	public BadRequestException(String msg) {
		super(msg);
	}
}
