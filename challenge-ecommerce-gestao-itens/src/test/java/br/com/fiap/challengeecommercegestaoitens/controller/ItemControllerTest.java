package br.com.fiap.challengeecommercegestaoitens.controller;

import br.com.fiap.challengeecommercegestaoitens.controllers.ItemController;
import br.com.fiap.challengeecommercegestaoitens.entity.Item;
import br.com.fiap.challengeecommercegestaoitens.services.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    @Test
    public void testListarTodos() {
        // Create a mock ItemService
        ItemService mockItemService = mock(ItemService.class);
        
        // Prepare data for testing
        Item item1 = new Item();
        Item item2 = new Item();
        List<Item> expectedItems = Arrays.asList(item1, item2);
        
        // Define the behavior of the mock object
        when(mockItemService.listarTodos()).thenReturn(expectedItems);
        
        ItemController itemController = new ItemController(mockItemService);

        // Call the method under test
        ResponseEntity<List<Item>> responseEntity = itemController.listarTodos();
        
        // Verify the result
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedItems, responseEntity.getBody());
    }
}