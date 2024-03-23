package br.com.fiap.challengeecommercelogin.handlers;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.http.ResponseEntity;

import br.com.fiap.challengeecommercelogin.exceptions.ErrorResponse;
import br.com.fiap.challengeecommercelogin.exceptions.UserAlreadyExistsException;

public class CustomExceptionHandlerTest {

    @Test
    public void testHandleUserAlreadyExistsException() {
        CustomExceptionHandler handler = new CustomExceptionHandler();
        var exception = new UserAlreadyExistsException("Usuário já existe");

        ResponseEntity<ErrorResponse> responseEntity = handler.handleUserAlreadyExistsException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Usuário já existe", responseEntity.getBody().getMessage());
    }

    @Test
    public void testHandleExpiredJwtException() {
        CustomExceptionHandler handler = new CustomExceptionHandler();
        ExpiredJwtException exception = new ExpiredJwtException(null, null, "Token de autenticação expirado");

        ResponseEntity<ErrorResponse> responseEntity = handler.handleExpiredJwtException(exception);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("Token de autenticação expirado", responseEntity.getBody().getMessage());
    }
}
