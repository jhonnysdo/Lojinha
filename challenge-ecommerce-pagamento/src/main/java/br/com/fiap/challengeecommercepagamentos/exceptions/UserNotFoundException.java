package br.com.fiap.challengeecommercepagamentos.exceptions;

public class UserNotFoundException  extends RuntimeException {
    private static final String ERROR_MESSAGE = "Usuário não enontrado com o username: ";

    public UserNotFoundException(String usuername) {
        super(ERROR_MESSAGE + usuername );
    }
}
