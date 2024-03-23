package br.com.fiap.challengeecommercegestaoitens.services;

import br.com.fiap.challengeecommercegestaoitens.dto.ItemDTO;
import br.com.fiap.challengeecommercegestaoitens.entity.Item;
import br.com.fiap.challengeecommercegestaoitens.exceptions.ItemNotFoundException;
import br.com.fiap.challengeecommercegestaoitens.exceptions.ItemOutOfStockException;
import br.com.fiap.challengeecommercegestaoitens.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public ItemDTO salvarItem(ItemDTO itemDTO) {
        Item item = Item.builder()
                .nome(itemDTO.getNome())
                .preco(itemDTO.getPreco())
                .quantidade(itemDTO.getQuantidade())
                .build();

        return modelMapper.map(itemRepository.save(item), ItemDTO.class);
    }

    public List<ItemDTO> listarTodos() {
        List<Item> itens = itemRepository.findAll();
        return itens.stream()
                .map(item -> modelMapper.map(item, ItemDTO.class)).toList();
    }

    public Optional<ItemDTO> buscarPorId(Long id) {
        Item item =  itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));

        return Optional.of(modelMapper.map(item, ItemDTO.class));
    }

    @Transactional
    public void deletarItem(Long id) {
        itemRepository.deleteById(id);
    }

    @Transactional
    public ItemDTO atualizarItem(Long id, ItemDTO itemAtualizado) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));

        item.setNome(itemAtualizado.getNome());
        item.setPreco(itemAtualizado.getPreco());
        item.setQuantidade(itemAtualizado.getQuantidade());

        return modelMapper.map(itemRepository.save(item), ItemDTO.class);
    }

    @Transactional
    public void reservarEstoque(Long id, Integer quantidade) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));

        if (item.getQuantidade() < quantidade) {
            throw new ItemOutOfStockException(id, item.getQuantidade());
        }

        item.setQuantidade(item.getQuantidade() - quantidade);
        itemRepository.save(item);
    }

    @Transactional
    public void desreservarEstoque(Long id, Integer quantidade) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
        item.setQuantidade(item.getQuantidade() + quantidade);
        itemRepository.save(item);
    }
}
