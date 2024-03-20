package br.com.fiap.challengeecommercegestaoitens.controllers;

import br.com.fiap.challengeecommercegestaoitens.dto.ItemDTO;
import br.com.fiap.challengeecommercegestaoitens.services.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itens")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemDTO> adicionarItem(@RequestBody ItemDTO item) {
        ItemDTO salvo = itemService.salvarItem(item);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> listarTodos() {
        return ResponseEntity.ok(itemService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ItemDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        itemService.deletarItem(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> atualizarItem(@PathVariable Long id, @RequestBody ItemDTO itemDto) {
        ItemDTO itemAtualizado = itemService.atualizarItem(id, itemDto);
        return ResponseEntity.ok(itemAtualizado);
    }
}
