package br.com.fiap.challengeecommercecarrinhodecompras.exceptions;

public class ItemNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Item não encontrado com ID: ";

    public ItemNotFoundException(Long id) {
        super(ERROR_MESSAGE + id);
    }
}