package br.com.fiap.challengeecommercecarrinhodecompras.services;

import br.com.fiap.challengeecommercecarrinhodecompras.dto.ProdutoDTO;
import br.com.fiap.challengeecommercecarrinhodecompras.exceptions.ItemNotFoundException;
import br.com.fiap.challengeecommercecarrinhodecompras.exceptions.ItemOutOfStockException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProdutoService {

    private final RestTemplate restTemplate;

    public ProdutoService() {
        this.restTemplate = new RestTemplate();
    }

    public ProdutoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<ProdutoDTO> fetchProduto(
            Long produtoId,
            String authorizationHeader
    ) {

        String url = "http://localhost:8081/gestao-itens/"
                + produtoId;
        return callService(url, produtoId, 0, authorizationHeader, HttpMethod.GET);
    }

    public ResponseEntity<ProdutoDTO> adicinarItemCarrinho(
            Long produtoId,
            Integer quantidade,
            String authorizationHeader
    ) {

        String url = "http://localhost:8081/gestao-itens/adicinarItemCarrinho/"
                + produtoId + "/quantidade/" + quantidade;
        return callService(url, produtoId, quantidade, authorizationHeader, HttpMethod.PUT);
    }

    public ResponseEntity<ProdutoDTO> removeItemCarrinho(
            Long produtoId,
            Integer quantidade,
            String authorizationHeader
    ) {

        String url = "http://localhost:8081/gestao-itens/removerItemCarrinho/"
                + produtoId + "/quantidade/" + quantidade;
        return callService(url, produtoId, quantidade, authorizationHeader, HttpMethod.PUT);
    }

    private ResponseEntity<ProdutoDTO> callService(
            String url, Long produtoId, Integer quantidade, String authorizationHeader, HttpMethod httpMethod
    ) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorizationHeader);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            return restTemplate.exchange(url,
                    httpMethod,
                    entity,
                    ProdutoDTO.class);

        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ItemNotFoundException(produtoId);
            } else if (ex.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
                throw new ItemOutOfStockException(produtoId);
            } else {
                throw new RuntimeException("Erro ao chamar o serviço de itens.", ex);
            }
        }
    }
}
