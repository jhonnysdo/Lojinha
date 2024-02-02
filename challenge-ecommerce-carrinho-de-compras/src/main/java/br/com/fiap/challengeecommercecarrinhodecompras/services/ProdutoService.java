package br.com.fiap.challengeecommercecarrinhodecompras.services;

import br.com.fiap.challengeecommercecarrinhodecompras.exceptions.ItemNotFoundException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProdutoService {

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> fetchProduto(Long produtoId, String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange("http://localhost:8081/gestao-itens/itens/" + produtoId,
                    HttpMethod.GET,
                    entity,
                    String.class);

        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ItemNotFoundException(produtoId);
            } else {
                throw new RuntimeException("Erro ao chamar o serviço de itens.", ex);
            }
        }
    }
}
