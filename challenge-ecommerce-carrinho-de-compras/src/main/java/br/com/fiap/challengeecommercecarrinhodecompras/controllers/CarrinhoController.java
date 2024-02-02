package br.com.fiap.challengeecommercecarrinhodecompras.controllers;

import br.com.fiap.challengeecommercecarrinhodecompras.entity.ItemCarrinho;
import br.com.fiap.challengeecommercecarrinhodecompras.exceptions.ItemNotFoundException;
import br.com.fiap.challengeecommercecarrinhodecompras.services.CarrinhoService;
import br.com.fiap.challengeecommercecarrinhodecompras.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/carrinho")
@AllArgsConstructor
public class CarrinhoController {

    private CarrinhoService carrinhoService;
    JwtService jwtService;
    private RestTemplate restTemplate;

    @GetMapping
    public List<ItemCarrinho> listarItensCarrinho(@RequestParam String username) {
        return carrinhoService.listarItensCarrinho(username);
    }

    @PostMapping
    public ItemCarrinho adicionarItemCarrinho(@RequestBody ItemCarrinho itemCarrinho, @RequestHeader(value = "Authorization") String authorizationHeader) {
        var jwtToken = authorizationHeader.substring(7);
        itemCarrinho.setUsername(jwtService.extractUsername(jwtToken));

        // Cria um objeto HttpHeaders e configura o cabeçalho Authorization
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader); // Reutiliza o cabeçalho Authorization recebido

        // Cria um HttpEntity com os cabeçalhos (corpo pode ser null, já que estamos fazendo um GET)
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Faz a chamada GET incluindo o cabeçalho
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://localhost:8081/gestao-itens/itens/" + itemCarrinho.getProdutoId(),
                    HttpMethod.GET,
                    entity,
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return carrinhoService.adicionarItemCarrinho(itemCarrinho);
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ItemNotFoundException(itemCarrinho.getProdutoId());
            } else {
                // Lidar com outros códigos de status de erro, se necessário
                throw new RuntimeException("Erro ao chamar o serviço de itens.");
            }
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ItemNotFoundException(itemCarrinho.getProdutoId());
            } else {
                // Lidar com outros erros de cliente, se necessário
                throw new RuntimeException("Erro ao chamar o serviço de itens.", ex);
            }
        }
    }

    @DeleteMapping("/{id}")
    public String removerItemCarrinho(@PathVariable Long id) {
        carrinhoService.removerItemCarrinho(id);
        return "Item removido com sucesso.";
    }
}
