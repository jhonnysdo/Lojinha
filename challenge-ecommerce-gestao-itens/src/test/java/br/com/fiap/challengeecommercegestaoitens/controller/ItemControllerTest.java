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
import static org.mockito.ArgumentMatchers.anyString;
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
        JwtService jwtService = mock(JwtService.class);
        when(mockItemService.listarTodos()).thenReturn(expectedItems);
        when(jwtService.extractRole(anyString())).thenReturn("USER");
        ItemController itemController = new ItemController(mockItemService, jwtService);

        // Call the method under test
        ResponseEntity<List<ItemDTO>> responseEntity = itemController.listarTodos("Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6W3siYXV0aG9yaXR5IjoiQURNSU4ifV0sInN1YiI6Im90YXZpbyIsImlhdCI6MTcxMTE1MDQ1NSwiZXhwIjoxNzExMTUxODk1fQ.l47bwM-Tq1-ufLwBCUTi9KNgEHJj0rqMt06ZCidw5VA");
        
        // Verify the result
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedItems, responseEntity.getBody());
    }
}