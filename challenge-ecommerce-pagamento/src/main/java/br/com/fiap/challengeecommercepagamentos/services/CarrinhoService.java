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
        String url = "http://localhost:8082/carrinho-de-compras/carrinho/pendentePagamento";

        return callService(url, HttpMethod.GET, authorizationHeader);
    }

    public ResponseEntity<CarrinhoDTO> atualizarStautsPago(String authorizationHeader) {
        String url = "http://localhost:8082/carrinho-de-compras/carrinho/atualizarStatusPago";

        return callService(url, HttpMethod.PUT, authorizationHeader);
    }

    public ResponseEntity<CarrinhoDTO> atualizarStautsCancelado(String authorizationHeader) {
        String url = "http://localhost:8082/carrinho-de-compras/carrinho/cancelarCompra";

        return callService(url, HttpMethod.PUT, authorizationHeader);
    }

    private ResponseEntity<CarrinhoDTO> callService(String url, HttpMethod httpMethod, String authorizationHeader) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorizationHeader);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            return restTemplate.exchange(url,
                    httpMethod,
                    entity,
                    CarrinhoDTO.class);

        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CarrinhoNotFoundException(jwtService.extractUsername(authorizationHeader.substring(7)));
            } else {
                throw new RuntimeException("Erro ao chamar o serviço de itens.", ex);
            }
        }
    }
}
