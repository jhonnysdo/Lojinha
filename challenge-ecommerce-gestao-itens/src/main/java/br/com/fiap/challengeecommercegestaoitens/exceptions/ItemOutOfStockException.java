package br.com.fiap.challengeecommercegestaoitens.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ItemOutOfStockException extends HttpClientErrorException {
    public ItemOutOfStockException(Long id, Integer quantidade) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "itemId [" + id + "] não tem estoque disponível, quantidade disponível: " + quantidade);
    }
}
