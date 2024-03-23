package br.com.fiap.challengeecommercecarrinhodecompras.controllers;

import br.com.fiap.challengeecommercecarrinhodecompras.dto.CarrinhoDTO;
import br.com.fiap.challengeecommercecarrinhodecompras.dto.ItemCarrinhoDTO;
import br.com.fiap.challengeecommercecarrinhodecompras.entity.ItemCarrinho;
import br.com.fiap.challengeecommercecarrinhodecompras.exceptions.CarrinhoNotFoundException;
import br.com.fiap.challengeecommercecarrinhodecompras.exceptions.HttpUnauthorizedException;
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
    public ResponseEntity<CarrinhoDTO> listarItensCarrinho(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        return ResponseEntity.ok(carrinhoService.listarItensCarrinho(authorizationHeader));
    }

    @PostMapping
    public ResponseEntity<CarrinhoDTO> adicionarItemCarrinho(
            @Valid @RequestBody ItemCarrinhoDTO itemCarrinhoDTO,
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        return ResponseEntity.ok(
                carrinhoService.adicionarItemCarrinho(
                itemCarrinhoDTO,
                authorizationHeader
        ));
    }

    @DeleteMapping("/itens/{id}")
    public ResponseEntity<String> removerItemCarrinho(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        carrinhoService.removerItemCarrinho(id, authorizationHeader);
        return ResponseEntity.ok("Item removido com sucesso.");
    }

    @PutMapping("/finalizarCompra")
    public ResponseEntity<String> finalizarCompra(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        carrinhoService.finalizarCompra(authorizationHeader);
        return ResponseEntity.ok("Carrinho finalizado com sucesso, aguardando a confirmação do pagamento.");
    }

    @PutMapping("/cancelarCompra")
    public ResponseEntity<String> cancelarCompra(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        carrinhoService.cancelarCompra(authorizationHeader);
        return ResponseEntity.ok("Carrinho esvaziado com sucesso.");
    }

    private void isAuthorized(String tokenHeader) {
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            throw new HttpUnauthorizedException();
        }
        String token = tokenHeader.substring(7);

        String role = jwtService.extractRole(token);
        if (!role.equals("ADMIN") && !role.equals("USER")) {
            throw new HttpUnauthorizedException();
        }
    }
}
