package br.com.fiap.challengeecommercegestaoitens.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ItemNotFoundException extends HttpClientErrorException {
    public ItemNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "- Item com id " + id + " não encontrado");
    }
}
