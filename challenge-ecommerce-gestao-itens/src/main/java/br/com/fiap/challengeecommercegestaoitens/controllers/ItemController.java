package br.com.fiap.challengeecommercegestaoitens.controllers;

import br.com.fiap.challengeecommercegestaoitens.entity.Item;
import br.com.fiap.challengeecommercegestaoitens.services.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<Item> adicionarItem(@RequestBody Item item) {
        Item salvo = itemService.salvarItem(item);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Item>> listarTodos() {
        return ResponseEntity.ok(itemService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        itemService.deletarItem(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> atualizarItem(@PathVariable Long id, @RequestBody Item item) {
        Item itemAtualizado = itemService.atualizarItem(id, item);
        return ResponseEntity.ok(itemAtualizado);
    }
}
