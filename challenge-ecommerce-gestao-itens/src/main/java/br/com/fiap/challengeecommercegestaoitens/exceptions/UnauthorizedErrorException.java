package br.com.fiap.challengeecommercegestaoitens.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class UnauthorizedErrorException extends HttpClientErrorException {
    public UnauthorizedErrorException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
