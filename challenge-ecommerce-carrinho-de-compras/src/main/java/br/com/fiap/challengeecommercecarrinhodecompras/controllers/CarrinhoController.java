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
    private final JwtService jwtService;

    @GetMapping
    public List<ItemCarrinho> listarItensCarrinho(
            @RequestParam String username
    ) {
        return carrinhoService.listarItensCarrinho(username);
    }

    @PostMapping
    public ResponseEntity<ItemCarrinhoDTO> adicionarItemCarrinho(
            @Valid @RequestBody ItemCarrinhoDTO itemCarrinhoDTO,
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        return ResponseEntity.ok(carrinhoService.adicionarItemCarrinho(
                itemCarrinhoDTO,
                authorizationHeader
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerItemCarrinho(@PathVariable Long id) {
        carrinhoService.removerItemCarrinho(id);
        return ResponseEntity.ok("Item removido com sucesso.");
    }

    private void isAuthorized(String tokenHeader) {
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            throw new UnsupportedOperationException("Invalid token format.");
        }
        String token = tokenHeader.substring(7);

        String role = jwtService.extractRole(token);
        if (!role.equals("ADMIN")) {
            throw new UnsupportedOperationException("Unauthorized");
        }
    }
}
