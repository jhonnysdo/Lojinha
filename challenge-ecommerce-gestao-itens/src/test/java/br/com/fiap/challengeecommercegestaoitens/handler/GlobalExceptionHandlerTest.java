package br.com.fiap.challengeecommercegestaoitens.handler;

import br.com.fiap.challengeecommercegestaoitens.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    @Test
    public void testHandleItemNotFoundException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        br.com.fiap.challengeecommercegestaoitens.exceptions.ItemNotFoundException exception = new br.com.fiap.challengeecommercegestaoitens.exceptions.ItemNotFoundException(1L);

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> responseEntity = handler.handleItemNotFoundException (exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Item não encontrado", responseEntity.getBody().message());
    }

    // Classe auxiliar simulando a ItemNotFoundException
    private static class ItemNotFoundException extends RuntimeException {
        public ItemNotFoundException(String message) {
            super(message);
        }
    }
}
