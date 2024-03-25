package br.com.fiap.challengeecommercecarrinhodecompras.controllers;

import br.com.fiap.challengeecommercecarrinhodecompras.Enum.Status;
import br.com.fiap.challengeecommercecarrinhodecompras.dto.CarrinhoDTO;
import br.com.fiap.challengeecommercecarrinhodecompras.dto.ProdutoDTO;
import br.com.fiap.challengeecommercecarrinhodecompras.entity.Carrinho;
import br.com.fiap.challengeecommercecarrinhodecompras.entity.ItemCarrinho;
import br.com.fiap.challengeecommercecarrinhodecompras.repository.CarrinhoRepository;
import br.com.fiap.challengeecommercecarrinhodecompras.services.CarrinhoService;
import br.com.fiap.challengeecommercecarrinhodecompras.services.JwtService;
import br.com.fiap.challengeecommercecarrinhodecompras.services.ProdutoService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CarrinhoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarrinhoService carrinhoService;

    @MockBean
    private CarrinhoRepository carrinhoRepository;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private ProdutoService produtoService;

    @Test
    void listarItensCarrinho() throws Exception {
        String username = "test";
        List<ItemCarrinho> items = new ArrayList<>();
        // ItemCarrinho item = new ItemCarrinho(1L, username, 2L, 2);
        // items.add(item);
        // given(carrinhoService.listarItensCarrinho(username)).willReturn(items);

        // mockMvc.perform(get("/carrinho")
        //                 .param("username", username)
        //                 .header("Authorization", "Bearer token")
        //                 .contentType(MediaType.APPLICATION_JSON))
        //         .andExpect(status().isOk())
        //         .andExpect(jsonPath("$", hasSize(1)))
        //         .andExpect(jsonPath("$[0].id").value(item.getId()))
        //         .andExpect(jsonPath("$[0].username").value(username))
        //         .andExpect(jsonPath("$[0].produtoId").value(item.getProdutoId()))
        //         .andExpect(jsonPath("$[0].quantidade").value(item.getQuantidade()));
    }

    @Test
    void addCartItemTest() throws Exception {
        ItemCarrinho itemCarrinho = new ItemCarrinho();
        itemCarrinho.setProdutoId(1L);
        itemCarrinho.setQuantidade(1);

        var carrinho = new CarrinhoDTO();
                carrinho.setUsername("user");
                carrinho.setItens(new ArrayList<>());
                carrinho.setDataCriacao(LocalDate.now());
                carrinho.setStatus(Status.CRIADO);
                carrinho.setValorTotal(0.0);

        Mockito.when(jwtService.extractUsername(Mockito.anyString())).thenReturn("user");
        Mockito.when(produtoService.adicinarItemCarrinho(
                Mockito.anyLong(), Mockito.anyInt(), Mockito.anyString())).thenReturn(ResponseEntity.ok(new ProdutoDTO()));
        Mockito.when(jwtService.extractRole(any())).thenReturn("USER");
        Mockito.when(carrinhoService.adicionarItemCarrinho(Mockito.any(), Mockito.anyString())).thenReturn(carrinho);
        Mockito.when(produtoService.fetchProduto(
                Mockito.anyLong(), Mockito.anyString())).thenReturn(ResponseEntity.ok(new ProdutoDTO()));

        mockMvc.perform(post("/carrinho")
                        .header("Authorization", "Bearer token")
                        .contentType("application/json")
                        .content("{\"produtoId\": 1, \"quantidade\": 1}"))
                .andExpect(status().isOk());
    }

    /**
     * Test case for `removerItemCarrinho` method in `CarrinhoController`.
     * This test verifies if the `removerItemCarrinho` method returns the correct result and runs without any exceptions.
     * The Http Delete request is used to call this method.
     */
    @Test
    void removerItemCarrinhoTest() throws Exception {
        var carrinho = new Carrinho();
        ArrayList<ItemCarrinho> itens = new ArrayList<>();
        itens.add(new ItemCarrinho(1L, 1L, 1, "ps5", 3500.00));
        carrinho.setItens(itens);
        // Mock CarrinhoService method to not perform actions, because we are not testing service layer here
        doNothing().when(carrinhoService).removerProdutoCarrinho(anyLong(), anyString());
        Mockito.when(carrinhoRepository.findByUsernameAndStatusIsCriado(anyString())).thenReturn(carrinho);
        Mockito.when(jwtService.extractRole(any())).thenReturn("USER");

        // Perform the HTTP DELETE request and expect 200 OK status and "Item removido com sucesso." message in response
        this.mockMvc.perform(delete("/carrinho/produto/{id}", 1)
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(content().string("Item removido com sucesso."));

        // Verify that removerItemCarrinho method was called exactly once in our mock CarrinhoService service
        verify(carrinhoService, times(1)).removerProdutoCarrinho(anyLong(), anyString());
    }
}