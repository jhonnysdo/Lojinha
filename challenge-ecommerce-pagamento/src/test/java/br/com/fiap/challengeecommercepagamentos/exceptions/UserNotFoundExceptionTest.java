package br.com.fiap.challengeecommercepagamentos.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserNotFoundExceptionTest {
    @Test
    public void testErrorMessage() {
        String username = "jack_sparrow";
        UserNotFoundException exception = new UserNotFoundException(username);

        String expectedErrorMessage = "Usuário não encontrado com o username: " + username;
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
