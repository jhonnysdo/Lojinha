package br.com.fiap.challengeecommercecarrinhodecompras.exceptions;

public class CarrinhoNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Carrinho não encontrado para o usuário ID: ";

    public CarrinhoNotFoundException(String username) {
        super(ERROR_MESSAGE + username);
    }
}