package com.gerenciarh.gerenciarh.ExceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gerenciarh.gerenciarh.Exceptions.NotFoundException;
import com.gerenciarh.gerenciarh.Exceptions.RepeatDataException;
import com.gerenciarh.gerenciarh.Exceptions.UnauthorizedException;
import com.gerenciarh.gerenciarh.Exceptions.UserOrPasswordInvalidException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(RepeatDataException.class)
    public ResponseEntity<String> handleRepeatDataException(RepeatDataException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UserOrPasswordInvalidException.class)
    public ResponseEntity<String> handleUserOrPasswordInvalidException(UserOrPasswordInvalidException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage()); 
    }

    

}
