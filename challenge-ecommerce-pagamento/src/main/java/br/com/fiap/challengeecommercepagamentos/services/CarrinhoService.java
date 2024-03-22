package br.com.fiap.challengeecommercepagamentos.services;

import br.com.fiap.challengeecommercepagamentos.dto.CarrinhoDTO;
import br.com.fiap.challengeecommercepagamentos.exceptions.CarrinhoNotFoundException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CarrinhoService {

    private final RestTemplate restTemplate;

    private final JwtService jwtService = new JwtService();

    public CarrinhoService() {
        this.restTemplate = new RestTemplate();
    }

    public CarrinhoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<CarrinhoDTO> fetchCarrinho(String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange("http://localhost:8091/carrinho",
                    HttpMethod.GET,
                    entity,
                    CarrinhoDTO.class);

        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CarrinhoNotFoundException(
                        jwtService.extractUsername(authorizationHeader.substring(7))
                );
            } else {
                throw new RuntimeException("Erro ao chamar o serviço carrinho.", ex);
            }
        }
    }
}
