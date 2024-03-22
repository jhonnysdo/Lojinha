package br.com.fiap.challengeecommercegestaoitens.controller;

import br.com.fiap.challengeecommercegestaoitens.controllers.ItemController;
import br.com.fiap.challengeecommercegestaoitens.dto.ItemDTO;
import br.com.fiap.challengeecommercegestaoitens.services.ItemService;
import br.com.fiap.challengeecommercegestaoitens.services.JwtService;
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
        ItemDTO item1 = new ItemDTO();
        ItemDTO item2 = new ItemDTO();
        List<ItemDTO> expectedItems = Arrays.asList(item1, item2);
        
        // Define the behavior of the mock object
        when(mockItemService.listarTodos()).thenReturn(expectedItems);

        JwtService jwtService = null;
        ItemController itemController = new ItemController(mockItemService, jwtService);

        // Call the method under test
        ResponseEntity<List<ItemDTO>> responseEntity = itemController.listarTodos();
        
        // Verify the result
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedItems, responseEntity.getBody());
    }
}