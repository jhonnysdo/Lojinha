package br.com.fiap.challengeecommercegestaoitens.controllers;

import br.com.fiap.challengeecommercegestaoitens.dto.ItemDTO;
import br.com.fiap.challengeecommercegestaoitens.exceptions.UnauthorizedErrorException;
import br.com.fiap.challengeecommercegestaoitens.services.ItemService;
import br.com.fiap.challengeecommercegestaoitens.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final JwtService jwtService;


    @PostMapping
    public ResponseEntity<ItemDTO> adicionarItem(
            @RequestBody ItemDTO item,
            @RequestHeader("Authorization") String tokenHeader
    ) {
        isAdminAuthorized(tokenHeader);
        ItemDTO salvo = itemService.salvarItem(item);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> listarTodos(
            @RequestHeader("Authorization") String tokenHeader
    ) {
        isAuthorized(tokenHeader);
        return ResponseEntity.ok(itemService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ItemDTO>> buscarPorId(
            @PathVariable Long id,
            @RequestHeader("Authorization") String tokenHeader
    ) {
        isAuthorized(tokenHeader);
        return ResponseEntity.ok(itemService.buscarPorId(id));
    }

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


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(
            @PathVariable Long id,
            @RequestHeader("Authorization") String tokenHeader
    ) {
        isAdminAuthorized(tokenHeader);
        itemService.deletarItem(id);
        return ResponseEntity.ok().build();
    }

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
