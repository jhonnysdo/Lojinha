package br.com.fiap.challengeecommercecarrinhodecompras.services;

import br.com.fiap.challengeecommercecarrinhodecompras.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ProdutoServiceTest {

    private final RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    private final ProdutoService produtoService = new ProdutoService(restTemplate);

    @Test
    void testFetchProdutoSuccess() {
        Long produtoId = 1L;
        String authHeader = "testAuthHeader";
        String testResponse = "testApiResponse";
        when(restTemplate.exchange(
                "http://localhost:8081/gestao-itens/itens/" + produtoId,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(authHeader)),
                String.class))
                .thenReturn(new ResponseEntity<>(testResponse, HttpStatus.OK));

        ResponseEntity<String> response = produtoService.fetchProduto(produtoId, authHeader);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testResponse, response.getBody());
    }

    @Test
    void testFetchProdutoNotFound() {
        Long produtoId = 1L;
        String authHeader = "testAuthHeader";
        when(restTemplate.exchange(
                "http://localhost:8081/gestao-itens/itens/" + produtoId,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(authHeader)),
                String.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(ItemNotFoundException.class, () -> produtoService.fetchProduto(produtoId, authHeader));
    }

    @Test
    void testFetchProdutoOtherError() {
        Long produtoId = 8021L;
        String authHeader = "testAuthHeader";
        when(restTemplate.exchange(
                "http://localhost:8081/gestao-itens/itens/" + produtoId,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(authHeader)),
                String.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        
        assertThrows(RuntimeException.class, () -> produtoService.fetchProduto(produtoId, authHeader));
    }

    private HttpHeaders createHeaders(String authHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        return headers;
    }
}