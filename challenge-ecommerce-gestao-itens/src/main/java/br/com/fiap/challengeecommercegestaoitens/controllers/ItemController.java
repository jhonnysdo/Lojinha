package br.com.fiap.challengeecommercegestaoitens.controllers;

import br.com.fiap.challengeecommercegestaoitens.dto.ItemDTO;
import br.com.fiap.challengeecommercegestaoitens.exceptions.UnauthorizedErrorException;
import br.com.fiap.challengeecommercegestaoitens.services.ItemService;
import br.com.fiap.challengeecommercegestaoitens.services.JwtService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@OpenAPIDefinition
@AllArgsConstructor
@Tag(name = "Item", description = "APIs Relacionadas ao Item/Produto")
public class ItemController {

    private final ItemService itemService;
    private final JwtService jwtService;

    @Operation(summary = "Adiciona Item")
    @PostMapping
    public ResponseEntity<ItemDTO> adicionarItem(
            @RequestBody ItemDTO item,
            @RequestHeader("Authorization") String tokenHeader
    ) {
        isAdminAuthorized(tokenHeader);
        ItemDTO salvo = itemService.salvarItem(item);
        return ResponseEntity.ok(salvo);
    }

    @Operation(summary = "Lista todos os Itens")
    @GetMapping
    public ResponseEntity<List<ItemDTO>> listarTodos(
            @RequestHeader("Authorization") String tokenHeader
    ) {
        isAuthorized(tokenHeader);
        return ResponseEntity.ok(itemService.listarTodos());
    }

    @Operation(summary = "Busca Item por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ItemDTO>> buscarPorId(
            @PathVariable Long id,
            @RequestHeader("Authorization") String tokenHeader
    ) {
        isAuthorized(tokenHeader);
        return ResponseEntity.ok(itemService.buscarPorId(id));
    }

    @Operation(summary = "Adiciona Item ao Carrinho")
    @PutMapping("/adicinarItemCarrinho/{id}/quantidade/{quantidade}")
    public ResponseEntity<ItemDTO> reservarEstoque(
            @PathVariable Long id,
            @PathVariable Integer quantidade,
            @RequestHeader("Authorization") String tokenHeader
    ) {
        isAuthorized(tokenHeader);
        itemService.reservarEstoque(id, quantidade);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove Item do Carrinho")
    @PutMapping("/removerItemCarrinho/{id}/quantidade/{quantidade}")
    public ResponseEntity<ItemDTO> desreservarEstoque(
            @PathVariable Long id,
            @PathVariable Integer quantidade,
            @RequestHeader("Authorization") String tokenHeader
    ) {
        isAuthorized(tokenHeader);
        itemService.desreservarEstoque(id, quantidade);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deleta Item")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(
            @PathVariable Long id,
            @RequestHeader("Authorization") String tokenHeader
    ) {
        isAdminAuthorized(tokenHeader);
        itemService.deletarItem(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualiza Item")
    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> atualizarItem(
            @PathVariable Long id,
            @RequestBody ItemDTO itemDto,
            @RequestHeader("Authorization") String tokenHeader
    ) {
        isAdminAuthorized(tokenHeader);
        ItemDTO itemAtualizado = itemService.atualizarItem(id, itemDto);
        return ResponseEntity.ok(itemAtualizado);
    }

    private void isAdminAuthorized(String tokenHeader) {
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            throw new UnauthorizedErrorException();
        }
        String token = tokenHeader.substring(7);

        String role = jwtService.extractRole(token);
        if (!role.equals("ADMIN")) {
            throw new UnauthorizedErrorException();
        }
    }

    private void isAuthorized(String tokenHeader) {
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            throw new UnauthorizedErrorException();
        }

        String token = tokenHeader.substring(7);

        String role = jwtService.extractRole(token);
        if (!role.equals("ADMIN") && !role.equals("USER")) {
            throw new UnauthorizedErrorException();
        }
    }
}
