package br.com.fiap.challengeecommercepagamentos.exceptions;

public class PagamentoNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Pagamento não iniciado usuário ID: ";

    public PagamentoNotFoundException(String username) {
        super(ERROR_MESSAGE + username);
    }
}