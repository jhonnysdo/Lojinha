package br.com.fiap.challengeecommercecarrinhodecompras.controllers;

import br.com.fiap.challengeecommercecarrinhodecompras.dto.CarrinhoDTO;
import br.com.fiap.challengeecommercecarrinhodecompras.dto.ItemCarrinhoDTO;
import br.com.fiap.challengeecommercecarrinhodecompras.exceptions.HttpUnauthorizedException;
import br.com.fiap.challengeecommercecarrinhodecompras.services.CarrinhoService;
import br.com.fiap.challengeecommercecarrinhodecompras.services.JwtService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinho")
@AllArgsConstructor
@OpenAPIDefinition
@Tag(name = "Carrinho", description = "APIs Relacionadas ao Carrinho de Compras")
public class CarrinhoController {

    private CarrinhoService carrinhoService;
    private final JwtService jwtService;

    @Operation(summary = "Lista Todos os Carrinhos de Compras (Apenas Admin)")
    @GetMapping("/todos")
    public ResponseEntity<List<CarrinhoDTO>> listarTodosCarrinhos(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAdminAuthorized(authorizationHeader);
        return ResponseEntity.ok(carrinhoService.listarCarrinhos());
    }

    @Operation(summary = "Lista Carrinho do Usuário (Apenas Usuário)")
    @GetMapping
    public ResponseEntity<CarrinhoDTO> listarCarrinhoUsuario(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        return ResponseEntity.ok(carrinhoService.listarCarrinhoUsuario(authorizationHeader));
    }

    @Operation(summary = "Lista Carrinho do Usuário com Status Pendente de Pagamento")
    @GetMapping("/pendentePagamento")
    public ResponseEntity<CarrinhoDTO> listarCarrinhoPendentePagamento(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        return ResponseEntity.ok(carrinhoService.listarCarrinhoUsuarioPendentePagamento(authorizationHeader));
    }

    @Operation(summary = "Adiciona Item ao Carrinho")
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

    @Operation(summary = "Remove Item do Carrinho")
    @DeleteMapping("/produto/{id}")
    public ResponseEntity<String> removerProdutoCarrinho(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        carrinhoService.removerProdutoCarrinho(id, authorizationHeader);
        return ResponseEntity.ok("Item removido com sucesso.");
    }

    @Operation(summary = "Finaliza a Compra (Apenas Usuário)")
    @PutMapping("/finalizarCompra")
    public ResponseEntity<String> finalizarCompra(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        carrinhoService.finalizarCompra(authorizationHeader);
        return ResponseEntity.ok("Carrinho finalizado com sucesso, aguardando a confirmação do pagamento.");
    }

    @Operation(summary = "Atualiza Status do Carrinho para Pago")
    @PutMapping("/atualizarStatusPago")
    public ResponseEntity<CarrinhoDTO> atualizarStatusPago(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        isAuthorized(authorizationHeader);
        return ResponseEntity.ok(carrinhoService.atualizarStatusPago(authorizationHeader));
    }

    @Operation(summary = "Cancelar a Compra")
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
