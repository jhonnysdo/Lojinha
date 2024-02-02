package br.com.fiap.challengeecommercegestaoitens.services;

import br.com.fiap.challengeecommercegestaoitens.entity.Item;
import br.com.fiap.challengeecommercegestaoitens.exceptions.ItemNotFoundException;
import br.com.fiap.challengeecommercegestaoitens.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public Item salvarItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> listarTodos() {
        return itemRepository.findAll();
    }

    public Item buscarPorId(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
    }

    @Transactional
    public void deletarItem(Long id) {
        itemRepository.deleteById(id);
    }

    @Transactional
    public Item atualizarItem(Long id, Item itemAtualizado) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));

        item.setNome(itemAtualizado.getNome());
        item.setPreco(itemAtualizado.getPreco());

        return itemRepository.save(item);
    }
}
