package br.com.fiap.challengeecommercecarrinhodecompras.controllers;

import br.com.fiap.challengeecommercecarrinhodecompras.dto.CarrinhoDTO;
import br.com.fiap.challengeecommercecarrinhodecompras.dto.ItemCarrinhoDTO;
import br.com.fiap.challengeecommercecarrinhodecompras.exceptions.HttpUnauthorizedException;
import br.com.fiap.challengeecommercecarrinhodecompras.services.CarrinhoService;
import br.com.fiap.challengeecommercecarrinhodecompras.services.JwtService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinho")
@AllArgsConstructor
@OpenAPIDefinition
public class CarrinhoController {

    private CarrinhoService carrinhoService;
    private final JwtService jwtService;

    @GetMapping("/todos")
    public ResponseEntity<List<CarrinhoDTO>> listarTodosCarrinhos(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAdminAuthorized(authorizationHeader);
        return ResponseEntity.ok(carrinhoService.listarCarrinhos());
    }
    @GetMapping
    public ResponseEntity<CarrinhoDTO> listarCarrinhoUsuario(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        return ResponseEntity.ok(carrinhoService.listarCarrinhoUsuario(authorizationHeader));
    }

    @GetMapping("/pendentePagamento")
    public ResponseEntity<CarrinhoDTO> listarCarrinhoPendentePagamento(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        return ResponseEntity.ok(carrinhoService.listarCarrinhoUsuarioPendentePagamento(authorizationHeader));
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

    @DeleteMapping("/produto/{id}")
    public ResponseEntity<String> removerProdutoCarrinho(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        carrinhoService.removerProdutoCarrinho(id, authorizationHeader);
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

    @PutMapping("/atualizarStatusPago")
    public ResponseEntity<CarrinhoDTO> atualizarStatusPago(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        return ResponseEntity.ok(carrinhoService.atualizarStatusPago(authorizationHeader));
    }

    @PutMapping("/cancelarCompra")
    public ResponseEntity<CarrinhoDTO> cancelarCompra(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        return ResponseEntity.ok(carrinhoService.cancelarCompra(authorizationHeader));
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

    private void isAdminAuthorized(String tokenHeader) {
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            throw new HttpUnauthorizedException();
        }
        String token = tokenHeader.substring(7);

        String role = jwtService.extractRole(token);
        if (!role.equals("ADMIN")) {
            throw new HttpUnauthorizedException();
        }
    }
}
