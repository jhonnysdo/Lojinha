package br.com.fiap.challengeecommercegestaoitens.handler;

import br.com.fiap.challengeecommercegestaoitens.exceptions.ItemNotFoundException;
import br.com.fiap.challengeecommercegestaoitens.exceptions.ItemOutOfStockException;
import br.com.fiap.challengeecommercegestaoitens.exceptions.UnauthorizedErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedErrorException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedErrorException(UnauthorizedErrorException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(ItemOutOfStockException.class)
    public ResponseEntity<ErrorResponse> handleItemOutOfStockExceptionn(ItemOutOfStockException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(ItemNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
    }

    public record ErrorResponse(String message) {
    }
}