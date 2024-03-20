package br.com.fiap.challengeecommercecarrinhodecompras.controllers;

import br.com.fiap.challengeecommercecarrinhodecompras.dto.ItemCarrinhoDTO;
import br.com.fiap.challengeecommercecarrinhodecompras.entity.ItemCarrinho;
import br.com.fiap.challengeecommercecarrinhodecompras.services.CarrinhoService;
import br.com.fiap.challengeecommercecarrinhodecompras.services.JwtService;
import br.com.fiap.challengeecommercecarrinhodecompras.services.ProdutoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinho")
@AllArgsConstructor
public class CarrinhoController {

    private CarrinhoService carrinhoService;
    private JwtService jwtService;
    private ProdutoService produtoService;

    @GetMapping
    public List<ItemCarrinho> listarItensCarrinho(@RequestParam String username) {
        return carrinhoService.listarItensCarrinho(username);
    }

    @PostMapping
    public ItemCarrinho adicionarItemCarrinho(@Valid @RequestBody ItemCarrinhoDTO itemCarrinhoDTO, @RequestHeader(value = "Authorization") String authorizationHeader) {
        var jwtToken = authorizationHeader.substring(7);

        ItemCarrinho itemCarrinho = ItemCarrinho.builder()
                .produtoId(itemCarrinhoDTO.getProdutoId())
                .quantidade(itemCarrinhoDTO.getQuantidade())
                .username(jwtService.extractUsername(jwtToken))
                .build();

        ResponseEntity<String> response = produtoService.fetchProduto(itemCarrinho.getProdutoId(), authorizationHeader);
        if (response.getStatusCode().is2xxSuccessful()) {
            return carrinhoService.adicionarItemCarrinho(itemCarrinho);
        } else {
            throw new RuntimeException("Erro ao chamar o serviço de itens.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerItemCarrinho(@PathVariable Long id) {
        carrinhoService.removerItemCarrinho(id);
        return ResponseEntity.ok("Item removido com sucesso.");
    }
}
