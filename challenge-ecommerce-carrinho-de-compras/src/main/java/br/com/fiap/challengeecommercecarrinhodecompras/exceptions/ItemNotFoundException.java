package br.com.fiap.challengeecommercecarrinhodecompras.exceptions;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long id) {
        super("Item não encontrado com ID: " + id);
    }
}

