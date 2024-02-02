package br.com.fiap.challengeecommercecarrinhodecompras.handler;

import br.com.fiap.challengeecommercecarrinhodecompras.exceptions.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(ItemNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        String mensagemErro = String.format("O parâmetro requerido '%s' está ausente.", ex.getParameterName());
        return new ResponseEntity<>(mensagemErro, HttpStatus.BAD_REQUEST);
    }

    public record ErrorResponse(String message) {
    }
}