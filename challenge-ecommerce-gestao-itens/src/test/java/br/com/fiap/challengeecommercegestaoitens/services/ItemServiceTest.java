package br.com.fiap.challengeecommercegestaoitens.services;

import br.com.fiap.challengeecommercegestaoitens.entity.Item;
import br.com.fiap.challengeecommercegestaoitens.exceptions.ItemNotFoundException;
import br.com.fiap.challengeecommercegestaoitens.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    void testBuscarPorIdItemExists(){
        Item item = new Item();
        item.setId(1L);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Item result = itemService.buscarPorId(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testBuscarPorIdItemDoesNotExist(){
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> {
            itemService.buscarPorId(1L);
        });
    }
}