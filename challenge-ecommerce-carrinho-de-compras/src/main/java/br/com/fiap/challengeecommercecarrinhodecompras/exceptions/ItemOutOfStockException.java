package br.com.fiap.challengeecommercecarrinhodecompras.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ItemOutOfStockException extends HttpClientErrorException {
    public ItemOutOfStockException(Long id) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "itemId [" + id + "] não tem estoque disponível");
    }
}
